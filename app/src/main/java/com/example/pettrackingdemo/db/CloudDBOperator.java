package com.example.pettrackingdemo.db;

import android.content.Context;
import android.provider.UserDictionary;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.pettrackingdemo.tool.MyApplication;
import com.huawei.agconnect.cloud.database.AGConnectCloudDB;
import com.huawei.agconnect.cloud.database.CloudDBZone;
import com.huawei.agconnect.cloud.database.CloudDBZoneConfig;
import com.huawei.agconnect.cloud.database.CloudDBZoneObjectList;
import com.huawei.agconnect.cloud.database.CloudDBZoneQuery;
import com.huawei.agconnect.cloud.database.CloudDBZoneSnapshot;
import com.huawei.agconnect.cloud.database.exceptions.AGConnectCloudDBException;
import com.huawei.hmf.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudDBOperator {
    private static CloudDBZone mCloudDBZone;
    private static boolean isDBOpen = false;
    private final static String zoneName = "PetDemo";
    private final static String TAG = "CloudDBOperator";

    public static void openCloudDBZone(Context context){
        if(isDBOpen){
            Log.i(TAG, "openCloudDBZone: DB Zone already opened!");
           return;
        }
        try {
            //initialize cloud db
            AGConnectCloudDB.initialize(context);
            AGConnectCloudDB mCloudDB = AGConnectCloudDB.getInstance();
            mCloudDB.createObjectType(ObjectTypeInfoHelper.getObjectTypeInfo());

            CloudDBZoneConfig mConfig = new CloudDBZoneConfig(zoneName,
                    CloudDBZoneConfig.CloudDBZoneSyncProperty.CLOUDDBZONE_CLOUD_CACHE,
                    CloudDBZoneConfig.CloudDBZoneAccessProperty.CLOUDDBZONE_PUBLIC);
            mConfig.setPersistenceEnabled(true);
            Task<CloudDBZone> openDBZoneTask = mCloudDB.openCloudDBZone2(mConfig, true);
            openDBZoneTask.addOnSuccessListener(cloudDBZone -> {
                Log.i(TAG, "open cloudDBZone success");
                mCloudDBZone = cloudDBZone;
                isDBOpen = true;
            }).addOnFailureListener(e -> {
                Log.w(TAG, "open cloudDBZone failed for " + e.getMessage());
                isDBOpen = false;
            });
        }catch(Exception e){
            Log.e(TAG, "openCloudDBZone: " + e.getMessage());
            e.printStackTrace();
            isDBOpen = false;
        }
    }

//    public static void readWordFromDBByDate(MutableLiveData wordList){
//        if (!MyApplication.me.login || mCloudDBZone == null) {
//            Log.w(TAG, "CloudDBZone is null, try re-open it");
//            return;
//        }
//
//        CloudDBZoneQuery<UserDictionary.Words> query = CloudDBZoneQuery.where(Words.class);
//        query.equalTo(WordField.USER_ID, MyApplication.me.user.getUserID());
//        query.equalTo(WordField.ADD_DATE_STRING,MyApplication.me.selectedDate);
////        query.limit(10);
//        query.orderByAsc(WordField.ADD_DATE);
//
//        Task<CloudDBZoneSnapshot<Words>> queryTask = mCloudDBZone.executeQuery(query,
//                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY);
//        queryTask.addOnSuccessListener(snapshot -> {
//            try {
//                Log.d(TAG, "readWordFromDBByDate: processing result");
////                List<String> wordlist = new ArrayList<String>();
//                CloudDBZoneObjectList<Words> words = snapshot.getSnapshotObjects();
//                List<String> tmpList = new ArrayList<>();
//                while (words.hasNext()) {
//                    Words tmpWord = words.next();
//                    tmpList.add(tmpWord.getWord());
//                    Log.d(TAG, "readWordFromDBByDate: " + tmpWord.getWord());
//                }
//                wordList.setValue(tmpList);
//            } catch (AGConnectCloudDBException e) {
//                e.printStackTrace();
//            }
//        }).addOnFailureListener(e -> {
//            Log.e(TAG, "onFailure: Query failed");e.printStackTrace();
//        });
//    }
//
//    public static void readHelpFromDB(MutableLiveData voiceCommandList){
//        if (!MyApplication.me.login || mCloudDBZone == null) {
//            Log.w(TAG, "CloudDBZone is null, try re-open it");
//            return;
//        }
//
//        CloudDBZoneQuery<VoiceGuide> query = CloudDBZoneQuery.where(VoiceGuide.class);
//        query.orderByAsc("commandOrder");
//
//        Task<CloudDBZoneSnapshot<VoiceGuide>> queryTask = mCloudDBZone.executeQuery(query,
//                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY);
//        queryTask.addOnSuccessListener(snapshot -> {
//            try {
//                Log.d(TAG, "readHelpFromDB: processing result");
////                List<String> wordlist = new ArrayList<String>();
//                CloudDBZoneObjectList<VoiceGuide> voiceCommands = snapshot.getSnapshotObjects();
//                List tmpList = new ArrayList<Map<String, Spanned>>();
//                while (voiceCommands.hasNext()) {
//                    VoiceGuide tmpCommand = voiceCommands.next();
//                    Map tmpMap = new HashMap<String, Spanned>();
//                    tmpMap.put("command", Html.fromHtml(tmpCommand.getCommand()));
//                    tmpMap.put("commandDesc", Html.fromHtml(tmpCommand.getCommandDesc()));
//                    tmpMap.put("usageSample", Html.fromHtml(tmpCommand.getUsageSample()));
//                    tmpList.add(tmpMap);
//                    Log.d(TAG, "readWordFromDBByDate: " + tmpCommand.getCommand());
//                }
//                voiceCommandList.setValue(tmpList);
//            } catch (AGConnectCloudDBException e) {
//                e.printStackTrace();
//            }
//        }).addOnFailureListener(e -> {
//            Log.e(TAG, "onFailure: Query failed");e.printStackTrace();
//        });
//    }
//
//    //update or insert word to DB
//    public static void writeWord2DB(Words word){
//        if (mCloudDBZone == null) {
//            Log.w(TAG, "CloudDBZone is null, try re-open it");
//            return;
//        }
//        Task<Integer> upsertTask = mCloudDBZone.executeUpsert(word);
//        upsertTask.addOnSuccessListener(cloudDBZoneResult -> {
//            Log.i(TAG, "Upsert " + cloudDBZoneResult + " records");
////            Toast.makeText(,"请登录！", Toast.LENGTH_LONG).show();
//        }).addOnFailureListener(e -> {
//            Log.e(TAG, "writeUser2DB: add word failed.");
//            e.printStackTrace();
//        });
//    }
//
//    //delete word from DB
//    public static void deleteWordFromDB(Words word){
//        if (mCloudDBZone == null) {
//            Log.w(TAG, "CloudDBZone is null, try re-open it");
//            return;
//        }
//        Task<Integer> deleteTask = mCloudDBZone.executeDelete(word);
//        deleteTask.addOnSuccessListener(cloudDBZoneResult -> {
//            Log.i(TAG, "Delete " + cloudDBZoneResult + " records");
////            Toast.makeText(,"请登录！", Toast.LENGTH_LONG).show();
//        }).addOnFailureListener(e -> {
//            Log.e(TAG, "deleteWordFromDB: delete word failed.");
//            e.printStackTrace();
//        });
//    }

    public static void writeUser2DB(User user){
        if (mCloudDBZone == null) {
            Log.w(TAG, "CloudDBZone is null, try re-open it");
            return;
        }
        CloudDBZoneQuery<User> query = CloudDBZoneQuery.where(User.class);
        query.equalTo("userID", user.getUserID());
        Task<CloudDBZoneSnapshot<User>> queryTask = mCloudDBZone.executeQuery(query,
                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY);

        queryTask.addOnSuccessListener(snapshot -> {
            CloudDBZoneObjectList<User> queryUser = snapshot.getSnapshotObjects();
            //if its new user, add field for AddDate
            if(queryUser.size() <= 0) {
                user.setAddDate(new Date());
                Log.d(TAG, "writeUser2DB: new user, " + user.getUserID());
            }
            Task<Integer> upsertTask = mCloudDBZone.executeUpsert(user);
            upsertTask.addOnSuccessListener(cloudDBZoneResult -> Log.i(TAG, "Upsert " + cloudDBZoneResult + " records")).addOnFailureListener(e -> {
                Log.e(TAG, "writeUser2DB: add user failed.");
                e.printStackTrace();
            });
        }).addOnFailureListener(e -> {
            Log.e(TAG, "onFailure: Query failed");e.printStackTrace();
        });
    }
//
////    public static boolean exportAllWordsByUser(){
////
////        return true;
////    }
//
//    public static void readSettingFromDB(MutableLiveData Useretting){
//        if (!MyApplication.me.login || mCloudDBZone == null) {
//            Log.w(TAG, "CloudDBZone is null, try re-open it");
//            return;
//        }
//
//        CloudDBZoneQuery<Useretting> query = CloudDBZoneQuery.where(Useretting.class);
//        query.equalTo(WordField.USER_ID, MyApplication.me.user.getUserID());
//
//        Task<CloudDBZoneSnapshot<Useretting>> queryTask = mCloudDBZone.executeQuery(query,
//                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY);
//        queryTask.addOnSuccessListener(snapshot -> {
//            try {
//                Log.d(TAG, "readSettingFromDB: processing result");
////                List<String> wordlist = new ArrayList<String>();
//                CloudDBZoneObjectList<Useretting> setRes = snapshot.getSnapshotObjects();
//                MyApplication.me.Useretting.setUserID(MyApplication.me.user.getUserID());
//                if(setRes.size() == 1) {
//                    MyApplication.me.Useretting = setRes.next();
//                }
//                Useretting.setValue(MyApplication.me.Useretting);
//            } catch (AGConnectCloudDBException e) {
//                e.printStackTrace();
//            }
//        }).addOnFailureListener(e -> {
//            Log.e(TAG, "onFailure: Query failed");e.printStackTrace();
//        });
//    }
//
//    public static void writeSetting2DB(Useretting Useretting){
//        if (mCloudDBZone == null) {
//            Log.w(TAG, "CloudDBZone is null, try re-open it");
//            return;
//        }
//
//        Task<Integer> upsertTask = mCloudDBZone.executeUpsert(Useretting);
//        upsertTask.addOnSuccessListener(cloudDBZoneResult -> Log.i(TAG, "Upsert " + cloudDBZoneResult + " records")).addOnFailureListener(e -> {
//            Log.e(TAG, "writeUser2DB: add user failed.");
//            e.printStackTrace();
//        });
//    }
//
//    public static void readUserPerformanceByDate(MutableLiveData<List<Map<String, String>>> UserubjectLevelList, String dateString) {
//        if (!MyApplication.me.login || mCloudDBZone == null) {
//            Log.w(TAG, "CloudDBZone is null, try re-open it");
//            return;
//        }
//
//        CloudDBZoneQuery<UserubjectGradeLog> query = CloudDBZoneQuery.where(UserubjectGradeLog.class);
//        query.equalTo("userID", MyApplication.me.user.getUserID());
//        query.equalTo("recordDate", dateString);
//        query.orderByAsc("logTime");
//
//        Task<CloudDBZoneSnapshot<UserubjectGradeLog>> queryTask = mCloudDBZone.executeQuery(query,
//                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY);
//        queryTask.addOnSuccessListener(snapshot -> {
//            try {
//                Log.d(TAG, "readUserPerformanceByDate: processing result");
////                List<String> wordlist = new ArrayList<String>();
//                CloudDBZoneObjectList<UserubjectGradeLog> performanceResult = snapshot.getSnapshotObjects();
//                List tmpList = new ArrayList<Map<String, String>>();
//                while (performanceResult.hasNext()) {
//                    UserubjectGradeLog subjectLog = performanceResult.next();
//                    Map tmpMap = new HashMap<String, String>();
//                    tmpMap.put("subjectName", subjectLog.getSubjectName());
//                    tmpMap.put("subjectLevel", subjectLog.getSubjectLevel());
//                    tmpMap.put("logTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(subjectLog.getLogTime()));
//                    tmpMap.put("remark", subjectLog.getRemark().toString());
//                    tmpList.add(tmpMap);
//                }
//                UserubjectLevelList.setValue(tmpList);
//            } catch (AGConnectCloudDBException e) {
//                e.printStackTrace();
//            }
//        }).addOnFailureListener(e -> {
//            Log.e(TAG, "onFailure: Query failed");e.printStackTrace();
//        });
//    }
//
//    public static void readSubjectInfo() {
//        if (!MyApplication.me.login || mCloudDBZone == null) {
//            Log.w(TAG, "CloudDBZone is null, try re-open it");
//            return;
//        }
//
//        //query subject
//        CloudDBZoneQuery<Subject> query = CloudDBZoneQuery.where(Subject.class);
//        Task<CloudDBZoneSnapshot<Subject>> queryTask = mCloudDBZone.executeQuery(query,
//                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY);
//        queryTask.addOnSuccessListener(snapshot -> {
//            try {
//                Log.d(TAG, "readSubjectInfo: processing result");
////                List<String> wordlist = new ArrayList<String>();
//                CloudDBZoneObjectList<Subject> subjectRes = snapshot.getSnapshotObjects();
//                MyApplication.subject.clear();
//                while(subjectRes.hasNext())
//                    MyApplication.subject.add(subjectRes.next().getSubjectName());
//            } catch (AGConnectCloudDBException e) {
//                e.printStackTrace();
//            }
//        }).addOnFailureListener(e -> {
//            Log.e(TAG, "onFailure: Query failed");e.printStackTrace();
//        });
//
//        //query subject level
//        CloudDBZoneQuery<SubjectGradeLevel> query1 = CloudDBZoneQuery.where(SubjectGradeLevel.class);
//        Task<CloudDBZoneSnapshot<SubjectGradeLevel>> queryTask1 = mCloudDBZone.executeQuery(query1,
//                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY);
//        queryTask1.addOnSuccessListener(snapshot -> {
//            try {
//                Log.d(TAG, "readSubjectInfo: processing result");
////                List<String> wordlist = new ArrayList<String>();
//                CloudDBZoneObjectList<SubjectGradeLevel> subjectLevelRes = snapshot.getSnapshotObjects();
//                MyApplication.subjectLevel.clear();
//                List<String[]> subLevelRes = new ArrayList<>();
//                while(subjectLevelRes.hasNext()) {
//                    SubjectGradeLevel tmp = subjectLevelRes.next();
//                    String[] strTmp = {tmp.getSubjectName(), tmp.getSubjectLevel(), Integer.toString(tmp.getCalcRule())};
//                    subLevelRes.add(strTmp);
//                }
//                for(int i=0; i<subLevelRes.size(); i++){
//                    if(!MyApplication.subjectLevel.containsKey(subLevelRes.get(i)[0])){
//                        MyApplication.subjectLevel.put(subLevelRes.get(i)[0], new ArrayList<>());
//                    }
//                }
//                for(int i=0; i<subLevelRes.size(); i++){
//                    String[] subInfo = subLevelRes.get(i);
//                    String sub = subInfo[0];
//                    String[] levelInfo = {subInfo[1], subInfo[2]};
//                    List<String[]> tmpList = MyApplication.subjectLevel.get(sub);
//                    tmpList.add(levelInfo);
//                }
//                Log.d(TAG, "readSubjectInfo: finished.");
//            } catch (AGConnectCloudDBException e) {
//                e.printStackTrace();
//            }
//        }).addOnFailureListener(e -> {
//            Log.e(TAG, "onFailure: Query failed");e.printStackTrace();
//        });
//    }
}
