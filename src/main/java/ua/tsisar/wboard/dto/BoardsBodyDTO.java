package ua.tsisar.wboard.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoardsBodyDTO {

    @SerializedName("backgroundColor")
    @Expose
    private Integer backgroundColor;
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * No args constructor for use in serialization
     *
     */
    public BoardsBodyDTO() {
    }

    /**
     *
     * @param id
     * @param backgroundColor
     */
    public BoardsBodyDTO(Integer backgroundColor, Integer id) {
        super();
        this.backgroundColor = backgroundColor;
        this.id = id;
    }

    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

}