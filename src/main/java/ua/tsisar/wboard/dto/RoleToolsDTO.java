package ua.tsisar.wboard.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pavel on 07.07.17.
 */

public class RoleToolsDTO {
    @SerializedName("boardId")
    @Expose
    private Integer boardId;
    @SerializedName("boardName")
    @Expose
    private String boardName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("userLogin")
    @Expose
    private String userLogin;

    /**
     * No args constructor for use in serialization
     *
     */
    public RoleToolsDTO() {
    }

    /**
     *
     * @param id
     * @param boardId
     * @param userId
     * @param userLogin
     * @param boardName
     */
    public RoleToolsDTO(Integer boardId, String boardName, Integer id, Integer userId, String userLogin) {
        super();
        this.boardId = boardId;
        this.boardName = boardName;
        this.id = id;
        this.userId = userId;
        this.userLogin = userLogin;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(this);
    }
}
