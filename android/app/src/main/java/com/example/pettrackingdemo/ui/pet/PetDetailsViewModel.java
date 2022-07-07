package com.example.pettrackingdemo.ui.pet;

import android.widget.Button;
import android.widget.ListView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PetDetailsViewModel extends ViewModel {

    private MutableLiveData<Button> name;
    private MutableLiveData<ListView> locations;

}