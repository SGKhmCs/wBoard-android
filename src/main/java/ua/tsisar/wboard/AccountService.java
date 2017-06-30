package ua.tsisar.wboard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountService {

    private static final int RESULT_PSW_CHANGED = 2;
    private AccountListener listener;

    public AccountService(AccountListener listener){
        this.listener = listener;
    }

    public interface AccountListener {
        void onGetAccountResponse(Response<UserDTO> response);
        void onIsAuthenticatedResponse(Response<String> response);
        void onSaveAccountResponse(Response<String> response);
        void onChangePasswordResponse(Response<String> response);
        void onFailure(Throwable throwable);
    }


    public void getAccount(){
        Call<UserDTO> userCall = App.getApi().getAccount(App.getTokenDTO().getIdTokenEx());
        userCall.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                listener.onGetAccountResponse(response);
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable throwable) {
                listener.onFailure(throwable);
            }
        });
    }

    public void saveAccount(UserDTO userDTO){
        Call<String> stringCall = App.getApi().saveAccount(App.getTokenDTO().getIdTokenEx(), userDTO);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                listener.onSaveAccountResponse(response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                listener.onFailure(throwable);
            }
        });
    }

    public void changePassword(String password){
        Call<String> stringCall = App.getApi().changePassword(App.getTokenDTO().getIdTokenEx(), password);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                listener.onChangePasswordResponse(response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                listener.onFailure(throwable);
            }
        });
    }

    public void isAuthenticated(){
        Call<String> stringCall = App.getApi().isAuthenticated(App.getTokenDTO().getIdTokenEx());
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                listener.onIsAuthenticatedResponse(response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                listener.onFailure(throwable);
            }
        });
    }
}
