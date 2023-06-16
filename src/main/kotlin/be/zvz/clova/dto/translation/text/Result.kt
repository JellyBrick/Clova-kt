package be.zvz.clova.dto.translation.text

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty

data class Result(
    val pivot: String? = null,
    @JsonProperty("dict")
    val dictionary: Dictionary?,
    @JsonProperty("tarDict")
    val targetDictionary: Dictionary?,
    val delaySmt: Int,
    @JsonProperty("tlitSrc")
    @JsonAlias("tlit")
    val tlitSrc: TlitSrc,
    val delay: Int,
    @JsonProperty("srcLangType")
    val sourceLanguageType: String,
    @JsonProperty("tarLangType")
    val targetLanguageType: String,
    val translatedText: String,
    val engineType: String,
    @JsonProperty("modelVer")
    val modelVersion: String?,
)
