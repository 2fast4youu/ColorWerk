package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.checkPackName
import at.gregor.colorwerk.utils.getOSVersionCode

/**
 * 一碰互联页：解除一碰互联时长限制的开关。
 * 仅在 osCode >= 33 且装有 com.oplus.beaconlink 时出现（见 spec.isVisible），开关本身也在同一条件下渲染。
 */
object OplusBeaconLinkPage {

    val spec = ScopePageSpec(
        pageKey = "oplus_beacon_link",
        prefsName = ModulePrefs,
        packName = "com.oplus.beaconlink",
        scopes = arrayOf("com.oplus.beaconlink"),
        restartEnabled = true,
        isVisible = { getOSVersionCode >= 33 && checkPackName("com.oplus.beaconlink") },
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        if (getOSVersionCode >= 33) {
            switch(
                key = "remove_beacon_link_time_limit",
                title = c.getString(R.string.remove_beacon_link_time_limit),
            )
        }
    }
}
