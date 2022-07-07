package com.example.pettrackingdemo.ui.auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserLoginViewModel extends ViewModel {

    private MutableLiveData<String> user;
    private MutableLiveData<String> pass;

    public UserLoginViewModel() {
        user = new MutableLiveData<>();
        pass = new MutableLiveData<>();
    }

    public MutableLiveData<String> getUser() {
        return user;
    }

    public void setUser(MutableLiveData<String> user) {
        this.user = user;
    }

    public MutableLiveData<String> getPass() {
        return pass;
    }

    public void setPass(MutableLiveData<String> pass) {
        this.pass = pass;
    }
}