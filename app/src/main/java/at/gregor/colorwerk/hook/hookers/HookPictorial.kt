package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.pictorial.RemoveImageSaveWaterMark
import at.gregor.colorwerk.hook.scopes.pictorial.RemoveVideoSaveWaterMark
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookPictorial : YukiBaseHooker() {
    override fun onHook() {
        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //移除图片保存水印
            if (preferences(ModulePrefs).getBoolean("remove_image_save_watermark", false)) {
                loadHooker(RemoveImageSaveWaterMark(dexKitBridge))
            }
        }
        //移除视频保存水印
        if (preferences(ModulePrefs).getBoolean("remove_video_save_watermark", false)) {
            loadHooker(RemoveVideoSaveWaterMark)
        }
    }
}