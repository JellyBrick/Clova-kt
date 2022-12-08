package be.zvz.clova.translation.text

import be.zvz.clova.LanguageSetting
import be.zvz.clova.dto.translation.text.TranslateResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedInputStream

abstract class Translate(
    protected val okHttpClient: OkHttpClient,
    private val objectMapper: ObjectMapper
) {
    abstract fun buildTranslateRequest(
        language: LanguageSetting,
        text: String,
        agreeToUsingTextData: Boolean,
        enableDictionary: Boolean
    ): Request

    fun translate(
        language: LanguageSetting,
        text: String,
        agreeToUsingTextData: Boolean,
        enableDictionary: Boolean
    ): TranslateResponse {
        okHttpClient.newCall(buildTranslateRequest(language, text, agreeToUsingTextData, enableDictionary))
            .execute()
            .use { response ->
                return BufferedInputStream(response.body.byteStream()).use(objectMapper::readValue)
            }
    }
}
