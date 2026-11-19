package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.globals.HookGlobalFeatureConfig
import at.gregor.colorwerk.hook.scopes.mms.RemoveMmsBottomInputBoxMenu
import at.gregor.colorwerk.hook.scopes.mms.RemoveMmsCardMarketingButton
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookOplusMMS : YukiBaseHooker() {
    override fun onHook() {
        loadHooker(HookGlobalFeatureConfig)

        if (preferences(ModulePrefs).getBoolean("remove_mms_bottom_input_box_menu", false)) {
            loadHooker(RemoveMmsBottomInputBoxMenu)
        }

        if (preferences(ModulePrefs).getBoolean("remove_mms_card_marketing_button", false)) {
            loadHooker(RemoveMmsCardMarketingButton)
        }

    }
}