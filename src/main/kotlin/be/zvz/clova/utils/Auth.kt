package be.zvz.clova.utils

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.net.URLEncoder
import java.util.Base64
import java.util.UUID
import java.util.concurrent.locks.ReentrantLock
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.concurrent.withLock
import kotlin.math.min

object Auth {
    private val timeLock = ReentrantLock()
    private var executionTime = -1L
    private var latestUpdatedTime = -1L
    private val mac =
        Mac.getInstance("HmacSHA1").apply {
            init(
                SecretKeySpec(
                    Constants.KEY.toByteArray(),
                    algorithm,
                ),
            )
        }

    fun getSid(): String = "${Constants.API_VERSION}_${UUID.randomUUID()}"

    fun getCachedCurrentTime(): Long = getCachedCurrentTime(System.nanoTime())

    fun getCachedCurrentTime(currentTime: Long): Long = latestUpdatedTime + (currentTime - executionTime) / 1_000L * 1_000L

    fun getCurrentTime(httpClient: HttpClient): Long =
        runBlocking {
            val response =
                httpClient.get(Constants.Url.CURRENT_TIME) {
                    header("User-Agent", Constants.USER_AGENT)
                }

            return@runBlocking response.bodyAsText().toLongOrNull() ?: System.currentTimeMillis()
        }

    fun signUrl(
        httpClient: HttpClient,
        url: String,
    ): Signature {
        val currentTimestamp =
            timeLock.withLock {
                try {
                    val currentTime = System.nanoTime()

                    // 10 minutes
                    if (executionTime == -1L || currentTime - executionTime > 1_000L * 1_000L * 1_000L * 60L * 10L) {
                        executionTime = System.nanoTime()
                        latestUpdatedTime = getCurrentTime(httpClient)
                        latestUpdatedTime
                    } else {
                        getCachedCurrentTime(currentTime)
                    }
                } catch (ignored: IOException) {
                    System.currentTimeMillis()
                }
            }
        return Signature(
            currentTimestamp,
            Base64.getEncoder().encodeToString(
                (mac.clone() as Mac).doFinal(
                    (url.substring(0, min(255, url.length)) + currentTimestamp).toByteArray(),
                ),
            ),
        )
    }

    fun toSignedUrl(
        url: String,
        sign: Signature,
    ): String = url + "?msgpad=" + sign.timestamp + "&md=" + URLEncoder.encode(sign.hmac, "UTF-8")

    data class Signature(
        val timestamp: Long,
        val hmac: String,
    )
}
