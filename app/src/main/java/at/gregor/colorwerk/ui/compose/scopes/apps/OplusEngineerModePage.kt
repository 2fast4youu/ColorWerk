package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * 工程模式页：解锁隐藏选项的开关。
 */
object OplusEngineerModePage {

    val spec = ScopePageSpec(
        pageKey = "oplus_engineer_mode",
        prefsName = ModulePrefs,
        packName = "com.oplus.engineermode",
        scopes = arrayOf("com.oplus.engineermode"),
        restartEnabled = true,
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        switch(
            key = "unlock_some_hidden_options",
            title = c.getString(R.string.unlock_some_hidden_options),
        )
    }
}
