package be.zvz.clovatts

object Constants {
    const val TEXT_TO_SPEECH_ENDPOINT = "https://dict.naver.com/api/nvoice"

    val LANGUAGES = mapOf(
        Language.ENGLISH to "English",
        Language.KOREAN to "Korean",
        Language.JAPANESE to "Japanese",
        Language.CHINESE to "Chinese",
        Language.SPANISH to "Spanish",
    )

    val SPEAKERS = mapOf(
        Language.ENGLISH to Speakers(arrayOf(Speaker("matt")), arrayOf(Speaker("clara"))),
        Language.KOREAN to Speakers(arrayOf(Speaker("kyuri")), arrayOf(Speaker("jinho"))),
        Language.JAPANESE to Speakers(arrayOf(Speaker("shinji")), arrayOf(Speaker("yuri"), Speaker("nsayuri"))),
        Language.CHINESE to Speakers(arrayOf(Speaker("liangliang")), arrayOf(Speaker("meimei"))),
        Language.SPANISH to Speakers(arrayOf(Speaker("jose")), arrayOf(Speaker("carmen")))
    )

    class Speakers(
        val male: Array<Speaker>,
        val female: Array<Speaker>
    )

    data class Speaker(
        val name: String
    )
}
