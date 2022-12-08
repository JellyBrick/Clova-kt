package be.zvz.clova.dto.translation.text

data class Dictionary(
    val items: List<Item>,
    val examples: List<Example>?,
    val isWordType: Boolean
)
