package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.oshare.RemoveOShareCloseCountDown
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookOShare : YukiBaseHooker() {
    override fun onHook() {

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->

            //移除互传关闭倒计时
            if (preferences(ModulePrefs).getBoolean("remove_oshare_close_countdown", false)) {
                loadHooker(RemoveOShareCloseCountDown(dexKitBridge))
            }

        }

    }
}