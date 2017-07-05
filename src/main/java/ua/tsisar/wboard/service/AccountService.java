package ua.tsisar.wboard.service;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.dto.UserDTO;
import ua.tsisar.wboard.service.listener.AccountListener;

public class AccountService {

    private AccountListener listener;

    public AccountService(AccountListener listener){
        this.listener = listener;
    }

    private String getIdToken(){
        return App.getTokenDTO().getIdTokenEx();
    }

    public void getAccount(){
        App.getApi().getAccount(getIdToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(userDTO -> listener.onGetAccountSuccess(userDTO),
                     throwable -> listener.onFailure(throwable));
    }

    public void saveAccount(UserDTO userDTO){
        App.getApi().saveAccount(getIdToken(), userDTO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(string -> listener.onSaveAccountSuccess(string),
                    throwable -> listener.onFailure(throwable));
    }

    public void changePassword(String password){
        App.getApi().changePassword(getIdToken(), password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(string -> listener.onChangePasswordSuccess(string),
                    throwable -> listener.onFailure(throwable));
    }

    public void isAuthenticated(){
        App.getApi().isAuthenticated(getIdToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(string -> listener.onIsAuthenticatedSuccess(string),
                    throwable -> listener.onFailure(throwable));
    }

    public void registerAccount(UserDTO userDTO){
        App.getApi().registerAccount(userDTO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(string -> listener.onRegisterAccountSuccess(string),
                    throwable -> listener.onFailure(throwable));
    }
}
