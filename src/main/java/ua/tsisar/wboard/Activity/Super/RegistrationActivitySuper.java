package ua.tsisar.wboard.Activity.Super;

import android.support.v7.app.AppCompatActivity;

import retrofit2.Response;
import ua.tsisar.wboard.DTO.UserDTO;
import ua.tsisar.wboard.Message;
import ua.tsisar.wboard.Service.Listener.AccountListener;

public class RegistrationActivitySuper extends AppCompatActivity implements AccountListener {
    @Override
    public void onGetAccountResponse(Response<UserDTO> response) {
        Message.makeText(this, "Error",
                response.message() + ", status code: " + response.code()).show();
    }

    @Override
    public void onIsAuthenticatedResponse(Response<String> response) {
        Message.makeText(this, "Error",
                response.message() + ", status code: " + response.code()).show();
    }

    @Override
    public void onSaveAccountResponse(Response<String> response) {
        Message.makeText(this, "Error",
                response.message() + ", status code: " + response.code()).show();
    }

    @Override
    public void onChangePasswordResponse(Response<String> response) {
        Message.makeText(this, "Error",
                response.message() + ", status code: " + response.code()).show();
    }

    @Override
    public void onRegisterAccountResponse(Response<String> response) {
        Message.makeText(this, "Error",
                response.message() + ", status code: " + response.code()).show();
    }

    @Override
    public void onFailure(Throwable throwable) {
        Message.makeText(this, "Error", throwable.getMessage()).show();
    }
}
