package ua.tsisar.wboard.rest.helper.listener;

import java.util.List;

import ua.tsisar.wboard.dto.ReaderToolsDTO;

public interface ReaderToolsListener {
    void onGetAllReaderToolsByBoardIdSuccess(List<ReaderToolsDTO> list);
    void onSearchReaderToolsSuccess(List<ReaderToolsDTO> list);
    void onGetAllReaderToolsSuccess(List<ReaderToolsDTO> list);
    void onCreateReaderToolsSuccess(ReaderToolsDTO readerToolsDTO);
    void onUpdateReaderToolsSuccess(ReaderToolsDTO readerToolsDTO);
    void onDeleteReaderToolsSuccess(String string);
    void onGetReaderToolsSuccess(ReaderToolsDTO readerToolsDTO);
    void onFailure(Throwable throwable);
}
