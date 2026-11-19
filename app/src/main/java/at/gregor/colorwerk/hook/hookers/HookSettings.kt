package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.globals.HookGlobalFeatureConfig
import at.gregor.colorwerk.hook.globals.HookGlobalFeatureProvider
import at.gregor.colorwerk.hook.globals.HookGlobalSystemProperties
import at.gregor.colorwerk.hook.scopes.settings.AllowDisablingSystemApps
import at.gregor.colorwerk.hook.scopes.settings.AutoJumpAccessibilitySettings
import at.gregor.colorwerk.hook.scopes.settings.AutoUnlockRestrictedSettings
import at.gregor.colorwerk.hook.scopes.settings.CustomProcessorPageIntroductionParameters
import at.gregor.colorwerk.hook.scopes.settings.CustomizeDeviceOTACardBackground
import at.gregor.colorwerk.hook.scopes.settings.CustomizeDeviceSharingPageParameters
import at.gregor.colorwerk.hook.scopes.settings.DarkModeList
import at.gregor.colorwerk.hook.scopes.settings.DisableAccessibilityDialog
import at.gregor.colorwerk.hook.scopes.settings.DisableAppArchiveButton
import at.gregor.colorwerk.hook.scopes.settings.DisableAppArchivingUI
import at.gregor.colorwerk.hook.scopes.settings.DisableSettingOtgAutoOff
import at.gregor.colorwerk.hook.scopes.settings.EnableCustomAppLanguage
import at.gregor.colorwerk.hook.scopes.settings.EnableGoogleAutoFill
import at.gregor.colorwerk.hook.scopes.settings.EnableStatusBarClockFormat
import at.gregor.colorwerk.hook.scopes.settings.EnableSwipeUpNavigationGesture
import at.gregor.colorwerk.hook.scopes.settings.FixAppSpecificMediaVolumePage
import at.gregor.colorwerk.hook.scopes.settings.FixDefaultAppJumpProblem
import at.gregor.colorwerk.hook.scopes.settings.ForceDisplayAutoLaunchJumpOption
import at.gregor.colorwerk.hook.scopes.settings.ForceDisplayBottomGoogleSettings
import at.gregor.colorwerk.hook.scopes.settings.ForceDisplayContentRecommend
import at.gregor.colorwerk.hook.scopes.settings.ForceDisplayDisabledAppsManager
import at.gregor.colorwerk.hook.scopes.settings.ForceDisplayPasswordManagementSettings
import at.gregor.colorwerk.hook.scopes.settings.ForceDisplayProcessManagement
import at.gregor.colorwerk.hook.scopes.settings.HookAppDetails
import at.gregor.colorwerk.hook.scopes.settings.HookIris5Controller
import at.gregor.colorwerk.hook.scopes.settings.HookSettingsFeature
import at.gregor.colorwerk.hook.scopes.settings.HookSettingsPreferenceFragment
import at.gregor.colorwerk.hook.scopes.settings.RemoveDeviceNameChangeLimit
import at.gregor.colorwerk.hook.scopes.settings.RemoveDpiRestartRecovery
import at.gregor.colorwerk.hook.scopes.settings.RemoveSettingsBottomLaboratory
import at.gregor.colorwerk.hook.scopes.settings.RemoveTopAccountDisplay
import at.gregor.colorwerk.utils.A13
import at.gregor.colorwerk.utils.A14
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.SDK
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookSettings : YukiBaseHooker() {
    override fun onHook() {
        loadHooker(HookGlobalFeatureConfig)
        loadHooker(HookGlobalSystemProperties)

        val osCode = getOSVersionCode

        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            //HookAppFeatureProvider
            loadHooker(HookGlobalFeatureProvider(dexKitBridge))
            //HookSettingsFeature
            loadHooker(HookSettingsFeature(dexKitBridge))
            //移除DPI重启恢复
            if (preferences(ModulePrefs).getBoolean("remove_dpi_restart_recovery", false)) {
                if (osCode >= 24) loadHooker(RemoveDpiRestartRecovery(dexKitBridge))
            }
            //暗色模式列表
            if (preferences(ModulePrefs).getBoolean("dark_mode_list_enable", false)) {
                loadHooker(DarkModeList(dexKitBridge))
            }
            //自动解锁受限制的设置
            if (preferences(ModulePrefs).getBoolean("auto_unlock_restricted_settings", false)) {
                if (SDK >= A13) loadHooker(AutoUnlockRestrictedSettings(dexKitBridge))
            }
            //启用应用专属媒体音量
            if (preferences(ModulePrefs).getBoolean("enable_app_specific_media_volume", false)) {
                if (osCode >= 27) loadHooker(FixAppSpecificMediaVolumePage(dexKitBridge))
            }
            //启用Google自动填充
            if (preferences(ModulePrefs).getBoolean("enable_google_auto_fill", false)) {
                loadHooker(EnableGoogleAutoFill(dexKitBridge))
            }
        }

        //HookSettingsPreferenceFragment removePreference
        loadHooker(HookSettingsPreferenceFragment)

        //应用详情页
        loadHooker(HookAppDetails)

        //移除顶部账号显示
        if (preferences(ModulePrefs).getBoolean("remove_top_account_display", false)) {
            loadHooker(RemoveTopAccountDisplay)
        }
        //视频动态插帧2K 120
        if (preferences(ModulePrefs).getBoolean("enable_video_memc_frame_insertion", false)) {
            if (preferences(ModulePrefs).getBoolean("video_frame_insertion_support_2K120", false)) {
                loadHooker(HookIris5Controller)
            }
        }
        //强制显示设置底部Google
        if (preferences(ModulePrefs).getBoolean("force_display_bottom_google_settings", false)) {
            loadHooker(ForceDisplayBottomGoogleSettings)
        }
        //移除设置底部实验室
        if (preferences(ModulePrefs).getBoolean("remove_settings_bottom_laboratory", false)) {
            loadHooker(RemoveSettingsBottomLaboratory)
        }
        //启用状态栏时钟格式
        if (preferences(ModulePrefs).getBoolean("enable_statusbar_clock_format", false)) {
            if (osCode < 40) loadHooker(EnableStatusBarClockFormat)
        }
        //自定义设备分享页面参数
        if (preferences(ModulePrefs).getBoolean(
                "customize_device_sharing_page_parameters",
                false
            )
        ) {
            if (SDK >= A13) loadHooker(CustomizeDeviceSharingPageParameters)
        }
        //强制开启进程管理
        if (preferences(ModulePrefs).getBoolean("force_display_process_management", false)) {
            loadHooker(ForceDisplayProcessManagement)
        }
        //允许停用系统应用
        if (preferences(ModulePrefs).getBoolean("allow_disabling_system_apps", false)) {
            loadHooker(AllowDisablingSystemApps)
        }
        //强制显示已停用应用管理器
        if (preferences(ModulePrefs).getBoolean("force_display_disabled_apps_manager", false)) {
            loadHooker(ForceDisplayDisabledAppsManager)
        }
        //强制显示内容推荐
        if (preferences(ModulePrefs).getBoolean("force_display_content_recommend", false)) {
            loadHooker(ForceDisplayContentRecommend)
        }
        //启用自定义应用语言
        if (preferences(ModulePrefs).getBoolean("enable_custom_app_language", false)) {
            if (SDK >= A14) loadHooker(EnableCustomAppLanguage)
        }
        //强制显示密码管理设置项
        if (preferences(ModulePrefs).getBoolean(
                "force_display_password_management_settings",
                false
            )
        ) {
            loadHooker(ForceDisplayPasswordManagementSettings)
        }
        //自定义设备OTA卡片背景/视频：两个开关任一开启都要加载，视频开关本身不能靠背景开关兜着
        if (preferences(ModulePrefs).getBoolean("customize_device_ota_card_background", false) ||
            preferences(ModulePrefs).getBoolean("customize_device_ota_card_video", false)
        ) {
            loadHooker(CustomizeDeviceOTACardBackground)
        }
        //禁用OTG自动关闭
        if (preferences(ModulePrefs).getBoolean("disable_otg_auto_off", false)) {
            if (osCode >= 30) loadHooker(DisableSettingOtgAutoOff)
        }
        //修复设置CN特供版默认应用跳转
        if (preferences(ModulePrefs).getBoolean("disable_cn_special_edition_setting", false)) {
            if (preferences(ModulePrefs).getBoolean("fix_default_app_jump_problem", false)) {
                loadHooker(FixDefaultAppJumpProblem)
            }
            if (preferences(ModulePrefs).getBoolean(
                    "force_display_auto_launch_jump_option", false
                )
            ) {
                loadHooker(ForceDisplayAutoLaunchJumpOption)
            }
        }
        //移除设备名称更改限制
        if (preferences(ModulePrefs).getBoolean("remove_device_name_change_limit", false)) {
            if (osCode >= 30) loadHooker(RemoveDeviceNameChangeLimit)
        }
        //自定义处理器页面介绍参数
        if (preferences(ModulePrefs).getString("set_processor_click_page", "0") == "3") {
            loadHooker(CustomProcessorPageIntroductionParameters)
        }
        //启用上滑导航手势
        if (preferences(ModulePrefs).getBoolean("enable_swipe_up_navigation_gesture", false)) {
            if (osCode >= 30) loadHooker(EnableSwipeUpNavigationGesture)
        }
        //自动跳转无障碍设置
        if (preferences(ModulePrefs).getBoolean("auto_jump_accessibility_settings", false)) {
            loadHooker(AutoJumpAccessibilitySettings)
        }

        //《自动释放应用空间》默认关闭 - 开关 UI 显示
        if (osCode >= 36) loadHooker(DisableAppArchivingUI)

        //隐藏应用详情页的归档按钮
        if (osCode >= 36) loadHooker(DisableAppArchiveButton)

        //禁用无障碍对话框 C17（与 framework 侧共用同一开关）
        if (preferences(ModulePrefs).getBoolean("disable_accessibility_warning_dialog", false)) {
            if (osCode >= 40) loadHooker(DisableAccessibilityDialog)
        }

        //电源键
//        //Source PowerButtonPreferenceController
//        "com.oplus.settings.feature.convenient.controller.PowerButtonPreferenceController\$Companion".toClass()
//            .apply {
//                method { name = "isPowerButtonSupport" }.hook {
//                    replaceToTrue()
//                }
//            }
//
//        "com.oplus.settings.feature.convenient.controller.DoubleTapPowerButtonPreferenceController".toClass()
//            .apply {
//                method { name = "getAvailabilityStatus" }.hook {
//                    replaceTo(0)
//                }
//            }

        //keep_screen_on -> 充电时屏幕不休眠
        //settings put global stay_on_while_plugged_in 7
        //com.android.settings.development.StayAwakePreferenceController

        //<string name="airplane_mode">飞行模式</string>
        //com.oplus.settings.feature.network.AirplaneController -> setAirplaneModeOn

        //safecenter_prohibit_monitor safecenter_prohibit_monitor_title -> 禁用权限监控
        //com.oplus.settings.feature.othersettings.development.ProhibitMonitorPreferenceController
    }
}