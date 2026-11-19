package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.getOSVersionCode

/**
 * 互联页：强制开启与 iPhone 互通支持的开关。
 * 仅在 osCode >= 37 时渲染；改动需要重启系统。
 */
object OplusLinkerPage {

    val spec = ScopePageSpec(
        pageKey = "oplus_linker",
        prefsName = ModulePrefs,
        packName = "com.oplus.linker",
        scopes = arrayOf("com.oplus.linker", "com.android.contacts", "com.android.bluetooth"),
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        if (getOSVersionCode >= 37) {
            switch(
                key = "force_enable_iphone_shared_support",
                title = c.getString(R.string.force_enable_iphone_shared_support),
                summary = c.getString(R.string.need_restart_system),
            )
        }
    }
}
