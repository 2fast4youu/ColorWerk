package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * 系统消息页：系统消息的区域默认值取值。
 */
object OplusMcsPage {

    val spec = ScopePageSpec(
        pageKey = "oplus_mcs",
        prefsName = ModulePrefs,
        packName = "com.heytap.mcs",
        scopes = arrayOf("com.heytap.mcs"),
        restartEnabled = true,
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        list(
            key = "custom_system_message_region_defaults",
            title = c.getString(R.string.custom_system_message_region_defaults),
            entries = c.resources.getStringArray(
                R.array.custom_system_message_region_defaults_entries
            ),
            entryValues = arrayOf("", "CN", "IN", "US"),
            default = "",
            summary = c.getString(R.string.current_mode) + ": %s",
        )
    }
}
