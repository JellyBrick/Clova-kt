package be.zvz.clova.translation.ocr

import be.zvz.clova.LanguageSetting
import be.zvz.clova.dto.translation.ocr.DetectResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedInputStream

abstract class OpticalCharacterRecognition(
    protected val okHttpClient: OkHttpClient,
    private val objectMapper: ObjectMapper,
) {
    abstract fun buildOCRRequest(
        language: LanguageSetting,
        image: ByteArray,
    ): Request

    fun doOCR(
        language: LanguageSetting,
        image: ByteArray,
    ): DetectResponse {
        okHttpClient.newCall(buildOCRRequest(language, image))
            .execute()
            .use { response ->
                return BufferedInputStream(response.body.byteStream()).use(objectMapper::readValue)
            }
    }
}
