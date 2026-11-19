package at.gregor.colorwerk;

interface IPackageServiceController {
    void clearApplicationProfileData(String packageName);
    boolean performDexOptMode(String packageName);
}