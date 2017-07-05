package ua.tsisar.wboard.activity.base;

import android.support.v7.app.AppCompatActivity;

import retrofit2.Response;
import ua.tsisar.wboard.dto.TokenDTO;
import ua.tsisar.wboard.dto.UserDTO;
import ua.tsisar.wboard.Message;
import ua.tsisar.wboard.service.listener.AccountListener;
import ua.tsisar.wboard.service.listener.AuthenticateListener;

public class LoginActivityBase extends AppCompatActivity implements AuthenticateListener, AccountListener {


    @Override
    public void onAuthorizeSuccess(TokenDTO tokenDTO) {
    }

    @Override
    public void onGetAccountSuccess(UserDTO userDTO) {

    }

    @Override
    public void onIsAuthenticatedSuccess(String string) {

    }

    @Override
    public void onSaveAccountSuccess(String string) {

    }

    @Override
    public void onChangePasswordSuccess(String string) {

    }

    @Override
    public void onRegisterAccountSuccess(String string) {

    }

    @Override
    public void onFailure(Throwable throwable) {
        Message.makeText(this, "Error", throwable.getMessage()).show();
    }
}
