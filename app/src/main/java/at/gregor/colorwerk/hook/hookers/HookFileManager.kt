package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.filemanager.RemoveWordLimitForCompressFiles
import at.gregor.colorwerk.hook.scopes.filemanager.RemoveWordLimitForLabelNameFiles
import at.gregor.colorwerk.hook.scopes.filemanager.RemoveWordLimitForSavingFiles
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookFileManager : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->

            //移除文件保存字数限制
            if (preferences(ModulePrefs).getBoolean("remove_word_limit_for_saving_files", false)) {
                if (osCode >= 37) loadHooker(RemoveWordLimitForSavingFiles(dexKitBridge))
            }
            //移除压缩文件字数限制
            if (preferences(ModulePrefs).getBoolean("remove_word_limit_for_compress_files", false)) {
                if (osCode >= 37) loadHooker(RemoveWordLimitForCompressFiles(dexKitBridge))
            }
            //移除重命名文件字数限制
            if (preferences(ModulePrefs).getBoolean("remove_word_limit_for_label_name_files", false)) {
                if (osCode >= 37) loadHooker(RemoveWordLimitForLabelNameFiles(dexKitBridge))
            }
        }
    }
}