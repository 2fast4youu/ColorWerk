package at.gregor.colorwerk.hook.scopes.android

import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.extension.kavaref.hook
import com.highcapable.yukihookapi.extension.kavaref.toClass
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.utils.A12
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.SDK
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object ReplaceSystemRootStateDetection : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode
        val isEnable = preferences(ModulePrefs).getBoolean("replace_system_root_state_detection", false)
        if (SDK < A12 || !isEnable) return

        //Source HeimdallService
        if (osCode > 26) "com.android.server.oplus.heimdall.HeimdallService".toClass().resolve().apply {
            firstMethod { name = "isRootEnable" }.hook {
                intercept(false)
            }
        }

        //Source RootService
        "com.android.server.oplus.heimdall.service.RootService".toClass().resolve().apply {
            firstMethod { name = "isRoot" }.hook {
                intercept(false)
            }
        }

        //Source HeimdallService
        "com.android.server.oplus.heimdall.root.RootDetector".toClass().resolve().apply {
            firstMethod { name = "checkDeviceRootStatus" }.hook {
                intercept()
            }
        }
    }
}