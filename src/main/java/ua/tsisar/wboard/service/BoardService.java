package ua.tsisar.wboard.service;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.service.listener.BoardListener;

public class BoardService {

    private BoardListener listener;


    public BoardService(BoardListener listener){
        this.listener = listener;
    }

    public void searchBoards(int page, int size, String query, String... sort){
        Call<List<BoardDTO>> listCall = App.getApi().searchBoards(App.getTokenDTO().getIdTokenEx(), page, size, query, sort);
        listCall.enqueue(new Callback<List<BoardDTO>>() {

            @Override
            public void onResponse(Call<List<BoardDTO>> call, Response<List<BoardDTO>> response) {
                listener.onSearchBoardsResponse(response);
            }

            @Override
            public void onFailure(Call<List<BoardDTO>> call, Throwable throwable) {
                listener.onFailure(throwable);
            }
        });
    }

    public void getAllBoards(int page, int size, String... sort){
        Call<List<BoardDTO>> listCall = App.getApi().getAllBoards(App.getTokenDTO().getIdTokenEx(), page, size, sort);
        listCall.enqueue(new Callback<List<BoardDTO>>() {

            @Override
            public void onResponse(Call<List<BoardDTO>> call, Response<List<BoardDTO>> response) {
                listener.onGetAllBoardsResponse(response);
            }

            @Override
            public void onFailure(Call<List<BoardDTO>> call, Throwable throwable) {
                listener.onFailure(throwable);
            }
        });
    }

    public void createBoard(final BoardDTO boardDTO){
        Call<BoardDTO> boardDTOCall = App.getApi().createBoard(App.getTokenDTO().getIdTokenEx(), boardDTO);
        boardDTOCall.enqueue(new Callback<BoardDTO>() {

            @Override
            public void onResponse(Call<BoardDTO> call, Response<BoardDTO> response) {
                listener.onCreateBoardResponse(response);
            }

            @Override
            public void onFailure(Call<BoardDTO> call, Throwable throwable) {
                listener.onFailure(throwable);
            }
        });
    }

    public void updateBoard(final BoardDTO boardDTO){
        Call<BoardDTO> boardDTOCall = App.getApi().updateBoard(App.getTokenDTO().getIdTokenEx(), boardDTO);
        boardDTOCall.enqueue(new Callback<BoardDTO>() {

            @Override
            public void onResponse(Call<BoardDTO> call, Response<BoardDTO> response) {
                BoardDTO b = response.body();
            }

            @Override
            public void onFailure(Call<BoardDTO> call, Throwable throwable) {
            }
        });
    }

    public void getBoard(final long id){
        Call<BoardDTO> boardDTOCall = App.getApi().getBoard(App.getTokenDTO().getIdTokenEx(), id);
        boardDTOCall.enqueue(new Callback<BoardDTO>() {

            @Override
            public void onResponse(Call<BoardDTO> call, Response<BoardDTO> response) {
                BoardDTO b = response.body();
            }

            @Override
            public void onFailure(Call<BoardDTO> call, Throwable throwable) {
            }
        });
    }

}