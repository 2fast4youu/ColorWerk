package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.uiengine.EnableRandomTextOnAod
import at.gregor.colorwerk.hook.scopes.uiengine.RemoveAodNotificationWhitelist
import at.gregor.colorwerk.hook.scopes.uiengine.SetAodNotificationIconStyle
import at.gregor.colorwerk.hook.scopes.uiengine.SetAodTypefaceMode
import at.gregor.colorwerk.utils.A13
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.SDK
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookUIEngine : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        //移除通知图标白名单
        if (preferences(ModulePrefs).getBoolean("remove_aod_notification_icon_whitelist", false)) {
            if (SDK == A13) loadHooker(RemoveAodNotificationWhitelist)
        }

        //设置息屏样式模式
        if (SDK >= A13) loadHooker(SetAodNotificationIconStyle)

        //启用息屏随机一言
        if (osCode >= 26) loadHooker(EnableRandomTextOnAod)

        //设置息屏字体模式
        if (osCode >= 26) loadHooker(SetAodTypefaceMode)

    }
}