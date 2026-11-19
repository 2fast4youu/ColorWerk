package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.browser.RemoveAdsAtDownloadPageBottom
import at.gregor.colorwerk.hook.scopes.browser.RemoveAdsFromDownloadDialog
import at.gregor.colorwerk.hook.scopes.browser.RemoveBrowserSearchBarAppPromotion
import at.gregor.colorwerk.hook.scopes.browser.RemoveBrowserWindowLimitNumber
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookBrowser : YukiBaseHooker() {
    override fun onHook() {
        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //移除下载对话框广告
            if (preferences(ModulePrefs).getBoolean("remove_ads_from_download_dialog", false)) {
                loadHooker(RemoveAdsFromDownloadDialog(dexKitBridge))
            }
            //移除下载页面底部广告
            if (preferences(ModulePrefs).getBoolean("remove_ads_at_download_page_bottom", false)) {
                loadHooker(RemoveAdsAtDownloadPageBottom(dexKitBridge))
            }
            //移除浏览器窗口数量限制
            if (preferences(ModulePrefs).getBoolean("remove_browser_window_limit_number", false)) {
                loadHooker(RemoveBrowserWindowLimitNumber(dexKitBridge))
            }
            //移除浏览器搜索框App推广
            if (preferences(ModulePrefs).getBoolean("remove_browser_search_bar_app_promotion", false)) {
                loadHooker(RemoveBrowserSearchBarAppPromotion(dexKitBridge))
            }
        }
//        //移除天气页面广告
//        if (preferences(ModulePrefs).getBoolean("remove_ads_from_weather_page", false)) {
//            loadHooker(RemoveAdsFromWeatherPage)
//        }
    }
}