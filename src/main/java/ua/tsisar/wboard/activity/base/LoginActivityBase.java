package ua.tsisar.wboard.activity.base;

import android.support.v7.app.AppCompatActivity;

import com.github.mrengineer13.snackbar.SnackBar;

import ua.tsisar.wboard.dto.TokenDTO;
import ua.tsisar.wboard.dto.UserDTO;
import ua.tsisar.wboard.rest.helper.listener.AccountListener;
import ua.tsisar.wboard.rest.helper.listener.AuthenticateListener;

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
        new SnackBar.Builder(this)
                .withMessage(throwable.getMessage())
                .withStyle(SnackBar.Style.ALERT)
                .show();
    }
}
