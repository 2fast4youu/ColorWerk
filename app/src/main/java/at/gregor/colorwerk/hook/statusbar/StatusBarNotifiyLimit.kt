package at.gregor.colorwerk.hook.statusbar

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.systemui.AllowLongPressNotificationModifiable
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object StatusBarNotifiyLimit : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        //允许长按通知可修改
        if (preferences(ModulePrefs).getBoolean("allow_long_press_notification_modifiable", false)) {
            if (osCode <= 30) loadHooker(AllowLongPressNotificationModifiable)
        }
    }
}