package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.keyguardclock.KeyGuardcLockRedMode
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookKeyguardClock : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //锁屏时钟
            if (osCode < 40) loadHooker(KeyGuardcLockRedMode(dexKitBridge))
        }

    }
}
