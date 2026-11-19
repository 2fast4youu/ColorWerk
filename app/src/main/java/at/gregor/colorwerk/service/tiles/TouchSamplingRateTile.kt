package at.gregor.colorwerk.service.tiles

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import org.lsposed.lsparanoid.Obfuscate
import at.gregor.colorwerk.ITileServiceController
import at.gregor.colorwerk.service.TilesService
import at.gregor.colorwerk.utils.GlobalKeyValue.keyTouchSamplingRate
import at.gregor.colorwerk.utils.GlobalKeyValue.keyTouchSamplingRateLevel
import at.gregor.colorwerk.utils.SettingsPrefs
import at.gregor.colorwerk.utils.getString
import at.gregor.colorwerk.utils.putBoolean

@Obfuscate
class TouchSamplingRateTile : TileService() {
    private var controller: ITileServiceController? = null

    override fun onStartListening() {
        TilesService.get(this) {
            controller = it
            refreshData()
        }
    }

    override fun onClick() {
        val level = getString(SettingsPrefs, keyTouchSamplingRateLevel, "240")
        when (qsTile.state) {
            Tile.STATE_INACTIVE -> {
                controller?.touchMode = level.toInt()
                putBoolean(SettingsPrefs, keyTouchSamplingRate, true)
            }

            Tile.STATE_ACTIVE -> {
                controller?.touchMode = 0
                putBoolean(SettingsPrefs, keyTouchSamplingRate, false)
            }

            Tile.STATE_UNAVAILABLE -> {}
        }
        refreshData()
    }

    private fun refreshData() {
        qsTile.state = if (controller == null) Tile.STATE_UNAVAILABLE
        else if (!controller!!.checkTouchMode()) Tile.STATE_UNAVAILABLE
        else if (controller!!.touchMode > 0) Tile.STATE_ACTIVE
        else Tile.STATE_INACTIVE
        qsTile.updateTile()
        if (qsTile.state == Tile.STATE_UNAVAILABLE) putBoolean(
            SettingsPrefs, keyTouchSamplingRate, false
        )
    }
}
