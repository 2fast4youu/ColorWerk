package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog
import at.gregor.colorwerk.hook.scopes.opluskeyboard.ForceEnableOplusKeyboardBlur
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookOplusKeyboard : YukiBaseHooker() {
    override fun onHook() {
        //强制启用模糊效果
        val forceEnableBlur =
            preferences(ModulePrefs).getBoolean("force_enable_oplus_keyboard_blur", false)
        //诊断日志：确认 hooker 是否进到键盘进程、读到的开关值以及宿主 APK 路径
        YLog.info(
            "OplusKeyboard hook: forceEnableBlur = $forceEnableBlur, sourceDir = ${appInfo.sourceDir}"
        )
        if (forceEnableBlur) {
            DexkitUtils.create(appInfo.sourceDir) { loadHooker(ForceEnableOplusKeyboardBlur(it)) }
        }
    }
}
