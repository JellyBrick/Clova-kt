package be.zvz.clova.utils

import be.zvz.clova.Language

object Constants {
    object Url {
        const val CURRENT_TIME = "https://global.apis.naver.com/currentTime"
        const val PAPAGO = "https://apis.naver.com/papago/papago_app/"
    }

    const val API_VERSION = "1.10.7"
    const val KEY = "aVwDprJBYvnz1NBs8W7GBuaHQDeoynolGF5IdsxyYP6lyCzxAOG38hleJo43NnB6"
    const val USER_AGENT = "Translator/51.9 CFNetwork/1494.0.5 Darwin/23.4.0"

    val LANGUAGES = mapOf(
        Language.ENGLISH to "English",
        Language.KOREAN to "Korean",
        Language.JAPANESE to "Japanese",
        Language.TRADITIONAL_CHINESE to "Traditional Chinese",
        Language.SIMPLIFIED_CHINESE to "Simplified Chinese",
        Language.SPANISH to "Spanish",
        Language.FRENCH to "French",
        Language.DUTCH to "Dutch",
        Language.RUSSIAN to "Russian",
        Language.PORTUGUESE to "Portuguese",
        Language.ITALIAN to "Italian",
        Language.VIETNAMESE to "Vietnamese",
        Language.THAI to "Thai",
        Language.INDONESIAN to "Indonesian",
        Language.HINDI to "Hindi",
    )

    class Speakers(
        val male: Array<Speaker>,
        val female: Array<Speaker>,
    )

    data class Speaker(
        val name: String,
    )
}
