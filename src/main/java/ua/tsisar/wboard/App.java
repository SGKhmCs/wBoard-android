package ua.tsisar.wboard;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class App extends Application {

    private static ApiInterface apiInterface;
    private static Token token;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class); //Создаем объект, при помощи которого будем выполнять запросы

        token = new Token();
    }

    public static ApiInterface getApi() {
        return apiInterface;
    }

    public static void setIdToken(String idToken){
        token.setIdToken(idToken);
    }

    public static Token getToken(){
        return token;
    }
}