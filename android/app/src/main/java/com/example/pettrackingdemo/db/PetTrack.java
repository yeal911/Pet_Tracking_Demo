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
 * Definition of ObjectType PetTrack.
 *
 * @since 2022-04-20
 */
@PrimaryKeys({"RecordID"})
@Indexes({"idx_petid:PetID,Date", "idx_id:RecordID"})
public final class PetTrack extends CloudDBZoneObject {
    private String RecordID;

    private String PetID;

    private String Date;

    private String RecordTime;

    private String Longtitude;

    private String Latitude;

    private String Remark;

    public PetTrack() {
        super(PetTrack.class);
    }

    public void setRecordID(String RecordID) {
        this.RecordID = RecordID;
    }

    public String getRecordID() {
        return RecordID;
    }

    public void setPetID(String PetID) {
        this.PetID = PetID;
    }

    public String getPetID() {
        return PetID;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getDate() {
        return Date;
    }

    public void setRecordTime(String RecordTime) {
        this.RecordTime = RecordTime;
    }

    public String getRecordTime() {
        return RecordTime;
    }

    public void setLongtitude(String Longtitude) {
        this.Longtitude = Longtitude;
    }

    public String getLongtitude() {
        return Longtitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getRemark() {
        return Remark;
    }

}