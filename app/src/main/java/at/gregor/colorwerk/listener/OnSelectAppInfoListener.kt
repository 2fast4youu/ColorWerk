package at.gregor.colorwerk.listener

import org.lsposed.lsparanoid.Obfuscate
import at.gregor.colorwerk.data.AppInfo

@Obfuscate
interface OnSelectAppInfoListener {
    fun resultSelectAppInfos(list: ArrayList<AppInfo>)
}