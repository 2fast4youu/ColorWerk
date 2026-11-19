package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.alarmclock.AlarmClockWidget
import at.gregor.colorwerk.utils.DexkitUtils
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookAlarmClock : YukiBaseHooker() {
    override fun onHook() {

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //桌面时钟组件红一
            loadHooker(AlarmClockWidget(dexKitBridge))
        }

    }
}