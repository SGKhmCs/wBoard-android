package ua.tsisar.wboard.rest.helper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.rest.helper.listener.BoardListener;

public class BoardHelper {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private BoardListener listener;

    private String getIdToken(){
        return App.getTokenDTO().getIdTokenEx();
    }

    public BoardHelper(BoardListener listener){
        this.listener = listener;
    }


    public void searchBoards(int page, int size, String query, String... sort){
        compositeDisposable.add(App.getApi().searchBoards(getIdToken(), page, size, query, sort)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(list -> listener.onSearchBoardsSuccess(list),
                  throwable -> listener.onFailure(throwable)));
    }

    public void getAllBoards(int page, int size, String... sort){
        compositeDisposable.add(App.getApi().getAllBoards(getIdToken(), page, size, sort)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(list -> listener.onGetAllBoardsSuccess(list),
                  throwable -> listener.onFailure(throwable)));
    }

    public void createBoard(final BoardDTO boardDTO){
        compositeDisposable.add(App.getApi().createBoard(getIdToken(), boardDTO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(b -> listener.onCreateBoardSuccess(b),
               throwable -> listener.onFailure(throwable)));
    }

    public void updateBoard(final BoardDTO boardDTO){
        compositeDisposable.add(App.getApi().updateBoard(getIdToken(), boardDTO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(b -> listener.onUpdateBoardSuccess(b),
               throwable -> listener.onFailure(throwable)));
    }

    public void getBoard(final long id){
        compositeDisposable.add(App.getApi().getBoard(getIdToken(), id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(b -> listener.onGetBoardSuccess(b),
               throwable -> listener.onFailure(throwable)));
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}