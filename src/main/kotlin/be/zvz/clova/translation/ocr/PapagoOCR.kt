package be.zvz.clova.translation.ocr

import be.zvz.clova.Language
import be.zvz.clova.LanguageSetting
import be.zvz.clova.utils.Auth
import be.zvz.clova.utils.Constants
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.security.MessageDigest

class PapagoOCR(
    okHttpClient: OkHttpClient,
    objectMapper: ObjectMapper
) : OpticalCharacterRecognition(okHttpClient, objectMapper) {
    private fun sha512(source: ByteArray): String = MessageDigest
        .getInstance("SHA-512")
        .digest(source)
        .joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

    override fun buildOCRRequest(language: LanguageSetting, image: ByteArray): Request {
        val url = Constants.Url.PAPAGO + "ocr/detect"
        val sign = Auth.signUrl(okHttpClient, url)

        return Request.Builder()
            .url(Auth.urlToSignedUrl(url, sign))
            .header("User-Agent", Constants.USER_AGENT)
            .post(
                MultipartBody
                    .Builder()
                    .setType(MultipartBody.FORM)
                    // FIXME: imageId
                    /* .addPart(
                        Headers.Builder().addUnsafeNonAscii("Content-Disposition", "form-data;\r\nname=\"imageId\"").build(),
                        sha512(image).toRequestBody("text/plain".toMediaType())
                    ) */
                    .addFormDataPart("lang", language.source.code)
                    .addFormDataPart("source", language.source.code)
                    .addFormDataPart("target", language.target.code)
                    .addFormDataPart("langDetect", (language.source == Language.AUTO).toString())
                    .addFormDataPart(
                        "image",
                        "image",
                        image.toRequestBody("application/octet-stream".toMediaType(), 0, image.size)
                    )
                    .build()
            ).build()
    }
}
