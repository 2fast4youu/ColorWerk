package at.gregor.colorwerk.service.tiles

import android.service.quicksettings.TileService
import org.lsposed.lsparanoid.Obfuscate
import at.gregor.colorwerk.utils.IntentUtils
import at.gregor.colorwerk.utils.closeCollapse

@Obfuscate
class ChargingTestTile : TileService() {
    override fun onClick() {
        closeCollapse()
        IntentUtils(this).jumpBatteryInfo()
    }
}