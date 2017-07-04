package ua.tsisar.wboard.service.listener;

import retrofit2.Response;
import ua.tsisar.wboard.dto.TokenDTO;

public interface AuthenticateListener {
    void onAuthorizeResponse(Response<TokenDTO> response);
    void onFailure(Throwable throwable);
}
