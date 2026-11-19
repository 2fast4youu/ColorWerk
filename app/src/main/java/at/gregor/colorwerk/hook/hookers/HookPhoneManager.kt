package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.globals.HookGlobalFeatureConfig
import at.gregor.colorwerk.hook.scopes.phonemanager.RemoveCountdownAddVirusAppWhitelist
import at.gregor.colorwerk.hook.scopes.phonemanager.RemoveVirusRiskNotificationInPhoneManager
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookPhoneManager : YukiBaseHooker() {
    override fun onHook() {
        loadHooker(HookGlobalFeatureConfig)

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //移除手机管家发现病毒风险通知
            val removeVirusKey = "remove_virus_risk_notification_in_phone_manager"
            if (preferences(ModulePrefs).getBoolean(removeVirusKey, false)) {
                loadHooker(RemoveVirusRiskNotificationInPhoneManager(dexKitBridge))
            }
            //移除手机管家病毒App添加白名单倒计时
            val removeCountdownKey = "remove_countdown_add_virus_app_whitelist"
            if (preferences(ModulePrefs).getBoolean(removeCountdownKey, false)) {
                loadHooker(RemoveCountdownAddVirusAppWhitelist(dexKitBridge))
            }

        }
    }
}