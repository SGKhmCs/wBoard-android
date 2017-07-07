package ua.tsisar.wboard.rest.helper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.rest.helper.listener.OwnerToolsListener;

public class OwnerToolsHelper {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private OwnerToolsListener listener;

    private String getIdToken(){
        return App.getTokenDTO().getIdTokenEx();
    }

    OwnerToolsHelper(OwnerToolsListener listener){
        this.listener = listener;
    }

    public void searchOwnerTools(int page, int size, String query, String... sort){
        compositeDisposable.add(App.getApi().searchOwnerTools(getIdToken(), page, size, query, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> listener.onSearchOwnerToolsSuccess(list),
                      throwable -> listener.onFailure(throwable)));
    }

    public void getAllOwnerTools(int page, int size, String... sort){
        compositeDisposable.add(App.getApi().getAllOwnerTools(getIdToken(), page, size, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> listener.onGetAllOwnerToolsSuccess(list),
                      throwable -> listener.onFailure(throwable)));
    }

    public void deleteOwnerTools(final long id){
        compositeDisposable.add(App.getApi().deleteOwnerTools(getIdToken(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(string -> listener.onDeleteOwnerToolsSuccess(string),
                        throwable -> listener.onFailure(throwable)));
    }

    public void getOwnerTools(final long id){
        compositeDisposable.add(App.getApi().getOwnerTools(getIdToken(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b -> listener.onGetOwnerToolsSuccess(b),
                   throwable -> listener.onFailure(throwable)));
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
