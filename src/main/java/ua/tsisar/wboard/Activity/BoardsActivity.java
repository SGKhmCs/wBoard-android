package ua.tsisar.wboard.Activity;

import android.content.Context;
import android.os.Bundle;

import java.util.List;

import retrofit2.Response;
import ua.tsisar.wboard.Activity.Super.BoardActivitySuper;
import ua.tsisar.wboard.Service.BoardService;
import ua.tsisar.wboard.DTO.BoardDTO;
import ua.tsisar.wboard.R;

public class BoardsActivity extends BoardActivitySuper {

    private static final int RESPONSE_OK = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards);

        final BoardService boardService = new  BoardService(this);
        boardService.getAllBoards(0, 3, "name");
        boardService.searchBoards(0, 10, "brd2", "name");
        boardService.getBoard(951);
        BoardDTO boardDTO = new BoardDTO(952, "brdUUUU", true);
        boardService.updateBoard(boardDTO);
    }

    @Override
    public void onCreateBoardResponse(Response<BoardDTO> response) {
        super.onCreateBoardResponse(response);
    }

    @Override
    public void onGetAllBoardsResponse(Response<List<BoardDTO>> response) {
        if(response.code() == RESPONSE_OK){
            List list = response.body();
            return;
        }
        super.onGetAllBoardsResponse(response);
    }

    @Override
    public void onSearchBoardsResponse(Response<List<BoardDTO>> response) {
        if(response.code() == RESPONSE_OK){
            List list = response.body();
            return;
        }
        super.onSearchBoardsResponse(response);
    }
}
