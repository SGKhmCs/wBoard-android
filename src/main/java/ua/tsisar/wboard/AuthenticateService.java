package ua.tsisar.wboard;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticateService {

    private static final String TOKEN = "token";
    private AuthenticateListener listener;
    private boolean rememberMe;

    private Activity getActivity() {
        return (Activity) listener;
    }

    public AuthenticateService(AuthenticateListener listener){
        this.listener = listener;
        App.setIdToken(loadIdToken());
    }

    public interface AuthenticateListener {
        void authenticated();
    }

    public void authorize(AuthorizeDTO authorizeDTO){
        Call<Token> tokenCall = App.getApi().authorize(authorizeDTO);
        rememberMe = authorizeDTO.getRememberMe();

        tokenCall.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                switch (response.code()){
                    case 200:
                        String idToken = response.body().getIdToken();
                        App.setIdToken(idToken);
                        if (rememberMe){
                            saveIdToken(idToken);
                        } else {
                            saveIdToken("");
                        }
                        isAuthenticated();
                        break;
                    default:
                        Message.makeText(getActivity(), "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable throwable) {
                Message.makeText(getActivity(), "Error", throwable.getMessage()).show();
            }
        });
    }

    public void isAuthenticated(){
        Call<String> stringCall = App.getApi().isAuthenticated(App.getToken().getIdTokenEx());
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()){
                    case 200:
                        if(!response.body().isEmpty()) {
                            listener.authenticated();
                        }
                        break;
                    default:
                        Message.makeText(getActivity(), "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Message.makeText(getActivity(), "Error", throwable.getMessage()).show();
            }
        });
    }

    public void singOut(){
        App.getToken().resetToken();
        saveIdToken("");
    }

    private void saveIdToken(String idToken) {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, idToken);
        editor.apply();
    }

    private String loadIdToken() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN, "");
    }

}
