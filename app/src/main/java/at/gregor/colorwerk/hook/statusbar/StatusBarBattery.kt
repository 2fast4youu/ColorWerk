package at.gregor.colorwerk.hook.statusbar

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.systemui.StatusBarBatteryInfoNotify
import at.gregor.colorwerk.hook.scopes.systemui.StatusBarBatteryView
import at.gregor.colorwerk.utils.A12
import at.gregor.colorwerk.utils.SDK
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object StatusBarBattery : YukiBaseHooker() {
    override fun onHook() {
        //电池图标
        loadHooker(StatusBarBatteryView)

        //电池信息通知
        if (SDK >= A12) loadHooker(StatusBarBatteryInfoNotify)
    }
}