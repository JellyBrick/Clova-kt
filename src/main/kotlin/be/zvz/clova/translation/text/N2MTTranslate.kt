package be.zvz.clova.translation.text

import be.zvz.clova.LanguageSetting
import be.zvz.clova.utils.Auth
import be.zvz.clova.utils.Constants
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class N2MTTranslate(okHttpClient: OkHttpClient, objectMapper: ObjectMapper) : Translate(okHttpClient, objectMapper) {
    override fun buildTranslateRequest(
        language: LanguageSetting,
        text: String,
        agreeToUsingTextData: Boolean,
        enableDictionary: Boolean
    ): Request {
        val url = Constants.Url.PAPAGO + "n2mt/translate"
        val sign = Auth.signUrl(okHttpClient, url)
        return Request.Builder()
            .url(Auth.urlToSignedUrl(url, sign))
            .header("User-Agent", Constants.USER_AGENT)
            .post(
                FormBody
                    .Builder()
                    .addEncoded("agree", agreeToUsingTextData.toString())
                    .addEncoded("dict", enableDictionary.toString())
                    .addEncoded("locale", "ko-Kore_KR")
                    .addEncoded("reference", "KEYBOARD")
                    .addEncoded("source", language.source.code)
                    .addEncoded("srcTiltTar", "none")
                    .addEncoded("tarTiltTar", language.source.code)
                    .addEncoded("target", language.target.code)
                    .add("text", text)
                    .addEncoded("wsd", "false")
                    .build()
            ).build()
    }
}
