package ua.tsisar.wboard.service;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.service.listener.BoardListener;

public class BoardService {

    private BoardListener listener;

    private String getIdToken(){
        return App.getTokenDTO().getIdTokenEx();
    }

    public BoardService(BoardListener listener){
        this.listener = listener;
    }


    public void searchBoards(int page, int size, String query, String... sort){
        App.getApi().searchBoards(getIdToken(), page, size, query, sort)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(list -> listener.onSearchBoardsSuccess(list),
                  throwable -> listener.onFailure(throwable));
    }

    public void getAllBoards(int page, int size, String... sort){
        App.getApi().getAllBoards(getIdToken(), page, size, sort)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> listener.onGetAllBoardsSuccess(list),
                      throwable -> listener.onFailure(throwable));
    }

    public void createBoard(final BoardDTO boardDTO){
        App.getApi().createBoard(getIdToken(), boardDTO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(b -> listener.onCreateBoardSuccess(b),
               throwable -> listener.onFailure(throwable));
    }

    public void updateBoard(final BoardDTO boardDTO){
        App.getApi().updateBoard(getIdToken(), boardDTO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(b -> listener.onUpdateBoardSuccess(b),
               throwable -> listener.onFailure(throwable));
    }

    public void getBoard(final long id){
        App.getApi().getBoard(getIdToken(), id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(b -> listener.onGetBoardSuccess(b),
               throwable -> listener.onFailure(throwable));
    }
}