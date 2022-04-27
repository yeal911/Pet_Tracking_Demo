/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 * Generated by the CloudDB ObjectType compiler.  DO NOT EDIT!
 */
package com.example.pettrackingdemo.db;

import com.huawei.agconnect.cloud.database.CloudDBZoneObject;
import com.huawei.agconnect.cloud.database.Text;
import com.huawei.agconnect.cloud.database.annotations.DefaultValue;
import com.huawei.agconnect.cloud.database.annotations.EntireEncrypted;
import com.huawei.agconnect.cloud.database.annotations.NotNull;
import com.huawei.agconnect.cloud.database.annotations.Indexes;
import com.huawei.agconnect.cloud.database.annotations.PrimaryKeys;

import java.util.Date;

/**
 * Definition of ObjectType User.
 *
 * @since 2022-04-20
 */
@PrimaryKeys({"userID"})
@Indexes({"idx_userid:userID"})
public final class User extends CloudDBZoneObject {
    private String userID;

    private Date addDate;

    private Date lastLoginDate;

    private Text remark;

    private String userName;

    private String userType;

    private Text photoUrl;

    public User() {
        super(User.class);
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setRemark(Text remark) {
        this.remark = remark;
    }

    public Text getRemark() {
        return remark;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setPhotoUrl(Text photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Text getPhotoUrl() {
        return photoUrl;
    }

}