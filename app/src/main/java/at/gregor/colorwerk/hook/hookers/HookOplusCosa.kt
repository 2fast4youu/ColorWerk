package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.globals.HookGlobalFeatureConfig
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookOplusCosa : YukiBaseHooker() {
    override fun onHook() {
        loadHooker(HookGlobalFeatureConfig)

        val osCode = getOSVersionCode


        //启用旁路供电支持
//        if (preferences(ModulePrefs).getBoolean("enable_game_bypass_charging_support", false)) {
//            if (osCode >= 33) loadHooker(EnableGameBypassChargingSupport)
//        }
    }
}