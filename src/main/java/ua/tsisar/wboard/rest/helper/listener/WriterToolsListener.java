package ua.tsisar.wboard.rest.helper.listener;

import java.util.List;

import ua.tsisar.wboard.dto.WriterToolsDTO;

public interface WriterToolsListener {
    void onGetAllWriterToolsByBoardIdSuccess(List<WriterToolsDTO> list);
    void onSearchWriterToolsSuccess(List<WriterToolsDTO> list);
    void onGetAllWriterToolsSuccess(List<WriterToolsDTO> list);
    void onCreateWriterToolsSuccess(WriterToolsDTO writerToolsDTO);
    void onUpdateWriterToolsSuccess(WriterToolsDTO writerToolsDTO);
    void onDeleteWriterToolsSuccess(String string);
    void onGetWriterToolsSuccess(WriterToolsDTO writerToolsDTO);
    void onFailure(Throwable throwable);
}
