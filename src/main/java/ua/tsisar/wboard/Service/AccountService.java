package ua.tsisar.wboard.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.DTO.UserDTO;
import ua.tsisar.wboard.Service.Listener.AccountListener;

public class AccountService {

    private AccountListener listener;

    public AccountService(AccountListener listener){
        this.listener = listener;
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

    public void registerAccount(UserDTO userDTO){
        Call<String> stringCall = App.getApi().registerAccount(userDTO);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                listener.onRegisterAccountResponse(response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                listener.onFailure(throwable);
            }
        });
    }
}
