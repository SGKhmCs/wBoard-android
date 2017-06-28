package ua.tsisar.wboard;

import android.app.Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pavel on 28.06.17.
 */

public class RegisterService {

    private RegisterListener listener;

    private Activity getActivity() {
        return (Activity) listener;
    }

    public RegisterService(RegisterListener listener){
        this.listener = listener;
    }

    public interface RegisterListener {
        void registered(int responseCode);
    }

    public void registerAccount(UserDTO userDTO){
        Call<String> stringCall = App.getApi().registerAccount(userDTO);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()){
                    case 201:
                        listener.registered(response.code());
                        Message.makeText(getActivity(), "Registration saved!",
                                "Please check your email for confirmation.").show();
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
