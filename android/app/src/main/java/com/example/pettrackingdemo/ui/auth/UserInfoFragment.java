package com.example.pettrackingdemo.ui.auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pettrackingdemo.R;
import com.example.pettrackingdemo.databinding.FragmentRegisterBinding;
import com.example.pettrackingdemo.databinding.FragmentUserInfoBinding;
import com.example.pettrackingdemo.hms.auth.AuthController;
import com.example.pettrackingdemo.hms.auth.User;
import com.example.pettrackingdemo.tool.UserController;
import com.huawei.agconnect.auth.VerifyCodeResult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class UserInfoFragment extends Fragment {

    private FragmentUserInfoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.idEmail.setText(AuthController.getInstance().getUser().getEmail());
        binding.idLastLogin.setText("Today");

       binding.idButtonManagePet.setOnClickListener(view1 ->
               Navigation.findNavController(view1).navigate(R.id.navigation_list_pet));
    }


}