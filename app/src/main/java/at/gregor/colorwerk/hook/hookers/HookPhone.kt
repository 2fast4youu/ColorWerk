package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.globals.HookGlobalFeatureConfig
import at.gregor.colorwerk.hook.globals.HookGlobalSystemProperties
import at.gregor.colorwerk.hook.scopes.phone.ForceDisplaySIMSomeSwitch
import at.gregor.colorwerk.utils.DexkitUtils
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookPhone : YukiBaseHooker() {
    override fun onHook() {
        loadHooker(HookGlobalFeatureConfig)
        loadHooker(HookGlobalSystemProperties)

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //强制显示部分开关
            loadHooker(ForceDisplaySIMSomeSwitch(dexKitBridge))
        }
    }
}