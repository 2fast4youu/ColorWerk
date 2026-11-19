package at.gregor.colorwerk.ui.compose.scopes.others

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * GPS 摇杆页：Pro 解锁开关（写入 com.theappninjas.fakegpsjoystick）。
 */
object GpsJoyStickPage {

    val spec = ScopePageSpec(
        pageKey = "gps_joy_stick",
        prefsName = ModulePrefs,
        packName = "com.theappninjas.fakegpsjoystick",
        scopes = arrayOf("com.theappninjas.fakegpsjoystick"),
        restartEnabled = true,
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        switch(
            key = "gps_joystick_unlock_pro",
            title = c.getString(R.string.adm_unlock_pro),
        )
    }
}
