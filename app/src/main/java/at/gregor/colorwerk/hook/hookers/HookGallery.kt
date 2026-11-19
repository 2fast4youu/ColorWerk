package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.camera.HookCameraConfig
import at.gregor.colorwerk.hook.scopes.gallery.GalleryWaterMarkWordDialog
import at.gregor.colorwerk.hook.scopes.gallery.HookFunctionManager
import at.gregor.colorwerk.hook.scopes.gallery.HookSystemStorage
import at.gregor.colorwerk.hook.scopes.gallery.RemoveAIGCEliminationLimit
import at.gregor.colorwerk.hook.scopes.gallery.ReplaceOnePlusModelWatermark
import at.gregor.colorwerk.utils.A13
import at.gregor.colorwerk.utils.A15
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.SDK
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookGallery : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        if (osCode < 27) return

        //HookCameraConfig
        if (SDK >= A13) loadHooker(HookCameraConfig)

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //HookOtherSystemStorage
            loadHooker(HookSystemStorage(dexKitBridge))
            //HookFunctionManager
            if (osCode < 34) loadHooker(HookFunctionManager(dexKitBridge))

            //替换OnePlus机型水印
            if (preferences(ModulePrefs).getBoolean("replace_oneplus_model_watermark", false)) {
                if (osCode >= 34) loadHooker(ReplaceOnePlusModelWatermark(dexKitBridge))
            }
            //移除自定义水印字数限制
            if (preferences(ModulePrefs).getBoolean("remove_gallery_watermark_word_limit", false)) {
                if (osCode < 30) loadHooker(GalleryWaterMarkWordDialog(dexKitBridge))
            }
            //移除AIGC消除限制
            if (preferences(ModulePrefs).getBoolean("remove_aigc_elimination_limit", false)) {
                if (SDK < A15) loadHooker(RemoveAIGCEliminationLimit(dexKitBridge))
            }
        }
    }
}