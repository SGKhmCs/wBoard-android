package ua.tsisar.wboard.Service.Listener;

import retrofit2.Response;
import ua.tsisar.wboard.DTO.TokenDTO;

public interface AuthenticateListener {
    void onAuthorizeResponse(Response<TokenDTO> response);
    void onFailure(Throwable throwable);
}
