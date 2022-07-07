package com.example.pettrackingdemo.tool;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.example.pettrackingdemo.db.User;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyApplication extends Application {
    public static MyApplication me;
    public User user;
    public boolean login;
    private final static String TAG = "MyApplication";

    public final static String dayMapStyleID = "872602768391801344";
    public final static String dayMapStylePreID = "872602768391801344";
    public final static String nightMapStyleID = "873918093821478912";
    public final static String nightMapStylePreID = "873917772823005696";

    //dynamic selection setting
    public String selectedDate;
    //for baidu translation, and dict query
    private static String baiduAppID;
    private static String baiduPrivateKey;
    //for huawei ML
    private static String huaweiMLApiKey;
    //for xunfei voice recognition
    private static String xunfeiAppID;

    //subject info
    public static List<String> subject;
    public static Map<String, List<String[]>> subjectLevel;

    @Override
    public void onCreate() {
        super.onCreate();
        me = this;


        //init auth service
        if (AGConnectInstance.getInstance() == null) {
            AGConnectInstance.initialize(getApplicationContext());
        }


    }

    //initialize cloud DB and login status
    public static void initAppData(Activity activity){
//        initializeLogin(activity);
//        CloudDBOperator.openCloudDBZone(activity);
    }

    //if user has login, then set the login details upon app open
    public static void initializeLogin(Activity activity){
        AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
        if(user != null) {
            MyApplication myApp = (MyApplication) activity.getApplication();
            myApp.login = true;
            myApp.user.setUserID(user.getUid());
            myApp.user.setUserName(user.getDisplayName());
            myApp.user.setPhotoUrl(user.getPhotoUrl());
            myApp.user.setLastLoginDate(new Date());
            myApp.user.setUserType("Huawei");
        }
        Log.d(TAG, "initializeLogin: user initialized.");
    }
}
