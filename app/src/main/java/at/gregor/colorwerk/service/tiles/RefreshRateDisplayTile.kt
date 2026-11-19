package at.gregor.colorwerk.service.tiles

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import org.lsposed.lsparanoid.Obfuscate
import at.gregor.colorwerk.IRefreshRateController
import at.gregor.colorwerk.service.RefreshRateService

@Obfuscate
class RefreshRateDisplayTile : TileService() {
    private var controller: IRefreshRateController? = null

    override fun onStartListening() {
        RefreshRateService.get(this) {
            controller = it
            refreshData()
        }
    }

    override fun onClick() {
        if (qsTile.state == Tile.STATE_INACTIVE) controller?.refreshRateDisplay = true
        else if (qsTile.state == Tile.STATE_ACTIVE) controller?.refreshRateDisplay = false
        refreshData()
    }

    private fun refreshData() {
        qsTile.state = if (controller == null) Tile.STATE_UNAVAILABLE
        else if (controller!!.refreshRateDisplay) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.updateTile()
    }
}
