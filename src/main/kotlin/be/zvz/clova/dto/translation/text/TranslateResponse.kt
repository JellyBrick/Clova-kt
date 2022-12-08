package be.zvz.clova.dto.translation.text

data class TranslateResponse(
    val message: Message? = null,
    val errorCode: String? = null,
    val errorMessage: String? = null
)
