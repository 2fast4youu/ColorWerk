package at.gregor.colorwerk.service.tiles

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.telephony.SubscriptionManager
import org.lsposed.lsparanoid.Obfuscate
import at.gregor.colorwerk.ITileServiceController
import at.gregor.colorwerk.service.TilesService

@Obfuscate
class FiveGTile : TileService() {
    private var controller: ITileServiceController? = null

    override fun onStartListening() {
        TilesService.get(this) {
            controller = it
            refreshData()
        }
    }

    override fun onClick() {
        val subId = SubscriptionManager.getDefaultDataSubscriptionId()
        if (qsTile.state == Tile.STATE_INACTIVE) controller?.setFiveGStatus(subId, true)
        else if (qsTile.state == Tile.STATE_ACTIVE) controller?.setFiveGStatus(subId, false)
        refreshData()
    }

    private fun refreshData() {
        val subId = SubscriptionManager.getDefaultDataSubscriptionId()
        qsTile.state = if (controller == null) Tile.STATE_UNAVAILABLE
        else if (!controller!!.checkCompatibility(subId)) Tile.STATE_UNAVAILABLE
        else if (controller!!.getFiveGStatus(subId)) Tile.STATE_ACTIVE
        else Tile.STATE_INACTIVE
        qsTile.updateTile()
    }
}
