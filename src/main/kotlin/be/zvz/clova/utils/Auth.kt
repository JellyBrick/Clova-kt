package be.zvz.clova.utils

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.toLongOrDefault
import okio.IOException
import java.net.URLEncoder
import java.util.Base64
import java.util.UUID
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.math.min

object Auth {
    private var executionTime = -1L
    private var latestUpdatedTime = -1L
    private val mac = Mac.getInstance("HmacSHA1").apply {
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

    fun getCurrentTime(okHttpClient: OkHttpClient): Long = okHttpClient.newCall(
        Request.Builder()
            .url(Constants.Url.CURRENT_TIME)
            .header("User-Agent", Constants.USER_AGENT)
            .get()
            .build(),
    ).execute().body.string().toLongOrDefault(System.currentTimeMillis())

    fun signUrl(okHttpClient: OkHttpClient, url: String): Signature {
        val currentTimestamp = try {
            val currentTime = System.nanoTime()

            // 10 minutes
            if (executionTime == -1L || currentTime - executionTime > 1_000L * 1_000L * 1_000L * 60L * 10L) {
                executionTime = System.nanoTime()
                latestUpdatedTime = getCurrentTime(okHttpClient)
                latestUpdatedTime
            } else {
                getCachedCurrentTime(currentTime)
            }
        } catch (ignored: IOException) {
            System.currentTimeMillis()
        }
        return Signature(
            currentTimestamp,
            Base64.getEncoder().encodeToString(
                mac.doFinal(
                    (url.substring(0, min(255, url.length)) + currentTimestamp).toByteArray(),
                ),
            ),
        )
    }

    fun toSignedUrl(url: String, sign: Signature): String =
        url + "?msgpad=" + sign.timestamp + "&md=" + URLEncoder.encode(sign.hmac, "UTF-8")

    data class Signature(
        val timestamp: Long,
        val hmac: String,
    )
}
