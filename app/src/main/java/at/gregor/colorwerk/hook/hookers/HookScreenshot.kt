package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.screenshot.CustomizeLongScreenshotMaxCapturedPages
import at.gregor.colorwerk.hook.scopes.screenshot.DisableScreenshotPackageNameMd5Encrypt
import at.gregor.colorwerk.hook.scopes.screenshot.EnablePNGSaveFormat
import at.gregor.colorwerk.hook.utils.getAppVerInfo
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookScreenshot : YukiBaseHooker() {
    override fun onHook() {
        val appVer = preferences(ModulePrefs).getAppVerInfo(packageName)

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //移除长截图页数限制
            if (preferences(ModulePrefs).getBoolean("remove_page_limit_for_long_screenshots", false)) {
                val exist = appVer?.versionCode?.let { it > 130005000 } ?: false
                if (exist) loadHooker(CustomizeLongScreenshotMaxCapturedPages(dexKitBridge))
            }
            //启用PNG保存格式
            if (preferences(ModulePrefs).getBoolean("enable_png_save_format", false)) {
                loadHooker(EnablePNGSaveFormat(dexKitBridge))
            }
            //禁用截图包名MD5加密
            val disableMd5 =
                preferences(ModulePrefs).getBoolean("disable_screenshot_packagename_md5_encrypt", false)
            if (disableMd5) {
                loadHooker(DisableScreenshotPackageNameMd5Encrypt(dexKitBridge))
            }
        }
    }
}