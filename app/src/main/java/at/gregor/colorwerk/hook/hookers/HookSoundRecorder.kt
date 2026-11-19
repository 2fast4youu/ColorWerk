package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.globals.HookGlobalSystemProperties
import at.gregor.colorwerk.hook.scopes.soundrecorder.HookBaseUtil
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookSoundRecorder : YukiBaseHooker() {
    override fun onHook() {
        loadHooker(HookGlobalSystemProperties)

        val osCode = getOSVersionCode


        //启用三方应用通话录音
        if (preferences(ModulePrefs).getBoolean("enable_record_calls_on_third_party_apps", false)) {
            if (osCode == 30) loadHooker(HookBaseUtil)
        }
    }
}