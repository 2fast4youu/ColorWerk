package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.checkPackName
import at.gregor.colorwerk.utils.getOSVersionCode

/**
 * 护眼页：开启纸张纹理效果的开关。
 * 仅在 osCode >= 33 且装有 com.oplus.eyeprotect 时出现（见 spec.isVisible），开关本身也在同一条件下渲染；
 * 改动需要重启系统。
 */
object OplusEyeProtectPage {

    val spec = ScopePageSpec(
        pageKey = "oplus_eye_protect",
        prefsName = ModulePrefs,
        packName = "com.oplus.eyeprotect",
        scopes = arrayOf("com.oplus.eyeprotect"),
        restartEnabled = true,
        isVisible = { getOSVersionCode >= 33 && checkPackName("com.oplus.eyeprotect") },
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        if (getOSVersionCode >= 33) {
            switch(
                key = "enable_eyeprotect_paper_texture_support",
                title = c.getString(R.string.enable_eyeprotect_paper_texture_support),
                summary = c.getString(R.string.need_restart_system),
            )
        }
    }
}
