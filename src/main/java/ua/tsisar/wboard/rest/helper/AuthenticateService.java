package ua.tsisar.wboard.rest.helper;

import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ua.tsisar.wboard.App;
import ua.tsisar.wboard.dto.AuthorizeDTO;
import ua.tsisar.wboard.rest.helper.listener.AuthenticateListener;

public class AuthenticateService {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AuthenticateListener listener;

    public AuthenticateService(AuthenticateListener listener){
        this.listener = listener;
    }

//    public void authorize(AuthorizeDTO authorizeDTO) {
//        compositeDisposable.add(App.getApi().authorize(authorizeDTO)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<TokenDTO>(){
//                    @Override
//                    public void onSuccess(TokenDTO tokenDTO) {
//                        listener.onAuthorizeSuccess(tokenDTO);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        throwable.printStackTrace();
//                        listener.onFailure(throwable);
//                    }
//                }));
//
//    }

    public void authorize(AuthorizeDTO authorizeDTO) {
        compositeDisposable.add(App.getApi().authorize(authorizeDTO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(tokenDTO -> listener.onAuthorizeSuccess(tokenDTO),
                      throwable -> listener.onFailure(throwable)));

    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            Log.d("myLogs", "compositeDisposable.size(): " + compositeDisposable.size());
            compositeDisposable.dispose();
        }
    }
}
