package com.example.pettrackingdemo.ui.home;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.pettrackingdemo.databinding.FragmentHomeBinding;
import com.example.pettrackingdemo.db.CloudDBOperator;
import com.example.pettrackingdemo.host.response.PetTrackingItem;
import com.example.pettrackingdemo.tool.MyApplication;
import com.example.pettrackingdemo.tool.PetTrackingController;
import com.google.android.material.navigation.NavigationView;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectAuthCredential;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.agconnect.auth.HwIdAuthProvider;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMapOptions;
import com.huawei.hms.maps.MapFragment;
import com.huawei.hms.maps.MapView;
import com.example.pettrackingdemo.R;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.example.pettrackingdemo.tool.PermissionManager;
import com.huawei.hms.maps.model.MapStyleOptions;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private HuaweiMap hMap;
    private MapView mMapView;
    private View root;

    private final int SIGN_CODE = 100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        mMapView = root.findViewById(R.id.huaweiMap);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle("MapViewBundleKey");
        }
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        //initialize DB and user login
        MyApplication.initAppData(getActivity().getParent());

        setListener();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        mMapView.onDestroy();
    }

    public void onMapReady(HuaweiMap huaweiMap) {
        Log.d(TAG, "onMapReady: ");

        if (!PermissionManager.RequestPermissions(root.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION ) || !PermissionManager.RequestPermissions(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION ))
            return;

        hMap = huaweiMap;
        this.switchMap2DayTime();
        hMap.setMyLocationEnabled(true);
        hMap.getUiSettings().setMyLocationButtonEnabled(true);
        hMap.setLanguage("en");
        PetTrackingController.getInstance().getTracking(new PetTrackingController.PetTrackingCallback() {
            @Override
            public void onPetTrackingResponse(ArrayList<PetTrackingItem> trackingItems) {

            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

//    public void addMarker(){
////        Marker marker = new Marker();
////    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void switchMap2DayTime(){
        hMap.setStyleId(MyApplication.dayMapStyleID);
        hMap.previewId(MyApplication.dayMapStylePreID);
    }

    private void switchMap2NightTime(){
        hMap.setStyleId(MyApplication.nightMapStyleID);
        hMap.previewId(MyApplication.nightMapStylePreID);
    }

    private void setListener() {
        SwitchCompat switchCompat = (SwitchCompat)root.findViewById(R.id.switch1);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d(TAG, "onCheckedChanged: " + b);
                if(b){
                    switchCompat.setText("Switch to Day View");
                    switchMap2NightTime();
                }else{
                    switchCompat.setText("Switch to Night View");
                    switchMap2DayTime();
                }
            }
        });

        ImageView loginImg = root.findViewById(R.id.userImageView);
        loginImg.setOnClickListener(v -> loginUser());
    }

    //login
    private void loginUser(){
        AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
        if(user != null){
            setLoginUser();
            Toast.makeText(root.getContext(), "you have already signed in!", Toast.LENGTH_LONG).show();
            Log.i(TAG, "loginUser: you have already signed in!");
            return;
        }
        AccountAuthParams authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setAccessToken()
                .createParams();
        AccountAuthService accountAuthService = AccountAuthManager.getService(this.getContext(), authParams);
        //在需要的时候开启登录流程，比如你可以创建一个按钮，按钮的点击事件中调用下面的方法
        startActivityForResult(accountAuthService.getSignInIntent(), SIGN_CODE);
    }

    private void setLoginUser(){
        MyApplication myApp = (MyApplication) getActivity().getParent().getApplication();
        if(myApp.login) {
            NavigationView navigationView = root.findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            ImageView loginImg = headerView.findViewById(R.id.userImageView);
            Glide.with(this).load(myApp.user.getPhotoUrl().toString()).into(loginImg);

            //因为CLOUD DB的bug（数据库实际未完成打开动作，但是已返回成功），导致不得不延时执行写入操作
            new Handler().postDelayed(() -> {
                //execute the task
                //insert user or update user to cloud db
                CloudDBOperator.writeUser2DB(myApp.user);
                Log.d(TAG, "setLoginUser: write user.");
            },2000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == this.SIGN_CODE){
            Task<AuthAccount> authAccountTask  = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                AuthAccount authAccount = authAccountTask.getResult();
                Log.i(TAG, "accessToken:" + authAccount.getAccessToken());
                AGConnectAuthCredential credential = HwIdAuthProvider.credentialWithToken(authAccount.getAccessToken());
                AGConnectAuth.getInstance().signIn(credential).addOnSuccessListener(signInResult -> {
                    // onSuccess
                    MyApplication.initAppData(getActivity());
                    setLoginUser();
                }).addOnFailureListener(e -> {
                    // onFail
                    Log.e(TAG, "onFailure: " + e.getMessage() );
                });
            }
        }
    }
}