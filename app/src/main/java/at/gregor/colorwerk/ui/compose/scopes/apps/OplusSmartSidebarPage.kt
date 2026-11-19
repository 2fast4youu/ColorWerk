package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.A12
import at.gregor.colorwerk.utils.A13
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.SDK
import at.gregor.colorwerk.utils.checkPackName
import at.gregor.colorwerk.utils.getOSVersionCode

/**
 * 智能侧边栏页：浮窗自动隐藏、中转站、最近文件、后台运行与应用数量上限的开关。
 * 整页要求 SDK >= Android 12 且装有 com.coloros.smartsidebar（见 spec.isVisible）。
 */
object OplusSmartSidebarPage {

    val spec = ScopePageSpec(
        pageKey = "oplus_smart_sidebar",
        prefsName = ModulePrefs,
        packName = "com.coloros.smartsidebar",
        scopes = arrayOf("com.coloros.smartsidebar"),
        isVisible = { SDK >= A12 && checkPackName("com.coloros.smartsidebar") },
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        val osCode = getOSVersionCode
        if (SDK == A12) {
            switch(
                key = "force_enable_buoy_automatically_hides",
                title = c.getString(R.string.force_enable_buoy_automatically_hides),
            )
        }
        if (SDK == A13) {
            switch(
                key = "unlock_transfer_dock",
                title = c.getString(R.string.unlock_transfer_dock),
            )
            switch(
                key = "unlock_recent_files",
                title = c.getString(R.string.unlock_recent_files),
            )
        }
        if (osCode >= 27) {
            switch(
                key = "enable_run_in_background",
                title = c.getString(R.string.enable_run_in_background),
                summary = if (osCode >= 37) c.getString(R.string.enable_run_in_background_summary) else null,
            )
        }
    }
}
