package at.gregor.colorwerk.hook.statusbar

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.systemui.DoubleClickLockScreen
import at.gregor.colorwerk.hook.scopes.systemui.VibrateWhenOpeningTheStatusBar
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object StatusBarUI : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        //双击状态栏锁屏
        if (preferences(ModulePrefs).getBoolean("statusbar_double_click_lock_screen", false)) {
            loadHooker(DoubleClickLockScreen)
        }
        //打开状态栏时振动
        if (preferences(ModulePrefs).getBoolean("vibrate_when_opening_the_statusbar", false)) {
            if (osCode >= 26) loadHooker(VibrateWhenOpeningTheStatusBar)
        }
    }
}