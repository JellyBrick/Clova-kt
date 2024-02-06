package be.zvz.clova.translation.text

import be.zvz.clova.LanguageSetting
import be.zvz.clova.dto.translation.text.TranslateResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post

abstract class Translate(
    protected val httpClient: HttpClient
) {
    abstract fun buildTranslateRequest(
        language: LanguageSetting,
        text: String,
        agreeToUsingTextData: Boolean,
        enableDictionary: Boolean,
    ): HttpRequestBuilder

    suspend fun translate(
        language: LanguageSetting,
        text: String,
        agreeToUsingTextData: Boolean,
        enableDictionary: Boolean,
    ): TranslateResponse {
        return httpClient.post(buildTranslateRequest(language, text, agreeToUsingTextData, enableDictionary)).body()
    }
}
