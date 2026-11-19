package at.gregor.colorwerk;

interface IRefreshRateController {
    boolean getRefreshRateDisplay();
    void setRefreshRateDisplay(boolean status);
    List getSupportModes();
    void setRefreshRateMode(int modeId);
    void resetRefreshRateMode();

    boolean getHdrSdrRatioOverlay();
    void setHdrSdrRatioOverlay(boolean enabled);
}