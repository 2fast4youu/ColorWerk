package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.speechassist.ForceEnableAISpeechAssistCall
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookSpeechAssist : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        //强制启用小布通话
        if (preferences(ModulePrefs).getBoolean("force_enable_ai_speechassist_call", false)) {
            if (osCode >= 30) loadHooker(ForceEnableAISpeechAssistCall)
        }
    }
}