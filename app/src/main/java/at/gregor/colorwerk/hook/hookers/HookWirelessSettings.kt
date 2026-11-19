package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.wirelesssettings.EnableWifiDetailsDisplayGateway
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookWirelessSettings : YukiBaseHooker() {
    override fun onHook() {

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->

            //启用WiFi详情显示网关
            if (preferences(ModulePrefs).getBoolean("enable_wifi_details_display_gateway", false)) {
                loadHooker(EnableWifiDetailsDisplayGateway(dexKitBridge))
            }

        }
    }
}