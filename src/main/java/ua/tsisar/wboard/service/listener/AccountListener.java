package ua.tsisar.wboard.service.listener;

import retrofit2.Response;
import ua.tsisar.wboard.dto.UserDTO;

public interface AccountListener {
    void onGetAccountSuccess(UserDTO userDTO);
    void onIsAuthenticatedSuccess(String string);
    void onSaveAccountSuccess(String string);
    void onChangePasswordSuccess(String string);
    void onRegisterAccountSuccess(String string);
    void onFailure(Throwable throwable);
}
