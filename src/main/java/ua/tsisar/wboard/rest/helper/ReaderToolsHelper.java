package ua.tsisar.wboard.rest.helper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.dto.ReaderToolsDTO;
import ua.tsisar.wboard.rest.helper.listener.ReaderToolsListener;

public class ReaderToolsHelper {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ReaderToolsListener listener;

    private String getIdToken(){
        return App.getTokenDTO().getIdTokenEx();
    }

    public ReaderToolsHelper(ReaderToolsListener listener){
        this.listener = listener;
    }

    public void getAllReaderToolsByBoardId(int page, int size, Long boardId, String... sort){
        compositeDisposable.add(App.getApi().getAllReaderToolsByBoardId(getIdToken(), page, size, boardId, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> listener.onGetAllReaderToolsByBoardIdSuccess(list),
                      throwable -> listener.onFailure(throwable)));
    }

    public void searchReaderTools(int page, int size, String query, String... sort){
        compositeDisposable.add(App.getApi().searchReaderTools(getIdToken(), page, size, query, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> listener.onSearchReaderToolsSuccess(list),
                      throwable -> listener.onFailure(throwable)));
    }

    public void getAllReaderTools(int page, int size, String... sort){
        compositeDisposable.add(App.getApi().getAllReaderTools(getIdToken(), page, size, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> listener.onGetAllReaderToolsSuccess(list),
                      throwable -> listener.onFailure(throwable)));
    }

    public void createReaderTools(final ReaderToolsDTO readerToolsDTO){
        compositeDisposable.add(App.getApi().createReaderTools(getIdToken(), readerToolsDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b -> listener.onCreateReaderToolsSuccess(b),
                        throwable -> listener.onFailure(throwable)));
    }

    public void updateReaderTools(final ReaderToolsDTO readerToolsDTO){
        compositeDisposable.add(App.getApi().updateReaderTools(getIdToken(), readerToolsDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b -> listener.onUpdateReaderToolsSuccess(b),
                        throwable -> listener.onFailure(throwable)));
    }

    //TODO fix return
    public void deleteReaderTools(final long id){
        compositeDisposable.add(App.getApi().deleteReaderTools(getIdToken(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(string -> listener.onDeleteReaderToolsSuccess(string),
                        throwable -> listener.onFailure(throwable)));
    }

    public void getReaderTools(final long id){
        compositeDisposable.add(App.getApi().getReaderTools(getIdToken(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> listener.onGetReaderToolsSuccess(r),
                   throwable -> listener.onFailure(throwable)));
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
