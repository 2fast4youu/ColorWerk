package at.gregor.colorwerk.hook

import android.annotation.SuppressLint
import com.highcapable.yukihookapi.annotation.xposed.YukiHookLibXposedEntry
import com.highcapable.yukihookapi.hook.factory.configure
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.YukiHookXposedModule
import at.gregor.colorwerk.hook.hookers.HookAlarmClock
import at.gregor.colorwerk.hook.hookers.HookAndroid
import at.gregor.colorwerk.hook.hookers.HookAudioEffectCenter
import at.gregor.colorwerk.hook.hookers.HookAudioMonitor
import at.gregor.colorwerk.hook.hookers.HookBattery
import at.gregor.colorwerk.hook.hookers.HookBeaconLink
import at.gregor.colorwerk.hook.hookers.HookBrowser
import at.gregor.colorwerk.hook.hookers.HookCalendar
import at.gregor.colorwerk.hook.hookers.HookCamera
import at.gregor.colorwerk.hook.hookers.HookClaw
import at.gregor.colorwerk.hook.hookers.HookCloudService
import at.gregor.colorwerk.hook.hookers.HookCorePatch
import at.gregor.colorwerk.hook.hookers.HookDirectUI
import at.gregor.colorwerk.hook.hookers.HookEngineerMode
import at.gregor.colorwerk.hook.hookers.HookExternalStorage
import at.gregor.colorwerk.hook.hookers.HookFileManager
import at.gregor.colorwerk.hook.hookers.HookGallery
import at.gregor.colorwerk.hook.hookers.HookGesture
import at.gregor.colorwerk.hook.hookers.HookHealth
import at.gregor.colorwerk.hook.hookers.HookKeyguardClock
import at.gregor.colorwerk.hook.hookers.HookKeyguardPersonality
import at.gregor.colorwerk.hook.hookers.HookLauncher
import at.gregor.colorwerk.hook.hookers.HookMarket
import at.gregor.colorwerk.hook.hookers.HookMediaController
import at.gregor.colorwerk.hook.hookers.HookMetis
import at.gregor.colorwerk.hook.hookers.HookMultiApp
import at.gregor.colorwerk.hook.hookers.HookNfc
import at.gregor.colorwerk.hook.hookers.HookNotificationManager
import at.gregor.colorwerk.hook.hookers.HookOShare
import at.gregor.colorwerk.hook.hookers.HookOplusCosa
import at.gregor.colorwerk.hook.hookers.HookOplusGames
import at.gregor.colorwerk.hook.hookers.HookOplusKeyboard
import at.gregor.colorwerk.hook.hookers.HookOplusMMS
import at.gregor.colorwerk.hook.hookers.HookOplusOta
import at.gregor.colorwerk.hook.hookers.HookPackageInstaller
import at.gregor.colorwerk.hook.hookers.HookPermissionController
import at.gregor.colorwerk.hook.hookers.HookPhone
import at.gregor.colorwerk.hook.hookers.HookPhoneManager
import at.gregor.colorwerk.hook.hookers.HookPictorial
import at.gregor.colorwerk.hook.hookers.HookPscanvas
import at.gregor.colorwerk.hook.hookers.HookQuickSearchBox
import at.gregor.colorwerk.hook.hookers.HookSafeCenter
import at.gregor.colorwerk.hook.hookers.HookScreenshot
import at.gregor.colorwerk.hook.hookers.HookSecuritypPermission
import at.gregor.colorwerk.hook.hookers.HookSettings
import at.gregor.colorwerk.hook.hookers.HookSmartSidebar
import at.gregor.colorwerk.hook.hookers.HookSoundRecorder
import at.gregor.colorwerk.hook.hookers.HookSpeechAssist
import at.gregor.colorwerk.hook.hookers.HookSystemUI
import at.gregor.colorwerk.hook.hookers.HookThemeStore
import at.gregor.colorwerk.hook.hookers.HookUIEngine
import at.gregor.colorwerk.hook.hookers.HookWeather
import at.gregor.colorwerk.hook.hookers.HookWirelessSettings
import at.gregor.colorwerk.hook.scopes.otherapp.HookADM
import at.gregor.colorwerk.hook.scopes.otherapp.HookAlphaBackupPro
import at.gregor.colorwerk.hook.scopes.otherapp.HookFakeGpsJoyStick
import at.gregor.colorwerk.hook.scopes.otherapp.HookKsWeb
import org.lsposed.lsparanoid.Obfuscate
import java.io.File

