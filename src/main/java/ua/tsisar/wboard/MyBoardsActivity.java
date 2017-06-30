package ua.tsisar.wboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import retrofit2.Response;

public class MyBoardsActivity extends AppCompatActivity implements BoardService.BoardListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_boards);

        BoardService boardService = new  BoardService(this);
        boardService.getAllBoards(0, 3, "name");
        boardService.searchBoards(0, 10, "brd2", "name");
    }

    @Override
    public void onCreateBoardResponse(Response<BoardDTO> response) {

    }

    @Override
    public void onGetAllBoardsResponse(Response<List<BoardDTO>> response) {
        switch (response.code()){
            case 200:
                List list = response.body();
                break;
            default:
                Message.makeText(this, "Error",
                        response.message() + ", status code: " + response.code()).show();
                break;
        }
    }

    @Override
    public void onSearchBoardsResponse(Response<List<BoardDTO>> response) {
        switch (response.code()){
            case 200:
                List list = response.body();
                break;
            default:
                Message.makeText(this, "Error",
                        response.message() + ", status code: " + response.code()).show();
                break;
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        Message.makeText(this, "Error", throwable.getMessage()).show();
    }
}
