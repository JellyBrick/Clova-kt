package be.zvz.clova.translation.ocr

import be.zvz.clova.LanguageSetting
import be.zvz.clova.dto.translation.ocr.DetectResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post

abstract class OpticalCharacterRecognition(
    protected val httpClient: HttpClient,
) {
    abstract fun buildOCRRequest(
        language: LanguageSetting,
        image: ByteArray,
    ): HttpRequestBuilder

    suspend fun doOCR(
        language: LanguageSetting,
        image: ByteArray,
    ): DetectResponse {
        return httpClient.post(buildOCRRequest(language, image)).body()
    }
}
