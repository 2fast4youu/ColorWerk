package at.gregor.colorwerk;

import android.content.pm.UserInfo;

interface IActivityServiceController {
    void forceStopPackage(String packageName,int userId);
    UserInfo getCurrentUser();
}