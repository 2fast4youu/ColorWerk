package at.gregor.colorwerk.hook.scopes.otherapp

import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.kavaref.extension.classOf
import com.highcapable.yukihookapi.extension.kavaref.hook
import com.highcapable.yukihookapi.extension.kavaref.toClass
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import at.gregor.colorwerk.utils.DexkitUtils
import at.gregor.colorwerk.utils.DexkitUtils.checkDataList
import at.gregor.colorwerk.utils.ModulePrefs
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
object HookFakeGpsJoyStick : YukiBaseHooker() {
    override fun onHook() {
        if (!preferences(ModulePrefs).getBoolean("gps_joystick_unlock_pro", false)) return

        //Source
        DexkitUtils.create(appInfo.sourceDir) { dexKitBridge ->
            dexKitBridge.findClass {
                matcher {
                    addMethod { paramCount(0); returnType(classOf<Int>()) }
                    usingStrings("AD_FREE", "PRO")
                }
            }.apply {
                checkDataList("Clazz")

                findMethod {
                    matcher {
                        paramCount(0)
                        returnType(classOf<Int>())
                        usingStrings("AD_FREE", "PRO")
                        usingNumbers(3600000, 25200000)
                    }
                }.apply {
                    checkDataList("Method Pro")

                    single().className.toClass().resolve().apply {
                        firstMethod {
                            name = single().methodName
                            emptyParameters()
                            returnType = Int::class
                        }.hook {
                            intercept(3)
                        }
                    }
                }
            }
        }
    }
}