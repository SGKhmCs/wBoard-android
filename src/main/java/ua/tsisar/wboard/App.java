package ua.tsisar.wboard;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ua.tsisar.wboard.dto.TokenDTO;
import ua.tsisar.wboard.rest.ApiInterface;


public class App extends Application {

    private static ApiInterface apiInterface;
    private static TokenDTO tokenDTO;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.URL_BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class); //Создаем объект, при помощи которого будем выполнять запросы

        tokenDTO = new TokenDTO();
    }

    public static ApiInterface getApi() {
        return apiInterface;
    }

    public static void setIdToken(String idToken){
        tokenDTO.setIdToken(idToken);
    }

    public static TokenDTO getTokenDTO(){
        return tokenDTO;
    }
}