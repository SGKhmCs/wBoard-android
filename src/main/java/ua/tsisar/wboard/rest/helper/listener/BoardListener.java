package ua.tsisar.wboard.rest.helper.listener;

import java.util.List;

import ua.tsisar.wboard.dto.BoardDTO;

public interface BoardListener{
    void onSearchBoardsSuccess(List<BoardDTO> list);
    void onGetAllBoardsSuccess(List<BoardDTO> list);
    void onCreateBoardSuccess(BoardDTO boardDTO);
    void onUpdateBoardSuccess(BoardDTO boardDTO);
    void onGetBoardSuccess(BoardDTO boardDTO);
    void onFailure(Throwable throwable);
}
