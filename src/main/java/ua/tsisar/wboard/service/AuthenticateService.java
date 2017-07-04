package ua.tsisar.wboard.service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.dto.AuthorizeDTO;
import ua.tsisar.wboard.dto.TokenDTO;
import ua.tsisar.wboard.service.listener.AuthenticateListener;

public class AuthenticateService {

    private AuthenticateListener listener;

    public AuthenticateService(AuthenticateListener listener){
        this.listener = listener;
    }

    public void authorize(AuthorizeDTO authorizeDTO){
        Call<TokenDTO> tokenCall = App.getApi().authorize(authorizeDTO);
        tokenCall.enqueue(new Callback<TokenDTO>() {
            @Override
            public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                listener.onAuthorizeResponse(response);
            }

            @Override
            public void onFailure(Call<TokenDTO> call, Throwable throwable) {
                listener.onFailure(throwable);
            }
        });
    }

}
