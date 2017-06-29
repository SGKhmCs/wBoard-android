package ua.tsisar.wboard;

import android.app.Activity;
import android.content.Context;

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
                        listener.onBoarCreated(boardDTO);
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
}
