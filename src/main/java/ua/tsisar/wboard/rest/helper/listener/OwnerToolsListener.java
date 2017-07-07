package ua.tsisar.wboard.rest.helper.listener;

import java.util.List;

import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.dto.OwnerToolsDTO;

/**
 * Created by pavel on 07.07.17.
 */

public interface OwnerToolsListener {
    void onSearchOwnerToolsSuccess(List<OwnerToolsDTO> list);
    void onGetAllOwnerToolsSuccess(List<OwnerToolsDTO> list);
    void onDeleteOwnerToolsSuccess(String string);
    void onGetOwnerToolsSuccess(OwnerToolsDTO ownerToolsDTO);
    void onFailure(Throwable throwable);
}
