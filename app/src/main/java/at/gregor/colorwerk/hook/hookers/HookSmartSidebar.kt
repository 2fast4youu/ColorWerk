package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.globals.HookGlobalFeatureConfig
import at.gregor.colorwerk.hook.scopes.smartsidebar.EnableRunInBackground
import at.gregor.colorwerk.hook.scopes.smartsidebar.ForceEnableBuoyAutomaticallyHides
import at.gregor.colorwerk.hook.scopes.smartsidebar.HookFeatureOption
import at.gregor.colorwerk.hook.utils.getAppVerInfo
import at.gregor.colorwerk.utils.A12
import at.gregor.colorwerk.utils.A13
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.SDK
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookSmartSidebar : YukiBaseHooker() {
    override fun onHook() {
        loadHooker(HookGlobalFeatureConfig)

        val osCode = getOSVersionCode
        val appVer = preferences(ModulePrefs).getAppVerInfo(packageName)


        val v14 = appVer?.versionCode?.let { it >= 14000000 } ?: false

        //HookFeatureOption
        if (SDK == A13 && v14) loadHooker(HookFeatureOption)

        //强制启用浮标自动隐藏
        if (preferences(ModulePrefs).getBoolean("force_enable_buoy_automatically_hides", false)) {
            if (SDK == A12) loadHooker(ForceEnableBuoyAutomaticallyHides)
        }

        //启用后台挂机
        if (preferences(ModulePrefs).getBoolean("enable_run_in_background", false)) {
            if (osCode >= 27) loadHooker(EnableRunInBackground)
        }
    }
}