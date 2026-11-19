package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.globals.HookGlobalFeatureConfig
import at.gregor.colorwerk.hook.scopes.permissioncontroller.RemoveStoragePermissionExceptionDialog
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookPermissionController : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        loadHooker(HookGlobalFeatureConfig)

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->

            val storageDialog =
                preferences(ModulePrefs).getBoolean("remove_storage_permission_exception_dialog", false)
            if (osCode < 37 && storageDialog) {
                loadHooker(RemoveStoragePermissionExceptionDialog(dexKitBridge))
            }

        }
    }
}