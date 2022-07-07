package com.example.pettrackingdemo.ui.pet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.pettrackingdemo.R;
import com.example.pettrackingdemo.databinding.FragmentListPetBinding;
import com.example.pettrackingdemo.db.Pet;
import com.example.pettrackingdemo.hms.auth.AuthController;
import com.example.pettrackingdemo.tool.UserController;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class ListPetFragment extends Fragment {

    private FragmentListPetBinding binding;
    ArrayList<PetListRowAdapter.PetInfo> userPets = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentListPetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getPetListForCurrentUser();
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding.idPetList.setOnItemClickListener((adapterView, view1, i, l) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(PetDetailsFragment.PARAMS_KEY, userPets.get(i));
            Navigation.findNavController(view1).navigate(R.id.navigation_pet_details, bundle);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pet_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_register_pet: {
                Navigation.findNavController(getView()).navigate(R.id.navigation_pet_register);
                return true;
            }
        }
        return false;
    }

    private void getPetListForCurrentUser() {
        userPets.clear();
        UserController.getInstance().getPetsForUserId(AuthController.getInstance().getUser().getUid(),
                new UserController.GetPetsCallback() {
                    @Override
                    public void onSuccess(ArrayList<Pet> pets) {

                        for (Pet pet : pets) {
                            PetListRowAdapter.PetInfo item = new PetListRowAdapter.PetInfo();
                            item.setName(pet.getPetName());
                            item.setId(pet.getPetID());
                            userPets.add(item);
                        }
                        PetListRowAdapter adapter = new PetListRowAdapter(getContext(), userPets);
                        binding.idPetList.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(getContext(), throwable.getLocalizedMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

    }
}