package ua.tsisar.wboard.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import retrofit2.Response;
import ua.tsisar.wboard.Service.BoardService;
import ua.tsisar.wboard.DTO.BoardDTO;
import ua.tsisar.wboard.Message;
import ua.tsisar.wboard.R;
import ua.tsisar.wboard.Service.Listener.BoardListener;

public class BoardsActivity extends AppCompatActivity implements BoardListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_boards);

        BoardService boardService = new  BoardService(this);
        boardService.getAllBoards(0, 3, "name");
        boardService.searchBoards(0, 10, "brd2", "name");
        boardService.getBoard(951);
        BoardDTO boardDTO = new BoardDTO(952, "brdUUUU", true);
        boardService.updateBoard(boardDTO);
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
