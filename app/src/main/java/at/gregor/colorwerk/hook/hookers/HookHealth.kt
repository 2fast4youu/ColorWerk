package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.health.RemoveHealthRootCheck
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookHealth : YukiBaseHooker() {

    override fun onHook() {

        //移除Root检测对话框
        if (!preferences(ModulePrefs).getBoolean("remove_health_root_check_dialog", false)) return

        //弹窗只在主进程带着 MainActivity 触发，子进程（:transport、:once…）装上去只会白付一次 DexKit 初始化
        val process = runCatching { processName }.getOrNull().orEmpty()
        if (process.isNotEmpty() && process != "com.heytap.health") return

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            loadHooker(RemoveHealthRootCheck(dexKitBridge))
        }
    }
}