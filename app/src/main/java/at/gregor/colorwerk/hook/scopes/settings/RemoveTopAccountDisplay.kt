package at.gregor.colorwerk.hook.scopes.settings

import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.extension.kavaref.hook
import com.highcapable.yukihookapi.extension.kavaref.toClass
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.utils.A13
import at.gregor.colorwerk.utils.SDK
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object RemoveTopAccountDisplay : YukiBaseHooker() {
    override fun onHook() {
        //Source UserPreferenceController
        "com.oplus.settings.feature.homepage.user.UserPreferenceController".toClass().resolve()
            .apply {
                firstMethod {
                    name = if (SDK >= A13) "checkAvailable"
                    else "getAvailabilityStatus"
                }.hook {
                    if (SDK >= A13) {
                        intercept(false)
                    } else {
                        intercept(3)
                    }
                }
            }
    }
}