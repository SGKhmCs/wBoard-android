package ua.tsisar.wboard;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by pavel on 16.06.17.
 */

public interface ApiInterface {
    String URL_BASE = "http://192.168.20.102:8080/api/";

    @Headers("Content-Type: application/json")
    @POST("authenticate")
    Call<TokenDTO> authorize(@Body AuthenticateDTO authenticateDTO);
}
