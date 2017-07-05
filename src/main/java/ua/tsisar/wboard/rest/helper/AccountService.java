package ua.tsisar.wboard.rest.helper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.dto.UserDTO;
import ua.tsisar.wboard.rest.helper.listener.AccountListener;

public class AccountService {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AccountListener listener;

    public AccountService(AccountListener listener){
        this.listener = listener;
    }

    private String getIdToken(){
        return App.getTokenDTO().getIdTokenEx();
    }

    public void getAccount(){
        compositeDisposable.add(App.getApi().getAccount(getIdToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(userDTO -> listener.onGetAccountSuccess(userDTO),
                     throwable -> listener.onFailure(throwable)));
    }

    public void saveAccount(UserDTO userDTO){
        compositeDisposable.add(App.getApi().saveAccount(getIdToken(), userDTO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(string -> listener.onSaveAccountSuccess(string),
                    throwable -> listener.onFailure(throwable)));
    }

    public void changePassword(String password){
        compositeDisposable.add(App.getApi().changePassword(getIdToken(), password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(string -> listener.onChangePasswordSuccess(string),
                    throwable -> listener.onFailure(throwable)));
    }

    public void isAuthenticated(){
        compositeDisposable.add(App.getApi().isAuthenticated(getIdToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(string -> listener.onIsAuthenticatedSuccess(string),
                    throwable -> listener.onFailure(throwable)));
    }

    public void registerAccount(UserDTO userDTO){
        compositeDisposable.add(App.getApi().registerAccount(userDTO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(string -> listener.onRegisterAccountSuccess(string),
                    throwable -> listener.onFailure(throwable)));
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
