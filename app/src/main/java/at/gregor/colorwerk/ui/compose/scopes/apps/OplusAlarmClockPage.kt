package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * 闹钟页：时钟样式模式的取值（三档 0/1/2，默认 0）。
 */
object OplusAlarmClockPage {

    val spec = ScopePageSpec(
        pageKey = "oplus_alarm_clock",
        prefsName = ModulePrefs,
        packName = "com.coloros.alarmclock",
        scopes = arrayOf("com.coloros.alarmclock"),
        restartEnabled = true,
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        list(
            key = "alarmclock_widget_redone_mode",
            title = c.getString(R.string.alarmclock_widget_redone_mode),
            entries = c.resources.getStringArray(
                R.array.statusbar_control_center_clock_red_one_mode_entries
            ),
            entryValues = arrayOf("0", "1", "2"),
            default = "0",
            summary = c.getString(R.string.current_mode) + ": %s",
            notify = true,
        )
    }
}
