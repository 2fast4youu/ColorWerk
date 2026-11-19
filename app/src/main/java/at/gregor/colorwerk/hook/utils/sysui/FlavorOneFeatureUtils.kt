package at.gregor.colorwerk.hook.utils.sysui

import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.kavaref.extension.toClass
import at.gregor.colorwerk.utils.A14
import at.gregor.colorwerk.utils.SDK
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
class FlavorOneFeatureUtils(val classLoader: ClassLoader?) {

    val clazz = "com.oplusos.systemui.common.feature.FlavorOneFeatureOption".toClass(classLoader)

    fun getInstance(): Any? {
        return if (SDK >= A14) clazz.resolve().firstField { name = "INSTANCE" }.get() else null
    }

    fun isFlavorOneDevice(): Boolean? {
        return clazz.resolve().firstMethod { name = "isFlavorOneDevice" }.of(getInstance())
            .invoke<Boolean>()
    }
}