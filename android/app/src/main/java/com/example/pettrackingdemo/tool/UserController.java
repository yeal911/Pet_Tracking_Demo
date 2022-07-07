package com.example.pettrackingdemo.tool;

import com.example.pettrackingdemo.db.Pet;
import com.example.pettrackingdemo.host.internal.HostAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserController {
    private static UserController instance;

    public interface PutUserInfoCallback {
        void onSuccess(int code);

        void onError(Throwable throwable);
    }

    public interface PutPetCallback {
        void onSuccess(int code);

        void onError(Throwable throwable);
    }

    public interface GetPetsCallback {
        void onSuccess(ArrayList<Pet> values);

        void onError(Throwable throwable);
    }

    public interface DeletePetCallback {
        void onSuccess();

        void onError(Throwable throwable);
    }

    public void putUser(String uid, String name, String email, PutUserInfoCallback callback) {
        HostAdapter client = new HostAdapter();
        client.putUserInfo(uid, name, email).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                callback.onSuccess(response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void putPetToUserId(String userId, String petName, String petType, PutPetCallback callback) {
        HostAdapter client = new HostAdapter();
        client.putPet(userId, petName, petType).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onSuccess(0);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getPetsForUserId(String userId, GetPetsCallback callback) {
        HostAdapter client = new HostAdapter();
        client.getPetForUserId(userId).enqueue(new Callback<ArrayList<Pet>>() {
            @Override
            public void onResponse(Call<ArrayList<Pet>> call, Response<ArrayList<Pet>> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Pet>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void deletePetByPetId(String petId, DeletePetCallback callback) {
        HostAdapter client = new HostAdapter();
        client.deletePetByPetId(petId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t);
            }
        });
    }


    public void clearAllLocationsByPetId(String petId, DeletePetCallback callback) {
        HostAdapter client = new HostAdapter();
        client.clearAllLocationsByPetId(petId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t);
            }
        });
    }




    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }
}
