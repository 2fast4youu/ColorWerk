package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.weather.Enable15DayWeatherExpandList
import at.gregor.colorwerk.hook.scopes.weather.RestoreRainfallCloudMapPage
import at.gregor.colorwerk.hook.scopes.weather.WeatherAdsAndJumpBrowser
import at.gregor.colorwerk.hook.utils.getAppVerInfo
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookWeather : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode
        val appVer = preferences(ModulePrefs).getAppVerInfo(packageName)

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //天气广告与跳转浏览器
            loadHooker(WeatherAdsAndJumpBrowser(appVer, dexKitBridge))
        }

        //启用15日天气展开列表
        if (preferences(ModulePrefs).getBoolean("enable_15_day_weather_expand_list", false)) {
            loadHooker(Enable15DayWeatherExpandList)
        }
        //恢复降雨云图页面
        if (preferences(ModulePrefs).getBoolean("restore_rainfall_cloud_map_page", false)) {
            if (osCode < 34) loadHooker(RestoreRainfallCloudMapPage)
        }
    }
}