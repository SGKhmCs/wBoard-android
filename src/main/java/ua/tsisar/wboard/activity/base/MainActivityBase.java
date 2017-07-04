package ua.tsisar.wboard.activity.base;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

import retrofit2.Response;
import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.dto.UserDTO;
import ua.tsisar.wboard.Message;
import ua.tsisar.wboard.service.listener.AccountListener;
import ua.tsisar.wboard.service.listener.BoardListener;

public class MainActivityBase extends AppCompatActivity implements AccountListener, BoardListener {
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
    public void onCreateBoardResponse(Response<BoardDTO> response) {
        Message.makeText(this, "Error",
                response.message() + ", status code: " + response.code()).show();
    }

    @Override
    public void onGetAllBoardsResponse(Response<List<BoardDTO>> response) {
        Message.makeText(this, "Error",
                response.message() + ", status code: " + response.code()).show();
    }

    @Override
    public void onSearchBoardsResponse(Response<List<BoardDTO>> response) {
        Message.makeText(this, "Error",
                response.message() + ", status code: " + response.code()).show();
    }

    @Override
    public void onFailure(Throwable throwable) {
        Message.makeText(this, "Error", throwable.getMessage()).show();
    }

}
