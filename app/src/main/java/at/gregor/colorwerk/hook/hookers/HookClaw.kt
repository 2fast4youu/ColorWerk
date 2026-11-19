package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.globals.HookGlobalSystemProperties
import at.gregor.colorwerk.hook.scopes.claw.RemoveRootDetection
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookClaw : YukiBaseHooker() {
    override fun onHook() {
        loadHooker(HookGlobalSystemProperties)

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->

            //移除Root检测
            if (preferences(ModulePrefs).getBoolean("remove_root_detection", false)) {
                loadHooker(RemoveRootDetection(dexKitBridge))
            }

        }

    }
}
