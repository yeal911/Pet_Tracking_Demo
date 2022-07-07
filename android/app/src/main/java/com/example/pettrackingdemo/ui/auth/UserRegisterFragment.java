package com.example.pettrackingdemo.ui.auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pettrackingdemo.databinding.FragmentRegisterBinding;
import com.example.pettrackingdemo.hms.auth.AuthController;
import com.example.pettrackingdemo.hms.auth.User;
import com.example.pettrackingdemo.tool.UserController;
import com.huawei.agconnect.auth.VerifyCodeResult;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class UserRegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.idButtonGetCode.setOnClickListener(view1 ->
                AuthController.getInstance().requestVerificationCode(binding.idUserLogin.getText().toString(),
                        new AuthController.VerificationCodeCallback() {
                            @Override
                            public void onVerificationCodeSuccess(VerifyCodeResult result) {
                                Toast.makeText(getContext(), "Verification code has been sent to your mail",
                                        Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onVerificationCodeError(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }));

        binding.idVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if (text.length() == 6 && binding.idPass.length() >= 7) {
                    binding.idButtonSignUp.setEnabled(true);
                } else {
                    binding.idButtonSignUp.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.idButtonSignUp.setOnClickListener(view12 -> AuthController.getInstance()
                .signInWithVerificationCodeMailAndPassword(
                        binding.idUserLogin.getText().toString(),
                        binding.idPass.getText().toString(),
                        binding.idVerificationCode.getText().toString(), new AuthController.RegisterCallback() {
                            @Override
                            public void onSignUpSuccess(User user) {
                                UserController.getInstance().putUser(user.getUid(), user.getName(),
                                        user.getEmail(), new UserController.PutUserInfoCallback() {
                                            @Override
                                            public void onSuccess(int code) {

                                                Toast.makeText(getContext(), "Sign up successfully", Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onError(Throwable throwable) {
                                                Toast.makeText(getContext(), "Sign up failed:" + throwable.getLocalizedMessage(),
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });

                            }

                            @Override
                            public void onSignUpError(Throwable throwable) {
                                Toast.makeText(getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        }));

    }

}