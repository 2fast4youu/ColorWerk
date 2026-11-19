package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * 软件商店页：去掉开屏页、更新下载页、我的页面应用推荐，以及默认展开更新列表。
 */
object OplusMarketPage {

    val spec = ScopePageSpec(
        pageKey = "oplus_market",
        prefsName = ModulePrefs,
        packName = "com.heytap.market",
        scopes = arrayOf("com.heytap.market"),
        restartEnabled = true,
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        switch(
            key = "remove_market_splash_page_app_recommend",
            title = c.getString(R.string.remove_market_splash_page_app_recommend),
        )
        switch(
            key = "remove_market_update_download_page_app_recommend",
            title = c.getString(R.string.remove_market_update_download_page_app_recommend),
        )
        switch(
            key = "remove_market_mine_page_app_recommend",
            title = c.getString(R.string.remove_market_mine_page_app_recommend),
        )
        switch(
            key = "default_expand_update_list",
            title = c.getString(R.string.default_expand_update_list),
        )
    }
}
