package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * 无线设置页：WiFi 详情页显示网关的开关。
 */
object OplusWirelessSettingsPage {

    val spec = ScopePageSpec(
        pageKey = "oplus_wireless_settings",
        prefsName = ModulePrefs,
        packName = "com.oplus.wirelesssettings",
        scopes = arrayOf("com.oplus.wirelesssettings"),
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        switch(
            key = "enable_wifi_details_display_gateway",
            title = c.getString(R.string.enable_wifi_details_display_gateway),
        )
    }
}
