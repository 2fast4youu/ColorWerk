package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * 健康页：去掉 Root 检测提示对话框的开关。
 */
object OplusHealthPage {

    val spec = ScopePageSpec(
        pageKey = "oplus_health",
        prefsName = ModulePrefs,
        packName = "com.heytap.health",
        scopes = arrayOf("com.heytap.health"),
        restartEnabled = true,
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        switch(
            key = "remove_health_root_check_dialog",
            title = c.getString(R.string.remove_health_root_check_dialog),
        )
    }
}
