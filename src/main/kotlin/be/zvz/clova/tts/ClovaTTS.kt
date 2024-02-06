package be.zvz.clova.tts

import be.zvz.clova.Language
import be.zvz.clova.Speed
import be.zvz.clova.utils.Auth
import be.zvz.clova.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.formUrlEncode
import io.ktor.http.userAgent

class ClovaTTS(httpClient: HttpClient) : TextToSpeech(httpClient) {
    override fun buildTTSRequest(
        text: String,
        speaker: Constants.Speaker,
        speed: Speed,
        volume: Int,
    ): HttpRequestBuilder {
        val url = Constants.Url.PAPAGO + "tts/makeTTS"
        val sign = Auth.signUrl(httpClient, url)
        return HttpRequestBuilder().apply {
            url(Auth.toSignedUrl(url, sign))
            userAgent(Constants.USER_AGENT)
            method = HttpMethod.Post
            contentType(ContentType.Application.FormUrlEncoded)
            setBody(
                io.ktor.http.ParametersBuilder().apply {
                    append("text", text)
                    append("speaker", speaker.name)
                    append("speed", speed.code.toString())
                    append("volume", volume.toString())
                }.build().formUrlEncode(),
            )
        }
    }

    companion object {
        @JvmStatic
        val SPEAKERS =
            mapOf(
                Language.ENGLISH to
                    Constants.Speakers(
                        arrayOf(Constants.Speaker("matt")),
                        arrayOf(Constants.Speaker("clara")),
                    ),
                Language.KOREAN to
                    Constants.Speakers(
                        arrayOf(Constants.Speaker("jinho")),
                        arrayOf(Constants.Speaker("kyuri")),
                    ),
                Language.JAPANESE to
                    Constants.Speakers(
                        arrayOf(Constants.Speaker("shinji")),
                        arrayOf(Constants.Speaker("yuri"), Constants.Speaker("nsayuri")),
                    ),
                Language.TRADITIONAL_CHINESE to
                    Constants.Speakers(
                        arrayOf(Constants.Speaker("kuanlin")),
                        arrayOf(Constants.Speaker("chiahua")),
                    ),
                Language.SIMPLIFIED_CHINESE to
                    Constants.Speakers(
                        arrayOf(Constants.Speaker("liangliang")),
                        arrayOf(Constants.Speaker("meimei")),
                    ),
                Language.SPANISH to
                    Constants.Speakers(
                        arrayOf(Constants.Speaker("jose")),
                        arrayOf(Constants.Speaker("carmen")),
                    ),
                Language.FRENCH to
                    Constants.Speakers(
                        arrayOf(Constants.Speaker("louis")),
                        arrayOf(Constants.Speaker("roxane")),
                    ),
                Language.DUTCH to
                    Constants.Speakers(
                        arrayOf(Constants.Speaker("tim")),
                        arrayOf(Constants.Speaker("lena")),
                    ),
                Language.RUSSIAN to
                    Constants.Speakers(
                        arrayOf(Constants.Speaker("aleksei")),
                        arrayOf(Constants.Speaker("vera")),
                    ),
                // Language.PORTUGUESE to Speakers(arrayOf(), arrayOf()),
                // Language.ITALIAN to Speakers(arrayOf(), arrayOf()),
                // Language.VIETNAMESE to Speakers(arrayOf(), arrayOf()),
                Language.THAI to
                    Constants.Speakers(
                        arrayOf(Constants.Speaker("sarawut")),
                        arrayOf(Constants.Speaker("somsi")),
                    ),
                // Language.MALAY_INDONESIAN to Speakers(arrayOf(), arrayOf()),
                // Language.HINDI to Speakers(arrayOf(), arrayOf())
            )
    }
}
