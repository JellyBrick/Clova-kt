package be.zvz.clova.translation.ocr

import be.zvz.clova.Language
import be.zvz.clova.LanguageSetting
import be.zvz.clova.utils.Auth
import be.zvz.clova.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.userAgent
import java.security.MessageDigest

class PapagoOCR(
    httpClient: HttpClient,
) : OpticalCharacterRecognition(httpClient) {
    private fun sha512(source: ByteArray): String =
        MessageDigest
            .getInstance("SHA-512")
            .digest(source)
            .joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

    override fun buildOCRRequest(
        language: LanguageSetting,
        image: ByteArray,
    ): HttpRequestBuilder {
        val url = Constants.Url.PAPAGO + "ocr/detect"
        val sign = Auth.signUrl(httpClient, url)

        val isSourceAuto = language.source == Language.AUTO

        return HttpRequestBuilder().apply {
            method = io.ktor.http.HttpMethod.Post
            url(Auth.toSignedUrl(url, sign))
            userAgent(Constants.USER_AGENT)
            setBody(MultiPartFormDataContent(
                formData {
                    if (isSourceAuto) {
                        append("lang", "all")
                        append("source", language.target.code)
                    } else {
                        append("lang", language.source.code)
                        append("source", language.source.code)
                    }
                    append("target", language.target.code)
                    append("langDetect", isSourceAuto.toString())
                    append(
                        "image",
                        image.inputStream().buffered().use {
                            it.readBytes()
                        },
                        Headers.build {
                            append(HttpHeaders.ContentType, ContentType.Application.OctetStream.toString())
                            append(HttpHeaders.ContentDisposition, "filename=image")
                        }
                    )
                }
            ))
            // FIXME: imageId
            /**
             * .addPart(
             *   Headers.Builder().addUnsafeNonAscii("Content-Disposition", "form-data;\r\nname=\"imageId\"").build(),
             *   sha512(image).toRequestBody("text/plain".toMediaType())
             * )
             */
        }
    }
}
