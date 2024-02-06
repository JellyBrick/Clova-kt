package be.zvz.clova.translation.text

import be.zvz.clova.LanguageSetting
import be.zvz.clova.utils.Auth
import be.zvz.clova.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.ParametersBuilder
import io.ktor.http.contentType
import io.ktor.http.formUrlEncode
import io.ktor.http.userAgent

class N2MTTranslate(httpClient: HttpClient) : Translate(httpClient) {
    override fun buildTranslateRequest(
        language: LanguageSetting,
        text: String,
        agreeToUsingTextData: Boolean,
        enableDictionary: Boolean,
    ): HttpRequestBuilder = buildTranslateRequestWithOptionalParameters(language, text, agreeToUsingTextData, enableDictionary)

    fun buildTranslateRequestWithOptionalParameters(
        language: LanguageSetting,
        text: String,
        agreeToUsingTextData: Boolean,
        enableDictionary: Boolean,
        honorific: Boolean = false,
        instant: Boolean = false,
        dictDisplay: Int = -1,
    ): HttpRequestBuilder {
        val url = Constants.Url.PAPAGO + "n2mt/translate"
        val sign = Auth.signUrl(httpClient, url)
        return HttpRequestBuilder().apply {
            url(Auth.toSignedUrl(url, sign))
            userAgent(Constants.USER_AGENT)
            method = HttpMethod.Post
            contentType(ContentType.Application.FormUrlEncoded)
            setBody(
                ParametersBuilder().apply {
                    append("agree", agreeToUsingTextData.toString())
                    append("dict", enableDictionary.toString())
                    if (dictDisplay > -1) {
                        append("dictDisplay", dictDisplay.toString())
                    }
                    append("honorific", honorific.toString())
                    append("instant", instant.toString())
                    append("locale", "ko-Kore_KR")
                    append("reference", "KEYBOARD")
                    append("source", language.source.code)
                    append("srcTiltTar", "none")
                    append("tarTiltTar", language.source.code)
                    append("target", language.target.code)
                    append("text", text)
                    append("wsd", "false")
                }.build().formUrlEncode(),
            )
        }
    }
}
