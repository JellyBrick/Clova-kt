package be.zvz.clova.dto.translation.text

data class Meaning(
    val ruby: Ruby? = null,
    val meaning: String,
    val originalMeaning: String?,
    val examples: List<Example>?
)
