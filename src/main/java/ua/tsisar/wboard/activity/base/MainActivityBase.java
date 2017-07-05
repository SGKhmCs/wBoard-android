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
    public void onSearchBoardsSuccess(List<BoardDTO> list) {

    }

    @Override
    public void onGetAllBoardsSuccess(List<BoardDTO> list) {

    }

    @Override
    public void onCreateBoardSuccess(BoardDTO boardDTO) {

    }

    @Override
    public void onUpdateBoardSuccess(BoardDTO boardDTO) {

    }

    @Override
    public void onGetBoardSuccess(BoardDTO boardDTO) {

    }

    @Override
    public void onFailure(Throwable throwable) {
        Message.makeText(this, "Error", throwable.getMessage()).show();
    }

}
