package com.example.pettrackingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pettrackingdemo.databinding.ActivityLoginBinding;
import com.example.pettrackingdemo.hms.auth.AuthController;
import com.example.pettrackingdemo.hms.auth.User;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onInit();

    }

    private void onInit() {
        binding.idCreateAccount.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                   startActivity(intent);
               }
           });

        binding.idButtonSignIn.setOnClickListener(view12 -> {
            String email = binding.idUserLogin.getText().toString();
            String password = binding.idPass.getText().toString();
            AuthController.getInstance().signInWithMailAndPassword(email,
                    password, new AuthController.SignInCallback() {
                        @Override
                        public void onSignInSuccess(User user) {

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onSignInError(Throwable throwable) {
                            throwable.printStackTrace();
                            Toast.makeText(getBaseContext(),
                                    "Wrong user/password", Toast.LENGTH_LONG).show();
                        }
                    });

        });
    }

}