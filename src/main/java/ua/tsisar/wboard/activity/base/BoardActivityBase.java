package ua.tsisar.wboard.activity.base;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.Message;
import ua.tsisar.wboard.rest.helper.listener.BoardListener;


public class BoardActivityBase extends AppCompatActivity implements BoardListener{


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
