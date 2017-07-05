package ua.tsisar.wboard.rest.helper.listener;

import ua.tsisar.wboard.dto.TokenDTO;

public interface AuthenticateListener {
    void onAuthorizeSuccess(TokenDTO tokenDTO);
    void onFailure(Throwable throwable);
}
