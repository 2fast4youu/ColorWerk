package at.gregor.colorwerk.listener

import at.gregor.colorwerk.data.AppIntentInfo
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
interface OnSelectIntentInfoListener {
    fun resultSelectIntentInfos(list: ArrayList<AppIntentInfo>)
}