package ua.tsisar.wboard.service.listener;

import ua.tsisar.wboard.dto.TokenDTO;

public interface AuthenticateListener {
    void onAuthorizeSuccess(TokenDTO tokenDTO);
    void onFailure(Throwable throwable);
}
