package at.gregor.colorwerk.hook.statusbar

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.systemui.ControllerCenterSliderTransparency
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object StatusBarSilder : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        //控制中心滑动条透明度
        if (osCode in 26..33) loadHooker(ControllerCenterSliderTransparency)

    }
}