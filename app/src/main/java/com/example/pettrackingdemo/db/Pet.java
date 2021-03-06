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
 * Definition of ObjectType Pet.
 *
 * @since 2022-04-20
 */
@PrimaryKeys({"PetID"})
@Indexes({"idx_id:PetID"})
public final class Pet extends CloudDBZoneObject {
    private String PetID;

    private String PetName;

    private String PetDesc;

    private String Ownership;

    public Pet() {
        super(Pet.class);
    }

    public void setPetID(String PetID) {
        this.PetID = PetID;
    }

    public String getPetID() {
        return PetID;
    }

    public void setPetName(String PetName) {
        this.PetName = PetName;
    }

    public String getPetName() {
        return PetName;
    }

    public void setPetDesc(String PetDesc) {
        this.PetDesc = PetDesc;
    }

    public String getPetDesc() {
        return PetDesc;
    }

    public void setOwnership(String Ownership) {
        this.Ownership = Ownership;
    }

    public String getOwnership() {
        return Ownership;
    }

}
