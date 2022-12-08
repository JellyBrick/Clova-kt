package be.zvz.clova.dto.translation.text

data class Item(
    val subEntry: String?,
    val pos: List<Pos>,
    val entry: String,
    val matchType: String,
    val phoneticSigns: List<PhoneticSign>?,
    val url: String,
    val source: String,
    val locale: String,
    val mUrl: String,
    val gdid: String,
    val hanjaEntry: String?,
    val expDicTypeForm: String?,
    val expEntrySuperscript: String?
)
