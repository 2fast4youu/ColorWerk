package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.calendar.RemoveAlmanacPageInformationFlow
import at.gregor.colorwerk.hook.scopes.calendar.RemoveHolidayPageInformationFlow
import at.gregor.colorwerk.hook.scopes.calendar.RemoveHoroscopePageInformationFlow
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookCalendar : YukiBaseHooker() {
    override fun onHook() {
        //移除节假日页面信息流
        if (preferences(ModulePrefs).getBoolean("remove_holiday_page_information_flow", false)) {
            loadHooker(RemoveHolidayPageInformationFlow)
        }
        //移除黄历页面信息流
        if (preferences(ModulePrefs).getBoolean("remove_almanac_page_information_flow", false)) {
            loadHooker(RemoveAlmanacPageInformationFlow)
        }
        //移除星座页面信息流
        if (preferences(ModulePrefs).getBoolean("remove_horoscope_page_information_flow", false)) {
            loadHooker(RemoveHoroscopePageInformationFlow)
        }
    }
}