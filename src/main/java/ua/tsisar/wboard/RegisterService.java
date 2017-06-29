package ua.tsisar.wboard;

import android.app.Activity;
import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RegisterService {

    private static final int RESULT_REGISTERED = 3;
    private Context context;

    private Activity getActivity() {
        return (Activity) context;
    }

    public RegisterService(Context context){
        this.context = context;
    }


    public void registerAccount(UserDTO userDTO){
        Call<String> stringCall = App.getApi().registerAccount(userDTO);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()){
                    case 201:
                        Message.makeText(getActivity(), "Registration saved!",
                                "Please check your email for confirmation.", RESULT_REGISTERED).show();
                        break;
                    default:
                        Message.makeText(getActivity(), "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Message.makeText(getActivity(), "Error", throwable.getMessage()).show();
            }
        });
    }
}
