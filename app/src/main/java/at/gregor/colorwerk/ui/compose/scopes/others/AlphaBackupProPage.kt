package at.gregor.colorwerk.ui.compose.scopes.others

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * Alpha Backup Pro 页：Pro 授权检查的屏蔽开关（写入 com.ruet_cse_1503050.ragib.appbackup.pro）。
 */
object AlphaBackupProPage {

    val spec = ScopePageSpec(
        pageKey = "alpha_backup_pro",
        prefsName = ModulePrefs,
        packName = "com.ruet_cse_1503050.ragib.appbackup.pro",
        scopes = arrayOf("com.ruet_cse_1503050.ragib.appbackup.pro"),
        restartEnabled = true,
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        switch(
            key = "remove_check_license",
            title = c.getString(R.string.remove_pro_license),
        )
    }
}
