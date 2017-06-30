package ua.tsisar.wboard;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardService {

    private BoardListener listener;

    public BoardService(BoardListener listener){
        this.listener = listener;
    }

    public interface BoardListener {
        void onCreateBoardResponse(Response<BoardDTO> response);
        void onGetAllBoardsResponse(Response<List<BoardDTO>> response);
        void onSearchBoardsResponse(Response<List<BoardDTO>> response);
        void onFailure(Throwable throwable);
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
}
