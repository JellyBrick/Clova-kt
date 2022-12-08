package be.zvz.clova.tts

import be.zvz.clova.Language
import be.zvz.clova.Speed
import be.zvz.clova.utils.Auth
import be.zvz.clova.utils.Constants
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class ClovaTTS(okHttpClient: OkHttpClient) : TextToSpeech(okHttpClient) {
    override fun buildTTSRequest(
        text: String,
        speaker: Constants.Speaker,
        speed: Speed,
        volume: Int
    ): Request {
        val url = Constants.Url.PAPAGO + "tts/makeTTS"
        val sign = Auth.signUrl(okHttpClient, url)
        return Request.Builder()
            .url(Auth.urlToSignedUrl(url, sign))
            .header("User-Agent", Constants.USER_AGENT)
            .post(
                FormBody
                    .Builder()
                    .add("text", text)
                    .addEncoded("speaker", speaker.name)
                    .addEncoded("speed", speed.code.toString())
                    .addEncoded("volume", volume.toString())
                    .build()
            ).build()
    }

    companion object {
        @JvmStatic
        val SPEAKERS = mapOf(
            Language.ENGLISH to Constants.Speakers(
                arrayOf(Constants.Speaker("matt")),
                arrayOf(Constants.Speaker("clara"))
            ),
            Language.KOREAN to Constants.Speakers(
                arrayOf(Constants.Speaker("jinho")),
                arrayOf(Constants.Speaker("kyuri"))
            ),
            Language.JAPANESE to Constants.Speakers(
                arrayOf(Constants.Speaker("shinji")),
                arrayOf(Constants.Speaker("yuri"), Constants.Speaker("nsayuri"))
            ),
            Language.TRADITIONAL_CHINESE to Constants.Speakers(
                arrayOf(Constants.Speaker("kuanlin")),
                arrayOf(Constants.Speaker("chiahua"))
            ),
            Language.SIMPLIFIED_CHINESE to Constants.Speakers(
                arrayOf(Constants.Speaker("liangliang")),
                arrayOf(Constants.Speaker("meimei"))
            ),
            Language.SPANISH to Constants.Speakers(
                arrayOf(Constants.Speaker("jose")),
                arrayOf(Constants.Speaker("carmen"))
            ),
            Language.FRANCE to Constants.Speakers(
                arrayOf(Constants.Speaker("louis")),
                arrayOf(Constants.Speaker("roxane"))
            ),
            Language.DEUTSCH to Constants.Speakers(
                arrayOf(Constants.Speaker("tim")),
                arrayOf(Constants.Speaker("lena"))
            ),
            Language.RUSSIAN to Constants.Speakers(
                arrayOf(Constants.Speaker("aleksei")),
                arrayOf(Constants.Speaker("vera"))
            ),
            // Language.PORTUGUESE to Speakers(arrayOf(), arrayOf()),
            // Language.ITALIAN to Speakers(arrayOf(), arrayOf()),
            // Language.VIETNAMESE to Speakers(arrayOf(), arrayOf()),
            Language.THAI to Constants.Speakers(
                arrayOf(Constants.Speaker("sarawut")),
                arrayOf(Constants.Speaker("somsi"))
            )
            // Language.MALAY_INDONESIAN to Speakers(arrayOf(), arrayOf()),
            // Language.HINDI to Speakers(arrayOf(), arrayOf())
        )
    }
}
