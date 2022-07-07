package com.example.pettrackingdemo.ui.pet;

import android.widget.ListView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListPetViewModel extends ViewModel {

    private MutableLiveData<ListView> list;

    public MutableLiveData<ListView> getList() {
        return list;
    }

    public void setList(MutableLiveData<ListView> list) {
        this.list = list;
    }
}