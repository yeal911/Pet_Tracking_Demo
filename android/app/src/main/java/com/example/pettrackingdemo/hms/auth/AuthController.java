package com.example.pettrackingdemo.hms.auth;

import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectAuthCredential;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.agconnect.auth.EmailAuthProvider;
import com.huawei.agconnect.auth.EmailUser;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.agconnect.auth.TokenResult;
import com.huawei.agconnect.auth.VerifyCodeResult;
import com.huawei.agconnect.auth.VerifyCodeSettings;
import com.huawei.hmf.tasks.Task;

import java.util.Locale;

public class AuthController {
    private static AuthController instance;

    public interface VerificationCodeCallback {
        void onVerificationCodeSuccess(VerifyCodeResult result);

        void onVerificationCodeError(Throwable throwable);
    }

    public interface RegisterCallback {
        void onSignUpSuccess(User user);

        void onSignUpError(Throwable throwable);
    }

    public interface SignInCallback {
        void onSignInSuccess(User user);

        void onSignInError(Throwable throwable);
    }

    public interface GetUserInfoCallback {
        void onGetUserInfo(boolean success, User user);
    }

    private AuthController() {

    }

    private final User user = new User();

    public void getCurrentUser(GetUserInfoCallback callback) {
        AGConnectUser agcUser = AGConnectAuth.getInstance().getCurrentUser();
        user.setEmail(agcUser.getEmail());
        user.setPhone(agcUser.getPhone());
        user.setUid(agcUser.getUid());
        Task<TokenResult> tokenTask = agcUser.getToken(true);
        tokenTask.addOnSuccessListener(tokenResult -> {
            user.setToken(tokenResult.getToken());
            callback.onGetUserInfo(true, user);
        }).addOnFailureListener(e -> {
            e.printStackTrace();
            callback.onGetUserInfo(false, null);
        });
    }

    public void requestVerificationCode(String email, VerificationCodeCallback callback) {
        VerifyCodeSettings settings = new VerifyCodeSettings.Builder()
                .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
                .sendInterval(30)
                .locale(Locale.ENGLISH)
                .build();
        Task<VerifyCodeResult> task = AGConnectAuth.getInstance().requestVerifyCode(email, settings);
        task.addOnSuccessListener(verifyCodeResult ->
                callback.onVerificationCodeSuccess(verifyCodeResult))
                .addOnFailureListener(e -> {
                    callback.onVerificationCodeError(e);
                    e.printStackTrace();
                });
    }

    public void signInWithVerificationCodeMailAndPassword(String mail, String pass, String code,
                                                          RegisterCallback callback) {
        EmailUser emailUser = new EmailUser.Builder()
                .setEmail(mail)
                .setVerifyCode(code)
                .setPassword(pass)
                .build();
        Task<SignInResult> task = AGConnectAuth.getInstance().createUser(emailUser);
        task.addOnCompleteListener(task1 -> {
            try {
                if (task1.isSuccessful()) {
                    SignInResult signInResult = task1.getResult();
                    user.setEmail(signInResult.getUser().getEmail());
                    user.setName(signInResult.getUser().getDisplayName());
                    user.setUid(signInResult.getUser().getUid());
                    callback.onSignUpSuccess(user);
                } else {
                    callback.onSignUpError(task1.getException());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void signInWithMailAndPassword(String mail, String password, SignInCallback callback) {
        AGConnectAuthCredential credential = EmailAuthProvider.credentialWithPassword(mail, password);

        AGConnectAuth.getInstance().signIn(credential).addOnSuccessListener(signInResult -> {
            if (signInResult != null) {
                user.setEmail(signInResult.getUser().getEmail());
                user.setName(signInResult.getUser().getDisplayName());
                user.setUid(signInResult.getUser().getUid());
            }
            callback.onSignInSuccess(user);

        }).addOnFailureListener(e -> {

            if (e.getMessage() != null && e.getMessage().contains("code: 5")) {
                getCurrentUser((success, user) -> {
                    if (success) {
                        callback.onSignInSuccess(user);
                    } else {
                        callback.onSignInError(e);
                    }

                });
            } else {
                callback.onSignInError(e);
            }

        });
    }

    public User getUser() {
        return this.user;
    }


    public static AuthController getInstance() {
        if (instance == null) {
            instance = new AuthController();
        }
        return instance;
    }
}
