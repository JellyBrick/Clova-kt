package be.zvz.clovatts

import be.zvz.clovatts.exception.NoDataReceivedException
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class Clova @JvmOverloads constructor(
    private val okHttpClient: OkHttpClient = OkHttpClient
        .Builder()
        .build(),
) {
    fun buildTTSRequest(
        text: String,
        speaker: Constants.Speaker,
        speed: Speed
    ): Request = Request.Builder()
        .url(Constants.TEXT_TO_SPEECH_ENDPOINT)
        .header("Referer", "https://dict.naver.com")
        .post(
            FormBody
                .Builder()
                .addEncoded("service", "dictionary")
                .addEncoded("speech_fmt", "mp3")
                .add("text", text)
                .addEncoded("speaker", speaker.name)
                .addEncoded("speed", speed.code.toString())
                .build()
        ).build()

    fun getSpeech(
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
