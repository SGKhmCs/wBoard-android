package ua.tsisar.wboard.Service.Listener;

import java.util.List;

import retrofit2.Response;
import ua.tsisar.wboard.DTO.BoardDTO;

public interface BoardListener{
    void onCreateBoardResponse(Response<BoardDTO> response);
    void onGetAllBoardsResponse(Response<List<BoardDTO>> response);
    void onSearchBoardsResponse(Response<List<BoardDTO>> response);
    void onFailure(Throwable throwable);
}
