package be.zvz.clova.dto.translation.text

import com.fasterxml.jackson.annotation.JsonProperty

data class Message(
    val result: Result,
    @JsonProperty("@service")
    val service: String,
    @JsonProperty("@version")
    val version: String,
    @JsonProperty("@type")
    val type: String,
)
