package be.zvz.clova.tts

import be.zvz.clova.Speed
import be.zvz.clova.exception.NoDataReceivedException
import be.zvz.clova.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.Request

abstract class TextToSpeech(protected val okHttpClient: OkHttpClient) {
    abstract fun buildTTSRequest(
        text: String,
        speaker: Constants.Speaker,
        speed: Speed,
        volume: Int = 4
    ): Request

    fun doTextToSpeech(
        text: String,
        speaker: Constants.Speaker,
        speed: Speed
    ): ByteArray = okHttpClient.newCall(buildTTSRequest(text, speaker, speed)).execute().use { response ->
        val bytes = response.body.bytes()
        if (bytes.isNotEmpty()) {
            bytes
        } else {
            throw NoDataReceivedException()
        }
    }
}
