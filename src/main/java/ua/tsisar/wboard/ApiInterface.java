package ua.tsisar.wboard;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by pavel on 16.06.17.
 */

public interface ApiInterface {
    String URL_BASE = "http://192.168.38.105:8080/api/";

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("authenticate")
    Call<Token> authorize(@Body Authorize authorize);

    @Headers("Accept: text/plain")
    @GET("authenticate")
    Call<String> isAuthenticated(@Header("Authorization") String authorization);
}
