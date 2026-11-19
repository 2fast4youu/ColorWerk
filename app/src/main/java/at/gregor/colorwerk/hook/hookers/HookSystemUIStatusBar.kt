package at.gregor.colorwerk.hook.hookers

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.hook.scopes.systemui.CustomMusicFluidCloudWhitelist
import at.gregor.colorwerk.hook.scopes.systemui.DisableMediaMusicFluidCloudBlacklist
import at.gregor.colorwerk.hook.scopes.systemui.StatusBarIconOrder
import at.gregor.colorwerk.hook.statusbar.StatusBarBattery
import at.gregor.colorwerk.hook.statusbar.StatusBarClock
import at.gregor.colorwerk.hook.statusbar.StatusBarControlCenter
import at.gregor.colorwerk.hook.statusbar.StatusBarIcon
import at.gregor.colorwerk.hook.statusbar.StatusBarLayout
import at.gregor.colorwerk.hook.statusbar.StatusBarNetWorkSpeed
import at.gregor.colorwerk.hook.statusbar.StatusBarNotifiyLimit
import at.gregor.colorwerk.hook.statusbar.StatusBarNotify
import at.gregor.colorwerk.hook.statusbar.StatusBarSilder
import at.gregor.colorwerk.hook.statusbar.StatusBarTile
import at.gregor.colorwerk.hook.statusbar.StatusBarUI
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.getOSVersionCode
import org.lsposed.lsparanoid.Obfuscate
import org.luckypray.dexkit.DexKitBridge

@Obfuscate
class HookSystemUIStatusBar(val dexKitBridge: DexKitBridge) : YukiBaseHooker() {
    override fun onHook() {
        val osCode = getOSVersionCode

        //状态栏
        loadHooker(StatusBarUI)

        //状态栏时钟
        loadHooker(StatusBarClock())

        //状态栏网速
        loadHooker(StatusBarNetWorkSpeed)

        //状态栏通知
        loadHooker(StatusBarNotify)

        //状态栏通知限制
        loadHooker(StatusBarNotifiyLimit)

        //状态栏图标
        loadHooker(StatusBarIcon)

        //状态栏图标位置排序（始终加载：运行期由 dataChannel 推送新顺序）
        loadHooker(StatusBarIconOrder)

        //状态栏控制中心
        loadHooker(StatusBarControlCenter)

        //状态栏磁贴
        loadHooker(StatusBarTile(dexKitBridge))

        //状态栏滑动条
        loadHooker(StatusBarSilder)

        //状态栏布局
        loadHooker(StatusBarLayout)

        //状态栏电池
        loadHooker(StatusBarBattery)

        //自定义音乐流体云白名单
        if (osCode >= 33) {
            if (preferences(ModulePrefs).getBoolean("custom_music_fluid_cloud_whitelist", false)) {
                loadHooker(CustomMusicFluidCloudWhitelist)
            }
            if (preferences(ModulePrefs).getBoolean("disable_media_music_fluid_cloud_blacklist", false)) {
                if (osCode >= 35) loadHooker(DisableMediaMusicFluidCloudBlacklist)
            }
        }


        //Source OplusToggleSliderView
//        "com.oplus.systemui.qs.widget.OplusToggleSliderView".toClass().apply {
//            method { name = "onShapeChanged" }.hook {
//                after {
//                    val type = firstArg().get<Int>() ?: 0
//                    val mSlider = field { name = "mSlider" }.get(instance).any() ?: return@after
//                    val mThumbColorStateList = mSlider.current().field {
//                        name = "mThumbColorStateList"
//                    }.cast<ColorStateList>() ?: return@after
//                    val newColorStateList = mThumbColorStateList.withAlpha(50)
//                    mSlider.current().method { name = "setThumbColor" }.call(newColorStateList)
//                }
//            }
//        }

        //res/layout/bubble_expanded_view.xml
        //<string name="bubble_close">关闭对话</string>
        //<string name="open_app">进入应用</string>
        //flag_conversations
        //bubble_ic_create_bubble 箭头向右下
        //bubble_ic_stop_bubble 箭头向左上

//        "com.android.wm.shell.bubbles.BubbleController".toClass().apply {
//            method { name = "isResizableActivity" }.hook {
//                replaceToTrue()
//            }
//        }
//
//        "com.android.systemui.statusbar.notification.row.NotificationContentView".toClass().apply {
//            method { name = "shouldShowBubbleButton" }.hook {
//                before {
//                    val entry = firstArg().get() ?: return@before
//                    result = entry.current().method {
//                        name = "getBubbleMetadata";emptyParam()
//                    }.call() != null
//                    YLog.debug("${method.name} -> $result")
//                }
//            }
//        }
//
//        "com.android.systemui.wmshell.BubblesManager".toClass().apply {
//            method { name = "areBubblesEnabled" }.hook {
//                replaceToTrue()
//            }
//        }

    }
}
