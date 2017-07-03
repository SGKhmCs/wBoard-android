package ua.tsisar.wboard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ua.tsisar.wboard.DTO.AuthorizeDTO;
import ua.tsisar.wboard.DTO.BoardDTO;
import ua.tsisar.wboard.DTO.TokenDTO;
import ua.tsisar.wboard.DTO.UserDTO;


public interface ApiInterface {
    String URL_BASE = "http://192.168.38.105:8080/api/";

    @Headers("Content-Type: application/json")
    @POST("authenticate")
    Call<TokenDTO> authorize(@Body AuthorizeDTO authorizeDTO);

    @Headers("Content-Type: application/json")
    @GET("authenticate")
    Call<String> isAuthenticated(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @POST("register")
    Call<String> registerAccount(@Body UserDTO userDTO);

    @Headers("Content-Type: application/json")
    @GET("account")
    Call<UserDTO> getAccount(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @POST("account")
    Call<String> saveAccount(@Header("Authorization") String authorization,
                             @Body UserDTO userDTO);

    @Headers("Content-Type: application/json")
    @POST("account/change_password")
    Call<String> changePassword(@Header("Authorization") String authorization,
                                @Body String string);


    @Headers("Content-Type: application/json")
    @GET("boards")
    Call<List<BoardDTO>>getAllBoards(@Header("Authorization") String authorization,
                                     @Query("page") Integer page,
                                     @Query("size") Integer size,
                                     @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @GET("_search/boards")
    Call<List<BoardDTO>>searchBoards(@Header("Authorization") String authorization,
                                     @Query("page") Integer page,
                                     @Query("size") Integer size,
                                     @Query("query") String query,
                                     @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @POST("boards")
    Call<BoardDTO> createBoard(@Header("Authorization") String authorization,
                               @Body BoardDTO boardDTO);

    @Headers("Content-Type: application/json")
    @PUT("boards")
    Call<BoardDTO> updateBoard(@Header("Authorization") String authorization,
                               @Body BoardDTO boardDTO);

    @Headers("Content-Type: application/json")
    @GET("boards/{id}")
    Call<BoardDTO>getBoard(@Header("Authorization") String authorization,
                           @Path("id") long id);

}