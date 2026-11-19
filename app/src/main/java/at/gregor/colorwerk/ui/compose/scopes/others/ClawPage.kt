package at.gregor.colorwerk.ui.compose.scopes.others

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * 小布空间（Claw）页：root 检测的屏蔽开关（写入 com.oplus.claw）。
 */
object ClawPage {

    val spec = ScopePageSpec(
        pageKey = "claw",
        prefsName = ModulePrefs,
        packName = "com.oplus.claw",
        scopes = arrayOf("com.oplus.claw"),
        restartEnabled = true,
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        switch(
            key = "remove_root_detection",
            title = c.getString(R.string.remove_root_detection),
            summary = c.getString(R.string.remove_root_detection_summary),
        )
    }
}
