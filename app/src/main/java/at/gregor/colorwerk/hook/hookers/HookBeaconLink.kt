package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.beaconlink.RemoveBeaconLinkTimeLimit
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookBeaconLink : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->

            //移除无网畅聊时间限制
            if (preferences(ModulePrefs).getBoolean("remove_beacon_link_time_limit", false)) {
             if (osCode >= 33)   loadHooker(RemoveBeaconLinkTimeLimit(dexKitBridge))
            }

        }

    }
}