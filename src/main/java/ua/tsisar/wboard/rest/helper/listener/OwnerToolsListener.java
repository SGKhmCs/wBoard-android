package ua.tsisar.wboard.rest.helper.listener;

import java.util.List;

import ua.tsisar.wboard.dto.OwnerToolsDTO;

public interface OwnerToolsListener {
    void onGetAllOwnerToolsByBoardIdSuccess(List<OwnerToolsDTO> list);
    void onSearchOwnerToolsSuccess(List<OwnerToolsDTO> list);
    void onGetAllOwnerToolsSuccess(List<OwnerToolsDTO> list);
    void onDeleteOwnerToolsSuccess(String string);
    void onGetOwnerToolsSuccess(OwnerToolsDTO ownerToolsDTO);
    void onFailure(Throwable throwable);
}
