package be.zvz.clova.utils

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import java.net.URLEncoder
import java.util.Base64
import java.util.UUID
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.math.min

object Auth {
    val sid: String
        get() = "${Constants.API_VERSION}_${UUID.randomUUID()}"

    fun getCurrentTime(okHttpClient: OkHttpClient): String = okHttpClient.newCall(
        Request.Builder()
            .url(Constants.Url.CURRENT_TIME)
            .header("User-Agent", Constants.USER_AGENT)
            .get()
            .build()
    ).execute().body.string()

    fun signUrl(okHttpClient: OkHttpClient, url: String): Signature {
        val algorithm = "HmacSHA1"
        val signingKey = SecretKeySpec(
            Constants.KEY.toByteArray(),
            algorithm
        )
        val mac = Mac.getInstance(algorithm)
        mac.init(signingKey)
        val currentTimestamp = try {
            getCurrentTime(okHttpClient)
        } catch (ignored: IOException) {
            System.currentTimeMillis().toString()
        }
        return Signature(
            currentTimestamp,
            Base64.getEncoder().encodeToString(
                mac.doFinal(
                    (url.substring(0, min(255, url.length)) + currentTimestamp).toByteArray()
                )
            )
        )
    }

    fun urlToSignedUrl(url: String, sign: Signature): String =
        url + "?msgpad=" + sign.timestamp + "&md=" + URLEncoder.encode(sign.hmac, "UTF-8")

    data class Signature(
        val timestamp: String,
        val hmac: String
    )
}