@YukiHookLibXposedEntry(
    entryClassName = "Entry",
    minApiVersion = 102,
    targetApiVersion = 102,
    hotReload = YukiHookLibXposedEntry.HotReload.AUTO,
    scope = [
        "system",
        "com.android.systemui",
        "com.oplus.battery",
        "com.android.settings",
        "com.android.packageinstaller",
        "com.android.launcher",
        "com.oppo.launcher",
        "com.oneplus.camera",
        "com.oplus.camera",
        "com.coloros.gallery3d",
        "com.oplus.safecenter",
        "com.coloros.safecenter",
        "com.coloros.alarmclock",
        "com.oplus.notificationmanager",
        "com.coloros.phonemanager",
        "com.oplus.aod",
        "com.oplus.uiengine",
        "com.heytap.themestore",
        "com.oplus.games",
        "com.oplus.cosa",
        "com.heytap.cloud",
        "com.oplus.screenshot",
        "com.oplus.ota",
        "com.oplus.sau",
        "com.heytap.pictorial",
        "com.android.mms",
        "com.android.incallui",
        "com.android.phone",
        "com.android.externalstorage",
        "com.heytap.browser",
        "com.oplus.gesture",
        "com.android.permissioncontroller",
        "com.coloros.directui",
        "com.heytap.quicksearchbox",
        "com.heytap.market",
        "com.coloros.weather2",
        "com.coloros.calendar",
        "com.coloros.smartsidebar",
        "com.oplus.multiapp",
        "com.coloros.soundrecorder",
        "com.oplus.audiomonitor",
        "com.oplus.atlas",
        "com.oplus.audio.effectcenter",
        "com.oplus.appplatform",
        "com.oplus.eyeprotect",
        "com.oplus.mediacontroller",
        "com.oplus.exsystemservice",
        "com.oplus.keyguard.clock.base",
        "com.oplus.keyguard.personality.clocks",
        "com.oplus.beaconlink",
        "com.heytap.speechassist",
        "com.oplus.keyboard",
        "com.oplus.wirelesssettings",
        "com.heytap.health",
        "com.android.nfc",
        "com.coloros.oshare",
        "com.android.bluetooth",
        "com.android.contacts",
        "com.oplus.securitypermission",
        "com.coloros.filemanager",
        "com.oplus.engineermode",
        "com.heytap.mydevices",
        "com.heytap.accessory",
        "com.heytap.mcs",
        "com.oplus.claw",
        "com.oplus.metis",
        "com.oplus.pscanvas",
        "ru.kslabs.ksweb",
        "com.dv.adm",
        "com.ruet_cse_1503050.ragib.appbackup.pro",
        "com.theappninjas.fakegpsjoystick",
    ],
)
@Obfuscate
class MainHook : YukiHookXposedModule {

    override fun onInit() = configure {
        logging {
            tag = MainHook.TAG
        }
    }

    companion object {
        const val TAG = "LuckyTool"

        @SuppressLint("SdCardPath")
        fun isMasterEnabled(): Boolean {
            return try {
                File("/sdcard/disable_lt").exists()
            } catch (_: Throwable) {
                false
            }
        }
    }

