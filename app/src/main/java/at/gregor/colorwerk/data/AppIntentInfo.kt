package at.gregor.colorwerk.data

import at.gregor.colorwerk.enums.IntentType
import kotlinx.serialization.Serializable
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
@Serializable
data class AppIntentInfo(
    var name: String,
    var packName: String,
    var action: String,
    var activity: String,
    var type: IntentType
)