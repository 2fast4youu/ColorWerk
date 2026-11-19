package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.globals.HookGlobalFeatureConfig
import at.gregor.colorwerk.hook.scopes.safecenter.UnlockStartupLimitOld
import at.gregor.colorwerk.utils.A13
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.SDK
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookSafeCenter : YukiBaseHooker() {
    override fun onHook() {
        loadHooker(HookGlobalFeatureConfig)

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //移除自启数量限制
            if (SDK < A13 && preferences(ModulePrefs).getBoolean("unlock_startup_limit", false)) {
                loadHooker(UnlockStartupLimitOld(dexKitBridge))
            }
        }
    }
}