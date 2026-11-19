package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs

/**
 * 日历页：去掉节日、黄历、星座页信息流的开关。
 */
object OplusCalendarPage {

    val spec = ScopePageSpec(
        pageKey = "oplus_calendar",
        prefsName = ModulePrefs,
        packName = "com.coloros.calendar",
        scopes = arrayOf("com.coloros.calendar"),
        restartEnabled = true,
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        switch(
            key = "remove_holiday_page_information_flow",
            title = c.getString(R.string.remove_holiday_page_information_flow),
        )
        switch(
            key = "remove_almanac_page_information_flow",
            title = c.getString(R.string.remove_almanac_page_information_flow),
        )
        switch(
            key = "remove_horoscope_page_information_flow",
            title = c.getString(R.string.remove_horoscope_page_information_flow),
        )
    }
}
