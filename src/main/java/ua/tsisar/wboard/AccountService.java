package ua.tsisar.wboard;


import android.app.Activity;
import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountService {

    private static final int RESULT_SAVED = 1;
    private static final int RESULT_PSW_CHANGED = 2;
    private AccountListener listener;
    private Context context;

    private Activity getActivity() {
       if(context == null) {
           return (Activity) listener;
       }else {
           return (Activity) context;
       }
    }

    public AccountService(Context context){
        this.context = context;
    }

    public AccountService(AccountListener listener){
        this.listener = listener;
    }

    public AccountService(Context context, AccountListener listener){
        this.context = context;
        this.listener = listener;
    }

    public interface AccountListener {
        void onAccountGetter(UserDTO userDTO);
    }


    public void getAccount(){
        Call<UserDTO> userCall = App.getApi().getAccount(App.getTokenDTO().getIdTokenEx());
        userCall.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                switch (response.code()){
                    case 200:
                        listener.onAccountGetter(response.body());
                        break;
                    default:
                        Message.makeText(getActivity(), "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable throwable) {
                Message.makeText(getActivity(), "Error", throwable.getMessage()).show();
            }
        });
    }

    public void saveAccount(UserDTO userDTO){
        Call<String> stringCall = App.getApi().saveAccount(App.getTokenDTO().getIdTokenEx(), userDTO);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()){
                    case 200:
                        Message.makeText(getActivity(), "Saved!",
                                "Your settings saved.", RESULT_SAVED).show();
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

    public void changePassword(String password){
        Call<String> stringCall = App.getApi().changePassword(App.getTokenDTO().getIdTokenEx(), password);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()){
                    case 200:
                        Message.makeText(getActivity(), "Password saved!",
                                "Your password saved.", RESULT_PSW_CHANGED).show();
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
