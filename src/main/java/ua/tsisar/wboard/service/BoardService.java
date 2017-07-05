package ua.tsisar.wboard.service;


import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.dto.TokenDTO;
import ua.tsisar.wboard.service.listener.BoardListener;

public class BoardService {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private BoardListener listener;

    private String getIdToken(){
        return App.getTokenDTO().getIdTokenEx();
    }

    public BoardService(BoardListener listener){
        this.listener = listener;
    }

    public void searchBoards(int page, int size, String query, String... sort){
        compositeDisposable.add(App.getApi().searchBoards(getIdToken(), page, size, query, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<BoardDTO>>(){
                    @Override
                    public void onSuccess(List<BoardDTO> list) {
                        listener.onSearchBoardsSuccess(list);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.onFailure(throwable);
                    }
                }));
    }

    public void getAllBoards(int page, int size, String... sort){
        compositeDisposable.add(App.getApi().getAllBoards(getIdToken(), page, size, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<BoardDTO>>(){
                    @Override
                    public void onSuccess(List<BoardDTO> list) {
                        listener.onGetAllBoardsSuccess(list);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.onFailure(throwable);
                    }
                }));
    }

    public void createBoard(final BoardDTO boardDTO){
        compositeDisposable.add(App.getApi().createBoard(getIdToken(), boardDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BoardDTO>(){
                    @Override
                    public void onSuccess(BoardDTO b) {
                        listener.onCreateBoardSuccess(b);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.onFailure(throwable);
                    }
                }));
    }

    public void updateBoard(final BoardDTO boardDTO){
        compositeDisposable.add(App.getApi().updateBoard(getIdToken(), boardDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BoardDTO>(){
                    @Override
                    public void onSuccess(BoardDTO b) {
                        listener.onUpdateBoardSuccess(b);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.onFailure(throwable);
                    }
                }));
    }

    public void getBoard(final long id){
        compositeDisposable.add(App.getApi().getBoard(getIdToken(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BoardDTO>(){
                    @Override
                    public void onSuccess(BoardDTO b) {
                        listener.onGetBoardSuccess(b);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.onFailure(throwable);
                    }
                }));
    }

}