package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.systemui.DisableSysUIOTGAutoOff
import at.gregor.colorwerk.hook.scopes.systemui.RemovePowerMenuSOSButton
import at.gregor.colorwerk.hook.scopes.systemui.ShowChargingRipple
import at.gregor.colorwerk.hook.scopes.systemui.ShowManualLockButtonPowerMenu
import at.gregor.colorwerk.utils.A12
import at.gregor.colorwerk.utils.A13
import at.gregor.colorwerk.utils.A14
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.SDK
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookSystemUiMiscellaneous : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        //充电纹波
        if (preferences(ModulePrefs).getBoolean("show_charging_ripple", false)) {
            if (SDK >= A12) loadHooker(ShowChargingRipple)
        }
        //禁用OTG自动关闭
        if (preferences(ModulePrefs).getBoolean("disable_otg_auto_off", false)) {
            if (osCode < 30) loadHooker(DisableSysUIOTGAutoOff)
        }
        //电源菜单显示手动锁定按钮
        if (preferences(ModulePrefs).getBoolean("show_manual_lock_button_power_menu", false)) {
            if (SDK >= A14) loadHooker(ShowManualLockButtonPowerMenu)
        }
        //移除电源菜单SOS按钮
        if (preferences(ModulePrefs).getBoolean("remove_power_menu_sos_button", false)) {
            if (SDK >= A13) loadHooker(RemovePowerMenuSOSButton)
        }
    }
}