package com.example.pettrackingdemo.ui.home;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pettrackingdemo.databinding.FragmentHomeBinding;
import com.example.pettrackingdemo.db.Pet;
import com.example.pettrackingdemo.hms.auth.AuthController;
import com.example.pettrackingdemo.hms.auth.User;
import com.example.pettrackingdemo.host.internal.PetTrackingItem;
import com.example.pettrackingdemo.host.internal.Position;
import com.example.pettrackingdemo.host.internal.Routes;
import com.example.pettrackingdemo.host.internal.Step;
import com.example.pettrackingdemo.tool.LocationManager;
import com.example.pettrackingdemo.tool.MyApplication;
import com.example.pettrackingdemo.tool.PetTrackingController;
import com.example.pettrackingdemo.tool.RouteController;
import com.example.pettrackingdemo.tool.UserController;
import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.example.pettrackingdemo.R;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.example.pettrackingdemo.tool.PermissionManager;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private HuaweiMap hMap;
    private MapView mMapView;
    private View root;
    private LatLng currentLocation;
    ArrayList<Pet> pets = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        mMapView = root.findViewById(R.id.huaweiMap);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle("MapViewBundleKey");
        }
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
        MyApplication.initAppData(getActivity().getParent());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pets.clear();
        loadPetsForUser();
        binding.idPetSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getPetTracking(pets.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        mMapView.onDestroy();
    }

    public void onMapReady(HuaweiMap huaweiMap) {
        Log.d(TAG, "onMapReady: ");

        if (!PermissionManager.RequestPermissions(root.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) || !PermissionManager.RequestPermissions(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION))
            return;

        hMap = huaweiMap;
        this.switchMap2DayTime();
        hMap.setMyLocationEnabled(true);
        hMap.getUiSettings().setMyLocationButtonEnabled(true);
        hMap.setLanguage("en");

        getCurrentLocation();
    }

    public void addMarker(int tag, LatLng position, PetTrackingItem item, boolean last) {
        MarkerOptions options = new MarkerOptions()
                .position(position)
                .title(tag + ". " + item.getFormattedDate())
                .visible(true);

        Marker marker = hMap.addMarker(options);
        if (tag == 1) {
            marker.showInfoWindow();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(position);
            hMap.animateCamera(cameraUpdate);
//            cameraUpdate = CameraUpdateFactory.zoomBy(5);
            hMap.animateCamera(cameraUpdate);
        }
        if (last) {
            Button button = getView().findViewById(R.id.readTrackbutton);
            button.setOnClickListener(view -> {
                if (currentLocation != null) {
                    RouteController.getInstance().getRoute("driving", currentLocation, position, new RouteController.RouteCallback() {
                        @Override
                        public void onRouteResult(Routes routes) {
                            List<Step> steps = routes.getRoutes().get(0).getPaths().get(0).getSteps();

                            for (Step item : steps) {
                                int index = 0;
                                LatLng[] lats = new LatLng[item.getPolyline().size()];
                                for (Position pos : item.getPolyline()) {
                                    lats[index++] = new LatLng(pos.getLat(), pos.getLng());
                                }
                                addPolyline(lats);
                            }
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Toast.makeText(getContext(), "Error planning route", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "No location yet", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    public void addPolyline(LatLng[] values) {
        hMap.addPolyline(new PolylineOptions()
                .add(values)
                .color(Color.BLUE)
                .width(3));
    }

    public void getCurrentLocation() {
        LocationManager.getInstance().getLocation(getContext(),
                latLng -> currentLocation = latLng);
    }

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

    private void switchMap2DayTime() {
        hMap.setStyleId(MyApplication.dayMapStyleID);
        hMap.previewId(MyApplication.dayMapStylePreID);
    }

    private void switchMap2NightTime() {
        hMap.setStyleId(MyApplication.nightMapStyleID);
        hMap.previewId(MyApplication.nightMapStylePreID);
    }


    private void loadPetsForUser() {
        User user = AuthController.getInstance().getUser();
        ArrayList<String> items = new ArrayList<>();
        if (user != null && user.getUid() != null) {
            UserController.getInstance().getPetsForUserId(user.getUid(), new UserController.GetPetsCallback() {
                @Override
                public void onSuccess(ArrayList<Pet> values) {
                    pets = values;
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_list_item_1, items);
                    for (Pet item : values) {
                        items.add(item.getPetName());
                    }
                    binding.idPetSelection.setAdapter(adapter);
                }

                @Override
                public void onError(Throwable throwable) {
                    Toast.makeText(getContext(),
                            throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void getPetTracking(Pet pet) {
        PetTrackingController.getInstance().getTracking(pet.getPetID(), new PetTrackingController.PetTrackingCallback() {
            @Override
            public void onPetTrackingResponse(ArrayList<PetTrackingItem> trackingItems) {
                managePetTracking(trackingItems);
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void managePetTracking(ArrayList<PetTrackingItem> trackingItems) {
        int index = 1;
        try {
            hMap.clear();

            LatLng[] values = new LatLng[trackingItems.size()];
            for (PetTrackingItem item : trackingItems) {
                if (item.getLat() != null && item.getLon() != null) {
                    LatLng latLng = new LatLng(Double.parseDouble(item.getLat()),
                            Double.parseDouble(item.getLon()));
                    values[index - 1] = latLng;
                    addMarker(index++, latLng, item, (index - 1) == trackingItems.size());
                }
            }
            addPolyline(values);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No values to display", Toast.LENGTH_LONG).show();
        }
    }

}