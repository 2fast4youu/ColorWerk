package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.audiomonitor.HookVoipRecorderService
import at.gregor.colorwerk.hook.scopes.audiomonitor.VoipRecorderWhitelist
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookAudioMonitor : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        //启用三方应用通话录音
        if (preferences(ModulePrefs).getBoolean("enable_record_calls_on_third_party_apps", false)) {
            if (osCode == 30) loadHooker(HookVoipRecorderService)
        }

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //扩展第三方通话录音白名单
            if (preferences(ModulePrefs).getBoolean("expand_voip_recorder_whitelist", false)) {
                if (osCode >= 31) loadHooker(VoipRecorderWhitelist(dexKitBridge))
            }
        }
    }
}