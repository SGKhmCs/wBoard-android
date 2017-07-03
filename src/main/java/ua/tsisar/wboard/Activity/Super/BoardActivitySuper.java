package ua.tsisar.wboard.Activity.Super;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

import retrofit2.Response;
import ua.tsisar.wboard.DTO.BoardDTO;
import ua.tsisar.wboard.Message;
import ua.tsisar.wboard.Service.Listener.BoardListener;


public class BoardActivitySuper extends AppCompatActivity implements BoardListener{
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
