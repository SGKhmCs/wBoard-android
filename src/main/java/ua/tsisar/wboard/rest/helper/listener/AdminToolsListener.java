package ua.tsisar.wboard.rest.helper.listener;

import java.util.List;

import ua.tsisar.wboard.dto.AdminToolsDTO;

public interface AdminToolsListener {
    void onGetAllAdminToolsByBoardIdSuccess(List<AdminToolsDTO> list);
    void onSearchAdminToolsSuccess(List<AdminToolsDTO> list);
    void onGetAllAdminToolsSuccess(List<AdminToolsDTO> list);
    void onCreateAdminToolsSuccess(AdminToolsDTO adminToolsDTO);
    void onUpdateAdminToolsSuccess(AdminToolsDTO adminToolsDTO);
    void onDeleteAdminToolsSuccess(String string);
    void onGetAdminToolsSuccess(AdminToolsDTO adminToolsDTO);
    void onFailure(Throwable throwable);
}
