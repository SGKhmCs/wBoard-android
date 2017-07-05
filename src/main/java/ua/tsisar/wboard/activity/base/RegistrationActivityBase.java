package ua.tsisar.wboard.activity.base;

import android.support.v7.app.AppCompatActivity;

import com.github.mrengineer13.snackbar.SnackBar;

import ua.tsisar.wboard.dto.UserDTO;
import ua.tsisar.wboard.rest.helper.listener.AccountListener;

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
        new SnackBar.Builder(this)
                .withMessage(throwable.getMessage())
                .withStyle(SnackBar.Style.ALERT)
                .show();
    }
}
