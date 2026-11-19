package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * NFC 页：扫描到 NFC 标签时自动点击的开关，改动需要重启系统。
 */
object OplusNfcPage {

    val spec = ScopePageSpec(
        pageKey = "oplus_nfc",
        prefsName = ModulePrefs,
        packName = "com.android.nfc",
        scopes = arrayOf("com.android.nfc"),
        restartEnabled = true,
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        switch(
            key = "scan_nfc_tag_auto_click",
            title = c.getString(R.string.scan_nfc_tag_auto_click),
            summary = c.getString(R.string.need_restart_system),
        )
    }
}
