package com.example.pettrackingdemo.ui.pet;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.pettrackingdemo.databinding.FragmentPetDetailsBinding;
import com.example.pettrackingdemo.db.User;
import com.example.pettrackingdemo.host.internal.Constants;
import com.example.pettrackingdemo.host.internal.PetTrackingItem;
import com.example.pettrackingdemo.tool.PetTrackingController;
import com.example.pettrackingdemo.tool.UserController;
import com.example.pettrackingdemo.tool.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PetDetailsFragment extends Fragment {

    private FragmentPetDetailsBinding binding;
    PetListRowAdapter.PetInfo petInfo;
    public static String PARAMS_KEY = "PET_INFO";
    ArrayList<String> locations = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPetDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        petInfo = (PetListRowAdapter.PetInfo) getArguments().getSerializable(PARAMS_KEY);
        binding.idPetName.setText(petInfo.getName());
        loadLocations();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.idButtonDelete.setOnClickListener(view1 -> {
            if (petInfo != null) {
                UserController.getInstance().deletePetByPetId(petInfo.getId(), new UserController.DeletePetCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getContext(), "Pet deleted", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        binding.idAddRandomLocation.setOnClickListener(view12 -> {
            double[] coords = Utils.generateRandomLocation(Constants.MADRID_LAT,
                    Constants.MADRID_LON);
            String lat = String.valueOf(coords[0]);
            String lon = String.valueOf(coords[1]);
            PetTrackingController.getInstance().putPetLocation(petInfo.getId(), lat, lon,
                    new PetTrackingController.PetPutLocationCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getContext(), "Location added successfully", Toast.LENGTH_LONG).show();
                            loadLocations();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Toast.makeText(getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        });

        binding.idClearAllLocations.setOnClickListener(view13 ->
                UserController.getInstance().clearAllLocationsByPetId(petInfo.getId(),
                        new UserController.DeletePetCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getContext(), "Locations deleted", Toast.LENGTH_LONG).show();
                                loadLocations();
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                Toast.makeText(getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        }));

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void loadLocations() {

        locations.clear();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, locations);

        PetTrackingController.getInstance().getTracking(petInfo.getId(), new PetTrackingController.PetTrackingCallback() {
            @Override
            public void onPetTrackingResponse(ArrayList<PetTrackingItem> trackingItems) {
                for (PetTrackingItem item : trackingItems) {
                    String location = "lat: " + item.getLat() + " lon: " + item.getLon();
                    String date = item.getDate();
                    locations.add(location);
                }
                binding.idPetLocations.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(getContext(), throwable.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });


    }
}