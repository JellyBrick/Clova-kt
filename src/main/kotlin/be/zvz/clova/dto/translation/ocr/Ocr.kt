package be.zvz.clova.dto.translation.ocr

import com.fasterxml.jackson.annotation.JsonProperty

data class Ocr(
    @JsonProperty("LT")
    val lt: Coordinate,
    @JsonProperty("LB")
    val lb: Coordinate,
    @JsonProperty("RT")
    val rt: Coordinate,
    @JsonProperty("RB")
    val rb: Coordinate,
    val lang: String,
    val text: String,
)
