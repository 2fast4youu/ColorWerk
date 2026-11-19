package at.gregor.colorwerk.service.tiles

import android.service.quicksettings.TileService
import at.gregor.colorwerk.utils.A15
import at.gregor.colorwerk.utils.IntentUtils
import at.gregor.colorwerk.utils.SDK
import at.gregor.colorwerk.utils.closeCollapse
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
class HighPerformanceModeTile : TileService() {
    override fun onClick() {
        closeCollapse()
        if (SDK >= A15) IntentUtils(this).jumpBattery()
        else IntentUtils(this).jumpHighPerformance()
    }
}