package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.mediacontroller.ForceEnableMediaMusicFluidCloudRipple
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookMediaController : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        //强制启用媒体音乐流体云波纹
        if (osCode >= 33) loadHooker(ForceEnableMediaMusicFluidCloudRipple)
    }
}