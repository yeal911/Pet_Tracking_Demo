package com.example.pettrackingdemo.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.pettrackingdemo.R;
import com.example.pettrackingdemo.databinding.FragmentLoginBinding;
import com.example.pettrackingdemo.hms.auth.AuthController;
import com.example.pettrackingdemo.hms.auth.User;

public class UserLoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        UserLoginViewModel userLoginViewModel =
                new ViewModelProvider(this).get(UserLoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.idCreateAccount.setOnClickListener(view1 ->
                Navigation.findNavController(view).navigate(R.id.navigation_user_register));

        binding.idButtonSignIn.setOnClickListener(view12 -> {
            String email = binding.idUserLogin.getText().toString();
            String password = binding.idPass.getText().toString();
            AuthController.getInstance().signInWithMailAndPassword(email,
                    password, new AuthController.SignInCallback() {
                        @Override
                        public void onSignInSuccess(User user) {
                            Navigation.findNavController(view12).navigate(R.id.navigation_user_info);
                        }

                        @Override
                        public void onSignInError(Throwable throwable) {
                            throwable.printStackTrace();
                            Toast.makeText(getContext(), "Wrong user/password", Toast.LENGTH_LONG).show();
                        }
                    });

        });
    }
}