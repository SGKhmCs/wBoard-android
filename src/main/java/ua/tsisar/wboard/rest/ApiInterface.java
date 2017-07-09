package ua.tsisar.wboard.rest;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ua.tsisar.wboard.dto.AdminToolsDTO;
import ua.tsisar.wboard.dto.AuthorizeDTO;
import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.dto.OwnerToolsDTO;
import ua.tsisar.wboard.dto.ReaderToolsDTO;
import ua.tsisar.wboard.dto.TokenDTO;
import ua.tsisar.wboard.dto.UserDTO;
import ua.tsisar.wboard.dto.WriterToolsDTO;


public interface ApiInterface {
    String URL_BASE = "http://192.168.20.105:8080/api/";

    //User JWT Controller
    @Headers("Content-Type: application/json")
    @POST("authenticate")
    Single<TokenDTO> authorize(@Body AuthorizeDTO authorizeDTO);

    //Account Resource
    @Headers("Content-Type: application/json")
    @GET("account")
    Single<UserDTO> getAccount(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @POST("account")
    Single<String> saveAccount(@Header("Authorization") String authorization,
                               @Body UserDTO userDTO);

    @Headers("Content-Type: application/json")
    @POST("account/change_password")
    Single<String> changePassword(@Header("Authorization") String authorization,
                                  @Body String string);

    @Headers("Content-Type: application/json")
    @GET("authenticate")
    Single<String> isAuthenticated(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @POST("register")
    Single<String> registerAccount(@Body UserDTO userDTO);

    //Board Resource
    @Headers("Content-Type: application/json")
    @GET("_search/boards")
    Single<List<BoardDTO>> searchBoards(@Header("Authorization") String authorization,
                                     @Query("page") Integer page,
                                     @Query("size") Integer size,
                                     @Query("query") String query,
                                     @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @GET("boards")
    Single<List<BoardDTO>> getAllBoards(@Header("Authorization") String authorization,
                                       @Query("page") Integer page,
                                       @Query("size") Integer size,
                                       @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @POST("boards")
    Single<BoardDTO> createBoard(@Header("Authorization") String authorization,
                               @Body BoardDTO boardDTO);

    @Headers("Content-Type: application/json")
    @PUT("boards")
    Single<BoardDTO> updateBoard(@Header("Authorization") String authorization,
                               @Body BoardDTO boardDTO);

    @Headers("Content-Type: application/json")
    @GET("boards/{id}")
    Single<BoardDTO> getBoard(@Header("Authorization") String authorization,
                           @Path("id") long id);


    //Owner-tools Resource
    @Headers("Content-Type: application/json")
    @GET("_by-board/owner-tools")
    Single<List<OwnerToolsDTO>> getAllOwnerToolsByBoardId(@Header("Authorization") String authorization,
                                                         @Query("page") Integer page,
                                                         @Query("size") Integer size,
                                                         @Query("boardId") Long boardId,
                                                         @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @GET("_search/owner-tools")
    Single<List<OwnerToolsDTO>> searchOwnerTools(@Header("Authorization") String authorization,
                                                @Query("page") Integer page,
                                                @Query("size") Integer size,
                                                @Query("query") String query,
                                                @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @GET("owner-tools")
    Single<List<OwnerToolsDTO>> getAllOwnerTools(@Header("Authorization") String authorization,
                                                @Query("page") Integer page,
                                                @Query("size") Integer size,
                                                @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @DELETE("owner-tools/{id}")
    Single<String> deleteOwnerTools(@Header("Authorization") String authorization,
                                       @Path("id") long id);


    @Headers("Content-Type: application/json")
    @GET("owner-tools/{id}")
    Single<OwnerToolsDTO> getOwnerTools(@Header("Authorization") String authorization,
                                       @Path("id") long id);


    //Admin-tools Resource
    @Headers("Content-Type: application/json")
    @GET("_by-board/admin-tools")
    Single<List<AdminToolsDTO>> getAllAdminToolsByBoardId(@Header("Authorization") String authorization,
                                                          @Query("page") Integer page,
                                                          @Query("size") Integer size,
                                                          @Query("boardId") Long boardId,
                                                          @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @GET("_search/admin-tools")
    Single<List<AdminToolsDTO>> searchAdminTools(@Header("Authorization") String authorization,
                                                 @Query("page") Integer page,
                                                 @Query("size") Integer size,
                                                 @Query("query") String query,
                                                 @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @GET("admin-tools")
    Single<List<AdminToolsDTO>> getAllAdminTools(@Header("Authorization") String authorization,
                                                 @Query("page") Integer page,
                                                 @Query("size") Integer size,
                                                 @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @POST("admin-tools")
    Single<AdminToolsDTO> createAdminTools(@Header("Authorization") String authorization,
                                 @Body AdminToolsDTO adminToolsDTO);

    @Headers("Content-Type: application/json")
    @PUT("admin-tools")
    Single<AdminToolsDTO> updateAdminTools(@Header("Authorization") String authorization,
                                           @Body AdminToolsDTO adminToolsDTO);

    @Headers("Content-Type: application/json")
    @DELETE("admin-tools/{id}")
    Single<String> deleteAdminTools(@Header("Authorization") String authorization,
                                    @Path("id") long id);


    @Headers("Content-Type: application/json")
    @GET("admin-tools/{id}")
    Single<AdminToolsDTO> getAdminTools(@Header("Authorization") String authorization,
                                        @Path("id") long id);

    //Writer-tools Resource
    @Headers("Content-Type: application/json")
    @GET("_by-board/writer-tools")
    Single<List<WriterToolsDTO>> getAllWriterToolsByBoardId(@Header("Authorization") String authorization,
                                                            @Query("page") Integer page,
                                                            @Query("size") Integer size,
                                                            @Query("boardId") Long boardId,
                                                            @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @GET("_search/writer-tools")
    Single<List<WriterToolsDTO>> searchWriterTools(@Header("Authorization") String authorization,
                                                   @Query("page") Integer page,
                                                   @Query("size") Integer size,
                                                   @Query("query") String query,
                                                   @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @GET("writer-tools")
    Single<List<WriterToolsDTO>> getAllWriterTools(@Header("Authorization") String authorization,
                                                   @Query("page") Integer page,
                                                   @Query("size") Integer size,
                                                   @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @POST("writer-tools")
    Single<WriterToolsDTO> createWriterTools(@Header("Authorization") String authorization,
                                             @Body WriterToolsDTO writerToolsDTO);

    @Headers("Content-Type: application/json")
    @PUT("writer-tools")
    Single<WriterToolsDTO> updateWriterTools(@Header("Authorization") String authorization,
                                             @Body WriterToolsDTO writerToolsDTO);

    @Headers("Content-Type: application/json")
    @DELETE("writer-tools/{id}")
    Single<String> deleteWriterTools(@Header("Authorization") String authorization,
                                     @Path("id") long id);


    @Headers("Content-Type: application/json")
    @GET("writer-tools/{id}")
    Single<WriterToolsDTO> getWriterTools(@Header("Authorization") String authorization,
                                          @Path("id") long id);

    //Reader-tools Resource
    @Headers("Content-Type: application/json")
    @GET("_by-board/reader-tools")
    Single<List<ReaderToolsDTO>> getAllReaderToolsByBoardId(@Header("Authorization") String authorization,
                                                            @Query("page") Integer page,
                                                            @Query("size") Integer size,
                                                            @Query("boardId") Long boardId,
                                                            @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @GET("_search/reader-tools")
    Single<List<ReaderToolsDTO>> searchReaderTools(@Header("Authorization") String authorization,
                                                   @Query("page") Integer page,
                                                   @Query("size") Integer size,
                                                   @Query("query") String query,
                                                   @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @GET("reader-tools")
    Single<List<ReaderToolsDTO>> getAllReaderTools(@Header("Authorization") String authorization,
                                                   @Query("page") Integer page,
                                                   @Query("size") Integer size,
                                                   @Query("sort") String... sort);

    @Headers("Content-Type: application/json")
    @POST("reader-tools")
    Single<ReaderToolsDTO> createReaderTools(@Header("Authorization") String authorization,
                                             @Body ReaderToolsDTO readerToolsDTO);

    @Headers("Content-Type: application/json")
    @PUT("reader-tools")
    Single<ReaderToolsDTO> updateReaderTools(@Header("Authorization") String authorization,
                                             @Body ReaderToolsDTO readerToolsDTO);

    @Headers("Content-Type: application/json")
    @DELETE("reader-tools/{id}")
    Single<String> deleteReaderTools(@Header("Authorization") String authorization,
                                     @Path("id") long id);


    @Headers("Content-Type: application/json")
    @GET("reader-tools/{id}")
    Single<ReaderToolsDTO> getReaderTools(@Header("Authorization") String authorization,
                                          @Path("id") long id);

}