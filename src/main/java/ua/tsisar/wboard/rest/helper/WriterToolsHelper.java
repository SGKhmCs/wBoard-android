package ua.tsisar.wboard.rest.helper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.dto.WriterToolsDTO;
import ua.tsisar.wboard.rest.helper.listener.WriterToolsListener;

public class WriterToolsHelper {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private WriterToolsListener listener;

    private String getIdToken(){
        return App.getTokenDTO().getIdTokenEx();
    }

    public WriterToolsHelper(WriterToolsListener listener){
        this.listener = listener;
    }

    public void getAllWriterToolsByBoardId(int page, int size, Long boardId, String... sort){
        compositeDisposable.add(App.getApi().getAllWriterToolsByBoardId(getIdToken(), page, size, boardId, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> listener.onGetAllWriterToolsByBoardIdSuccess(list),
                      throwable -> listener.onFailure(throwable)));
    }

    public void searchWriterTools(int page, int size, String query, String... sort){
        compositeDisposable.add(App.getApi().searchWriterTools(getIdToken(), page, size, query, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> listener.onSearchWriterToolsSuccess(list),
                      throwable -> listener.onFailure(throwable)));
    }

    public void getAllWriterTools(int page, int size, String... sort){
        compositeDisposable.add(App.getApi().getAllWriterTools(getIdToken(), page, size, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> listener.onGetAllWriterToolsSuccess(list),
                      throwable -> listener.onFailure(throwable)));
    }

    public void createWriterTools(final WriterToolsDTO writerToolsDTO){
        compositeDisposable.add(App.getApi().createWriterTools(getIdToken(), writerToolsDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b -> listener.onCreateWriterToolsSuccess(b),
                        throwable -> listener.onFailure(throwable)));
    }

    public void updateWriterTools(final WriterToolsDTO writerToolsDTO){
        compositeDisposable.add(App.getApi().updateWriterTools(getIdToken(), writerToolsDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b -> listener.onUpdateWriterToolsSuccess(b),
                        throwable -> listener.onFailure(throwable)));
    }

    //TODO fix return
    public void deleteWriterTools(final long id){
        compositeDisposable.add(App.getApi().deleteWriterTools(getIdToken(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(string -> listener.onDeleteWriterToolsSuccess(string),
                        throwable -> listener.onFailure(throwable)));
    }

    public void getWriterTools(final long id){
        compositeDisposable.add(App.getApi().getWriterTools(getIdToken(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> listener.onGetWriterToolsSuccess(r),
                   throwable -> listener.onFailure(throwable)));
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
