package be.zvz.clova.dto.translation.ocr

data class DetectResponse(
    val ocrs: List<Ocr>?,
    val source: String?,
    val imageId: String?,
    val target: String?,
    val renderedImage: String?,
    val errorCode: String?,
    val errorMessage: String?
)
