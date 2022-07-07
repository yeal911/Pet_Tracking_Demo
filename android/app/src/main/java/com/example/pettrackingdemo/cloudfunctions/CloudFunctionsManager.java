package com.example.pettrackingdemo.cloudfunctions;

import com.huawei.agconnect.function.AGConnectFunction;
import com.huawei.agconnect.function.FunctionResult;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;

import org.json.JSONObject;

public class CloudFunctionsManager {
    AGConnectFunction function;

    String url = "petracking-$latest";

    public void init() {
        function = AGConnectFunction.getInstance();
        System.out.println("---init::function: " + function);
    }

    public void callTo(String path) {
        System.out.println("---calling to function");
        Task<FunctionResult> task = function.wrap(url).call(new JSONObject());
        task.addOnCompleteListener(new OnCompleteListener<FunctionResult>() {
            @Override
            public void onComplete(Task<FunctionResult> task) {
                System.out.println("---on complete result: "+task.getResult().getValue());
                System.out.println("---on complete: "+task.getResult().getValue());
            }
        });


    }

    private static CloudFunctionsManager instance;

    public static CloudFunctionsManager getInstance() {
        if (instance == null) {
            instance = new CloudFunctionsManager();
        }
        return instance;
    }
}
