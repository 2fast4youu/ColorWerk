package at.gregor.colorwerk;

interface IPowerServiceController {
    void reboot(boolean confirm, String reason, boolean wait);
    void rebootSafeMode(boolean confirm, boolean wait);
}