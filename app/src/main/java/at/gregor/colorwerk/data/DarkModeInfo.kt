package at.gregor.colorwerk.data

import kotlinx.serialization.Serializable
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
@Serializable
data class DarkModeInfo(
    var packName: String,
    var curType: Int = 0,
)