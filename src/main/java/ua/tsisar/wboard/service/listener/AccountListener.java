package ua.tsisar.wboard.service.listener;

import retrofit2.Response;
import ua.tsisar.wboard.dto.UserDTO;

public interface AccountListener {
    void onGetAccountResponse(Response<UserDTO> response);
    void onIsAuthenticatedResponse(Response<String> response);
    void onSaveAccountResponse(Response<String> response);
    void onChangePasswordResponse(Response<String> response);
    void onRegisterAccountResponse(Response<String> response);
    void onFailure(Throwable throwable);
}
