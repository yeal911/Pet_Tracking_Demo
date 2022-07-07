package com.example.pettrackingdemo.ui.pet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.pettrackingdemo.databinding.FragmentPetDetailsBinding;
import com.example.pettrackingdemo.databinding.FragmentPetRegisterBinding;
import com.example.pettrackingdemo.databinding.FragmentRegisterBinding;
import com.example.pettrackingdemo.hms.auth.AuthController;
import com.example.pettrackingdemo.tool.UserController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RegisterPetFragment extends Fragment {

    private FragmentPetRegisterBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPetRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        String[] values = new String[]{"Dog", "Cat", "Bird"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, values);
        binding.idPetType.setAdapter(adapter);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.idButtonPetRegister.setOnClickListener(view1 -> {
            String petType = binding.idPetType.getSelectedItem().toString();
            String petName = binding.idPetName.getText().toString();
            UserController.getInstance().putPetToUserId(AuthController.getInstance().getUser().getUid(),
                    petName, petType, new UserController.PutPetCallback() {
                        @Override
                        public void onSuccess(int code) {
                            Toast.makeText(getContext(),
                                    "Pet registered successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Toast.makeText(getContext(),
                                    throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        }
                    });

        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }
}