package ua.tsisar.wboard.service;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.dto.UserDTO;
import ua.tsisar.wboard.service.listener.AccountListener;

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
                .subscribeWith(new DisposableSingleObserver<UserDTO>(){
                    @Override
                    public void onSuccess(UserDTO userDTO) {
                        listener.onGetAccountSuccess(userDTO);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.onFailure(throwable);
                    }
                }));
    }

    public void saveAccount(UserDTO userDTO){
        compositeDisposable.add(App.getApi().saveAccount(getIdToken(), userDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>(){
                    @Override
                    public void onSuccess(String string) {
                        listener.onSaveAccountSuccess(string);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.onFailure(throwable);
                    }
                }));
    }

    public void changePassword(String password){
        compositeDisposable.add(App.getApi().changePassword(getIdToken(), password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>(){
                    @Override
                    public void onSuccess(String string) {
                        listener.onChangePasswordSuccess(string);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.onFailure(throwable);
                    }
                }));
    }

    public void isAuthenticated(){
        compositeDisposable.add(App.getApi().isAuthenticated(getIdToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>(){
                    @Override
                    public void onSuccess(String string) {
                        listener.onIsAuthenticatedSuccess(string);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.onFailure(throwable);
                    }
                }));
    }

    public void registerAccount(UserDTO userDTO){
        compositeDisposable.add(App.getApi().registerAccount(userDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>(){
                    @Override
                    public void onSuccess(String string) {
                        listener.onRegisterAccountSuccess(string);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.onFailure(throwable);
                    }
                }));

    }
}
