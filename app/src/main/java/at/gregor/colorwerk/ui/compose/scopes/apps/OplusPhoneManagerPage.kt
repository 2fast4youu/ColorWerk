package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * 手机管家页：去掉病毒风险通知与加入白名单倒计时的开关。
 */
object OplusPhoneManagerPage {

    val spec = ScopePageSpec(
        pageKey = "oplus_phone_manager",
        prefsName = ModulePrefs,
        packName = "com.coloros.phonemanager",
        scopes = arrayOf("com.coloros.phonemanager"),
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        switch(
            key = "remove_virus_risk_notification_in_phone_manager",
            title = c.getString(R.string.remove_virus_risk_notification_in_phone_manager),
        )
        switch(
            key = "remove_countdown_add_virus_app_whitelist",
            title = c.getString(R.string.remove_countdown_add_virus_app_whitelist),
        )
    }
}
