package be.zvz.clova.tts

import be.zvz.clova.Speed
import be.zvz.clova.exception.NoDataReceivedException
import be.zvz.clova.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post

abstract class TextToSpeech(protected val httpClient: HttpClient) {
    abstract fun buildTTSRequest(
        text: String,
        speaker: Constants.Speaker,
        speed: Speed,
        volume: Int = 4,
    ): HttpRequestBuilder

    suspend fun doTextToSpeech(
        text: String,
        speaker: Constants.Speaker,
        speed: Speed,
    ): ByteArray {
        val res = httpClient.post(buildTTSRequest(text, speaker, speed))
        val bytes = res.body<ByteArray>()

        if (bytes.isNotEmpty()) {
            return bytes
        } else {
            throw NoDataReceivedException()
        }
    }
}
