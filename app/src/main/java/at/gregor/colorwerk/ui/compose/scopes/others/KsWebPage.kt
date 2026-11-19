package at.gregor.colorwerk.ui.compose.scopes.others

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * KSWeb 页：Pro 授权检查的屏蔽开关（写入 ru.kslabs.ksweb）。
 */
object KsWebPage {

    val spec = ScopePageSpec(
        pageKey = "ks_web",
        prefsName = ModulePrefs,
        packName = "ru.kslabs.ksweb",
        scopes = arrayOf("ru.kslabs.ksweb"),
        restartEnabled = true,
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        switch(
            key = "ksweb_remove_check_license",
            title = c.getString(R.string.remove_pro_license),
        )
    }
}
