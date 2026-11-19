package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.keyguardpersonality.KeyGuardPersonalityRedMode
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookKeyguardPersonality : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        //锁屏红一
        if (osCode >= 40) loadHooker(KeyGuardPersonalityRedMode)

    }
}
