package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.market.DefaultExpandUpdateList
import at.gregor.colorwerk.hook.scopes.market.RemoveMarketMinePageAppRecommend
import at.gregor.colorwerk.hook.scopes.market.RemoveMarketSplashPageAppRecommend
import at.gregor.colorwerk.hook.scopes.market.RemoveMarketUpdateDownloadPageAppRecommend
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookMarket : YukiBaseHooker() {
    override fun onHook() {
        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //移除商店启动页应用推荐
            if (preferences(ModulePrefs).getBoolean("remove_market_splash_page_app_recommend", false)) {
                loadHooker(RemoveMarketSplashPageAppRecommend(dexKitBridge))
            }
            //移除商店更新下载页面应用推荐
            if (preferences(ModulePrefs).getBoolean("remove_market_update_download_page_app_recommend", false)) {
                loadHooker(RemoveMarketUpdateDownloadPageAppRecommend(dexKitBridge))
            }
            //移除软件商店我的页面应用推荐
            if (preferences(ModulePrefs).getBoolean("remove_market_mine_page_app_recommend", false)) {
                loadHooker(RemoveMarketMinePageAppRecommend(dexKitBridge))
            }
            //默认展开更新列表
            if (preferences(ModulePrefs).getBoolean("default_expand_update_list", false)) {
                loadHooker(DefaultExpandUpdateList(dexKitBridge))
            }
        }
    }
}