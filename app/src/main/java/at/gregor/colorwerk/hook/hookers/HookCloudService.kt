package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.cloudservice.DisableForcedBackupAppList
import at.gregor.colorwerk.hook.scopes.cloudservice.RemoveNetworkRestriction
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookCloudService : YukiBaseHooker() {
    override fun onHook() {
        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //移除网络限制
            if (preferences(ModulePrefs).getBoolean("remove_network_limit", false)) {
                loadHooker(RemoveNetworkRestriction(dexKitBridge))
            }
        }

        //启用自定义备份选项
        if (preferences(ModulePrefs).getBoolean("disable_forced_backup_app_list", false)) {
            loadHooker(DisableForcedBackupAppList)
        }
    }
}