    override fun onHook() = encase {
        if (isMasterEnabled()) return@encase

        //系统框架
        loadSystem(HookAndroid)
        //核心破解
        loadSystem(HookCorePatch)

        //系统界面
        loadApp("com.android.systemui", HookSystemUI)

        //经典主题 Clock
        loadApp("com.oplus.keyguard.clock.base", HookKeyguardClock)
        loadApp("com.oplus.keyguard.personality.clocks", HookKeyguardPersonality)

        //通知管理
        loadApp("com.oplus.notificationmanager", HookNotificationManager)

        //时钟
        loadApp("com.coloros.alarmclock", HookAlarmClock)

        //桌面
        loadApp("com.oppo.launcher", HookLauncher)
        loadApp("com.android.launcher", HookLauncher)

        //百变引擎
        loadApp("com.oplus.uiengine", HookUIEngine)

        //截屏
        loadApp("com.oplus.screenshot", HookScreenshot)

        //安全中心
        loadApp("com.oplus.safecenter", HookSafeCenter)
        loadApp("com.coloros.safecenter", HookSafeCenter)

        //应用安装器
        loadApp("com.android.packageinstaller", HookPackageInstaller)

        //外部存储设备
        loadApp("com.android.externalstorage", HookExternalStorage)

        //电池
        loadApp("com.oplus.battery", HookBattery)

        //设置
        loadApp("com.android.settings", HookSettings)

        //相机
        loadApp("com.oplus.camera", HookCamera)
        loadApp("com.oneplus.camera", HookCamera)

        //相册
        loadApp("com.coloros.gallery3d", HookGallery)

        //主题商店
        loadApp("com.heytap.themestore", HookThemeStore)
        loadApp("com.oplus.themestore", HookThemeStore)

        //云服务
        loadApp("com.heytap.cloud", HookCloudService)

        //游戏助手
        loadApp("com.oplus.games", HookOplusGames)

        //应用增强服务
        loadApp("com.oplus.cosa", HookOplusCosa)

        //软件更新
        loadApp("com.oplus.ota", HookOplusOta)

        //乐划锁屏
        loadApp("com.heytap.pictorial", HookPictorial)

        //信息
        loadApp("com.android.mms", HookOplusMMS)

        //电话服务
        loadApp("com.android.phone", HookPhone)

        //浏览器
        loadApp("com.heytap.browser", HookBrowser)

        //手势体感
        loadApp("com.oplus.gesture", HookGesture)

        //权限控制器
        loadApp("com.android.permissioncontroller", HookPermissionController)

        //小布助手
        loadApp("com.heytap.speechassist", HookSpeechAssist)

        //小布输入法
        loadApp("com.oplus.keyboard", HookOplusKeyboard)

        //小布识屏
        loadApp("com.coloros.directui", HookDirectUI)

        //全局搜索
        loadApp("com.heytap.quicksearchbox", HookQuickSearchBox)

        //软件商店
        loadApp("com.heytap.market", HookMarket)

        //天气
        loadApp("com.coloros.weather2", HookWeather)

        //日历
        loadApp("com.coloros.calendar", HookCalendar)

        //智能侧边栏
        loadApp("com.coloros.smartsidebar", HookSmartSidebar)

        //手机管家
        loadApp("com.coloros.phonemanager", HookPhoneManager)

        //应用分身
        loadApp("com.oplus.multiapp", HookMultiApp)

        //录音
        loadApp("com.coloros.soundrecorder", HookSoundRecorder)

        //三方应用通话录音 / 智慧语音
        loadApp("com.oplus.audiomonitor", HookAudioMonitor)

        //audioEffectCenter
        loadApp("com.oplus.audio.effectcenter", HookAudioEffectCenter)

        //MediaController
        loadApp("com.oplus.mediacontroller", HookMediaController)

        //无网畅聊
        loadApp("com.oplus.beaconlink", HookBeaconLink)

        //无线设置
        loadApp("com.oplus.wirelesssettings", HookWirelessSettings)

        //健康
        loadApp("com.heytap.health", HookHealth)

        //NFC服务
        loadApp("com.android.nfc", HookNfc)

        //互传
        loadApp("com.coloros.oshare", HookOShare)

        //权限管理
        loadApp("com.oplus.securitypermission", HookSecuritypPermission)

        //文件管理
        loadApp("com.coloros.filemanager", HookFileManager)

        //工程模式
        loadApp("com.oplus.engineermode", HookEngineerMode)

        //小布Next
        loadApp("com.oplus.claw", HookClaw)

        //流体云
        loadApp("com.oplus.metis", HookMetis)

        //全景工作台
        loadApp("com.oplus.pscanvas", HookPscanvas)

        //其他APP
        loadApp("com.theappninjas.fakegpsjoystick", HookFakeGpsJoyStick)
        loadApp("com.ruet_cse_1503050.ragib.appbackup.pro", HookAlphaBackupPro)
        loadApp("ru.kslabs.ksweb", HookKsWeb)
        loadApp("com.dv.adm", HookADM)
    }

}
