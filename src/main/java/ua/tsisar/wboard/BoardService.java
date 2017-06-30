package ua.tsisar.wboard;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardService {

    private BoardListener listener;
    private Context context;

    public BoardService(Context context){
        this.context = context;
    }

    public BoardService(BoardListener listener){
        this.listener = listener;
    }

    public BoardService(Context context, BoardListener listener){
        this.context = context;
        this.listener = listener;
    }

    public interface BoardListener {
        void onBoarCreated(BoardDTO boardDTO);
    }

    private Activity getActivity() {
        if(context == null) {
            return (Activity) listener;
        }else {
            return (Activity) context;
        }
    }

    public void createBoard(final BoardDTO boardDTO){
        Call<BoardDTO> boardDTOCall = App.getApi().createBoard(App.getTokenDTO().getIdTokenEx(), boardDTO);
        boardDTOCall.enqueue(new Callback<BoardDTO>() {

            @Override
            public void onResponse(Call<BoardDTO> call, Response<BoardDTO> response) {
                switch (response.code()){
                    case 201:
                        listener.onBoarCreated(response.body());
                        Message.makeText(getActivity(), "Created!",
                                "Your board created.").show();
                        break;
                    default:
                        Message.makeText(getActivity(), "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<BoardDTO> call, Throwable throwable) {
                Message.makeText(getActivity(), "Error", throwable.getMessage()).show();
            }
        });
    }

    public void getAllBoards(int page, int size, String... sort){
        Call<List<BoardDTO>> listCall = App.getApi().getAllBoards(App.getTokenDTO().getIdTokenEx(), page, size, sort);
        listCall.enqueue(new Callback<List<BoardDTO>>() {
            @Override
            public void onResponse(Call<List<BoardDTO>> call, Response<List<BoardDTO>> response) {
                switch (response.code()){
                    case 200:
                        List list = response.body();
                        break;
                    default:
                        Message.makeText(getActivity(), "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<BoardDTO>> call, Throwable throwable) {
                Message.makeText(getActivity(), "Error", throwable.getMessage()).show();
            }
        });
    }

    public void searchBoards(int page, int size, String query, String... sort){
        Call<List<BoardDTO>> listCall = App.getApi().searchBoards(App.getTokenDTO().getIdTokenEx(), page, size, query, sort);
        listCall.enqueue(new Callback<List<BoardDTO>>() {
            @Override
            public void onResponse(Call<List<BoardDTO>> call, Response<List<BoardDTO>> response) {
                switch (response.code()){
                    case 200:
                        List list = response.body();
                        break;
                    default:
                        Message.makeText(getActivity(), "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<BoardDTO>> call, Throwable throwable) {
                Message.makeText(getActivity(), "Error", throwable.getMessage()).show();
            }
        });
    }
}
