package ua.tsisar.wboard;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ua.tsisar.wboard.dto.AuthorizeDTO;
import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.dto.TokenDTO;
import ua.tsisar.wboard.dto.UserDTO;


public interface ApiInterface {
    String URL_BASE = "http://192.168.43.123:8080/api/";

    //User JWT Controller
    @Headers("Content-Type: application/json")
    @POST("authenticate")
    Single<TokenDTO>authorize(@Body AuthorizeDTO authorizeDTO);

    //Account Resource
    @Headers("Content-Type: application/json")
    @GET("account")
    Single<UserDTO>getAccount(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @POST("account")
    Single<String>saveAccount(@Header("Authorization") String authorization,
                               @Body UserDTO userDTO);

    @Headers("Content-Type: application/json")
    @POST("account/change_password")
    Single<String>changePassword(@Header("Authorization") String authorization,
                                  @Body String string);

    @Headers("Content-Type: application/json")
    @GET("authenticate")
    Single<String>isAuthenticated(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @POST("register")
    Single<String>registerAccount(@Body UserDTO userDTO);

    //Board Resource
    @Headers("Content-Type: application/json")
    @GET("boards")
    Single<List<BoardDTO>>getAllBoards(@Header("Authorization") String authorization,
                                     @Query("page") Integer page,
                                     @Query("size") Integer size,
                                     @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @GET("_search/boards")
    Single<List<BoardDTO>>searchBoards(@Header("Authorization") String authorization,
                                     @Query("page") Integer page,
                                     @Query("size") Integer size,
                                     @Query("query") String query,
                                     @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @POST("boards")
    Single<BoardDTO>createBoard(@Header("Authorization") String authorization,
                               @Body BoardDTO boardDTO);

    @Headers("Content-Type: application/json")
    @PUT("boards")
    Single<BoardDTO>updateBoard(@Header("Authorization") String authorization,
                               @Body BoardDTO boardDTO);

    @Headers("Content-Type: application/json")
    @GET("boards/{id}")
    Single<BoardDTO>getBoard(@Header("Authorization") String authorization,
                           @Path("id") long id);
}