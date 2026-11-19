package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.externalstorage.RemoveStorageLimit
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookExternalStorage : YukiBaseHooker() {
    override fun onHook() {
        //移除存储限制
        if (preferences(ModulePrefs).getBoolean("remove_storage_limit", false)) {
            loadHooker(RemoveStorageLimit)
        }
    }
}