package at.gregor.colorwerk.hook.hookers

import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.extension.kavaref.toClassOrNull
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.camera.CustomCameraOpenGalleryByDefault
import at.gregor.colorwerk.hook.scopes.camera.CustomModelWaterMark
import at.gregor.colorwerk.hook.scopes.camera.EnableCameraDebugUIOption
import at.gregor.colorwerk.hook.scopes.camera.HookCameraConfig
import at.gregor.colorwerk.hook.scopes.camera.RemoveCameraFlashLimit
import at.gregor.colorwerk.hook.scopes.camera.RemoveFilterModelLimit
import at.gregor.colorwerk.hook.scopes.camera.RemoveWatermarkWordLimit
import at.gregor.colorwerk.hook.utils.getAppVerInfo
import at.gregor.colorwerk.utils.A13
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.SDK
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookCamera : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        val appVer = preferences(ModulePrefs).getAppVerInfo(packageName)
        if (appVer?.versionCommit.isNullOrBlank()) return

        //Source BuildConfig
        val brand = "com.oplus.camera.filter.BuildConfig".toClassOrNull()?.resolve()
            ?.firstField { name = "FLAVOR_b" }?.get<String>() ?: ""

        //HookCameraConfig
        if (SDK >= A13) loadHooker(HookCameraConfig)

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //自定义机型水印
            val isRealme = brand.contains("realme", true)
            if (SDK >= A13 && isRealme.not()) loadHooker(CustomModelWaterMark(dexKitBridge))

            //移除自定义水印字数限制
            if (preferences(ModulePrefs).getBoolean("remove_watermark_word_limit", false)) {
                loadHooker(RemoveWatermarkWordLimit(dexKitBridge))
            }

            //自定义默认打开相册
            if (osCode >= 26) loadHooker(CustomCameraOpenGalleryByDefault(dexKitBridge))

            //启用DebugUI选项
            if (preferences(ModulePrefs).getBoolean("enable_camera_debug_ui_option", false)) {
                if (osCode >= 30) loadHooker(EnableCameraDebugUIOption(dexKitBridge))
            }

            //移除闪光灯使用限制
            if (preferences(ModulePrefs).getBoolean("remove_camera_flash_limit", false)) {
                if (osCode >= 26) loadHooker(RemoveCameraFlashLimit(dexKitBridge))
            }
        }

        //移除滤镜机型限制
        if (preferences(ModulePrefs).getBoolean("remove_filter_model_limit", false)) {
            if (osCode >= 34) loadHooker(RemoveFilterModelLimit)
        }

    }
}