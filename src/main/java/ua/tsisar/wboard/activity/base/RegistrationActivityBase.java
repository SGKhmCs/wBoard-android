package ua.tsisar.wboard.activity.base;

import android.support.v7.app.AppCompatActivity;

import retrofit2.Response;
import ua.tsisar.wboard.dto.UserDTO;
import ua.tsisar.wboard.Message;
import ua.tsisar.wboard.service.listener.AccountListener;

public class RegistrationActivityBase extends AppCompatActivity implements AccountListener {

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
