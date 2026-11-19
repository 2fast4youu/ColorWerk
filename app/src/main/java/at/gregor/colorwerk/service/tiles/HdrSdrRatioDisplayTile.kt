package at.gregor.colorwerk.service.tiles

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import org.lsposed.lsparanoid.Obfuscate
import at.gregor.colorwerk.IRefreshRateController
import at.gregor.colorwerk.service.RefreshRateService
import at.gregor.colorwerk.utils.A15
import at.gregor.colorwerk.utils.SDK

@Obfuscate
class HdrSdrRatioDisplayTile : TileService() {
    private var controller: IRefreshRateController? = null

    override fun onStartListening() {
        RefreshRateService.get(this) {
            controller = it
            refreshData()
        }
    }

    override fun onClick() {
        if (qsTile.state == Tile.STATE_INACTIVE) controller?.hdrSdrRatioOverlay = true
        else if (qsTile.state == Tile.STATE_ACTIVE) controller?.hdrSdrRatioOverlay = false
        refreshData()
    }

    private fun refreshData() {
        // 覆盖层由 SurfaceFlinger 提供，Android 15 才有 code 1043，更早的系统无法查询
        qsTile.state = if (SDK < A15 || controller == null) Tile.STATE_UNAVAILABLE
        else if (controller!!.hdrSdrRatioOverlay) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.updateTile()
    }
}
