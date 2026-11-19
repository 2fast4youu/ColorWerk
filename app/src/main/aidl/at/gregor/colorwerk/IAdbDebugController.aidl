package at.gregor.colorwerk;

interface IAdbDebugController {
    int getAdbPort();
    void setAdbPort(int port);
    String getWifiIP();
    void restartAdb();
}