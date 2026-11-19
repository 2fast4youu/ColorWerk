package at.gregor.colorwerk.hook.scopes.settings

import android.content.pm.ApplicationInfo
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.kavaref.extension.classOf
import com.highcapable.yukihookapi.extension.kavaref.hookAll
import com.highcapable.yukihookapi.extension.kavaref.toClass
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.utils.A13
import at.gregor.colorwerk.utils.DexkitUtils.checkDataList
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.SDK
import org.lsposed.lsparanoid.Obfuscate
import org.luckypray.dexkit.DexKitBridge

class HookSettingsFeature(val dexKitBridge: DexKitBridge) : YukiBaseHooker() {
    override fun onHook() {
        if (SDK < A13) loadHooker(HookExpUst(dexKitBridge))
    }

    @Obfuscate
    class HookExpUst(val dexKitBridge: DexKitBridge) : YukiBaseHooker() {
        override fun onHook() {
            val neverTimeout = preferences(ModulePrefs).getBoolean("enable_show_never_timeout", false)

            //Source ExpUstUtils
            dexKitBridge.findClass {
                matcher {
                    methods {
                    add { returnType(classOf<String>()) }
                        add { returnType(classOf<Boolean>()) }
                        add { returnType(classOf<ApplicationInfo>()) }
                        add { paramTypes(classOf<String>()) }
                        add { paramTypes(classOf<Int>()) }
                        add { paramTypes(classOf<Int>(), classOf<String>()) }
                        add { paramTypes(classOf<String>()) }
                        add { paramTypes(classOf<String>(), classOf<String>()) }
                    }
                    usingStrings("screen_off_timeout")
                }
            }.apply {
                checkDataList("HookExpUst")
                single().name.toClass().resolve().apply {
                    method {
                        parameters(Int::class)
                        returnType = Boolean::class
                    }.hookAll {
                        before {
                            when (firstArg().get<Int>() ?: 0) {
                                //Source DisplayTimeOutController -> 永不息屏(24H)
                                11 -> if (SDK < A13 && neverTimeout) result = true
                            }
                        }
                    }
                }
            }
        }
    }
}