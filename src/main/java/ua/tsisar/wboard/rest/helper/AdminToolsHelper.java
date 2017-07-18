package ua.tsisar.wboard.rest.helper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.dto.AdminToolsDTO;
import ua.tsisar.wboard.rest.helper.listener.AdminToolsListener;

//TODO We can use generic?
public class AdminToolsHelper {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AdminToolsListener listener;

    private String getIdToken(){
        return App.getTokenDTO().getIdTokenEx();
    }

    public AdminToolsHelper(AdminToolsListener listener){
        this.listener = listener;
    }

    public void getAllAdminToolsByBoardId(int page, int size, Long boardId, String... sort){
        compositeDisposable.add(App.getApi().getAllAdminToolsByBoardId(getIdToken(), page, size, boardId, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> listener.onGetAllAdminToolsByBoardIdSuccess(list),
                      throwable -> listener.onFailure(throwable)));
    }

    public void searchAdminTools(int page, int size, String query, String... sort){
        compositeDisposable.add(App.getApi().searchAdminTools(getIdToken(), page, size, query, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> listener.onSearchAdminToolsSuccess(list),
                      throwable -> listener.onFailure(throwable)));
    }

    public void getAllAdminTools(int page, int size, String... sort){
        compositeDisposable.add(App.getApi().getAllAdminTools(getIdToken(), page, size, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> listener.onGetAllAdminToolsSuccess(list),
                      throwable -> listener.onFailure(throwable)));
    }

    public void createAdminTools(final AdminToolsDTO adminToolsDTO){
        compositeDisposable.add(App.getApi().createAdminTools(getIdToken(), adminToolsDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b -> listener.onCreateAdminToolsSuccess(b),
                   throwable -> listener.onFailure(throwable)));
    }

    public void updateAdminTools(final AdminToolsDTO adminToolsDTO){
        compositeDisposable.add(App.getApi().updateAdminTools(getIdToken(), adminToolsDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b -> listener.onUpdateAdminToolsSuccess(b),
                   throwable -> listener.onFailure(throwable)));
    }

    //TODO fix return
    public void deleteAdminTools(final long id){
        compositeDisposable.add(App.getApi().deleteAdminTools(getIdToken(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(string -> listener.onDeleteAdminToolsSuccess(string),
                        throwable -> listener.onFailure(throwable)));
    }

    public void getAdminTools(final long id){
        compositeDisposable.add(App.getApi().getAdminTools(getIdToken(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> listener.onGetAdminToolsSuccess(r),
                   throwable -> listener.onFailure(throwable)));
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
