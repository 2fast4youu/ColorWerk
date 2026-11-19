package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * OShare 页：去掉互传关闭倒计时的开关。
 */
object OplusOSharePage {

    val spec = ScopePageSpec(
        pageKey = "oplus_oshare",
        prefsName = ModulePrefs,
        packName = "com.coloros.oshare",
        scopes = arrayOf("com.coloros.oshare"),
        restartEnabled = true,
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        switch(
            key = "remove_oshare_close_countdown",
            title = c.getString(R.string.remove_oshare_close_countdown),
        )
    }
}
