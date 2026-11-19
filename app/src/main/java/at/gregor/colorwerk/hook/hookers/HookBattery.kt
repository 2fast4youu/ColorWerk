package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.globals.HookGlobalFeatureConfig
import at.gregor.colorwerk.hook.globals.HookGlobalFeatureProvider
import at.gregor.colorwerk.hook.scopes.battery.DisplayModuleCalculatesBatteryHealthData
import at.gregor.colorwerk.hook.scopes.battery.HookBatteryNotify
import at.gregor.colorwerk.hook.scopes.battery.KeepGoogleServicesNetwork
import at.gregor.colorwerk.hook.scopes.battery.LauncherHighTempreatureProtection
import at.gregor.colorwerk.hook.scopes.battery.RemoveBatteryRestrictPlugin
import at.gregor.colorwerk.hook.scopes.battery.RemoveBatteryTemperatureControl
import at.gregor.colorwerk.hook.scopes.battery.UnlockStartupLimit
import at.gregor.colorwerk.utils.A13
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.SDK
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookBattery : YukiBaseHooker() {
    override fun onHook() {
        loadHooker(HookGlobalFeatureConfig)

        if (SDK < A13) try {
            DexkitUtils.create(appInfo.sourceDir).close()
        } catch (_: UnsatisfiedLinkError) {
            return
        }

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            loadHooker(HookGlobalFeatureProvider(dexKitBridge))
            //电池通知
            loadHooker(HookBatteryNotify(dexKitBridge))
            if (preferences(ModulePrefs).getBoolean("unlock_startup_limit", false)) {
                if (SDK >= A13) loadHooker(UnlockStartupLimit(dexKitBridge))
            }
            //移除电池温度控制
            if (preferences(ModulePrefs).getBoolean("remove_battery_temperature_control", false)) {
                loadHooker(RemoveBatteryTemperatureControl(dexKitBridge))
                loadHooker(LauncherHighTempreatureProtection(dexKitBridge))
            }
            //移除电池限制插件
            if (preferences(ModulePrefs).getBoolean("remove_battery_restrict_plugin", false)) {
                loadHooker(RemoveBatteryRestrictPlugin(dexKitBridge))
            }
        }

        //阻止电池组件在 Google 连通性探测失败后给 Google 服务禁网
        if (preferences(ModulePrefs).getBoolean("keep_google_services_network", false)) {
            loadHooker(KeepGoogleServicesNetwork)
        }

        //显示模块计算电池健康数据
        if (preferences(ModulePrefs).getBoolean("open_battery_health", false)) {
            if (SDK >= A13) loadHooker(DisplayModuleCalculatesBatteryHealthData)
        }
    }
}