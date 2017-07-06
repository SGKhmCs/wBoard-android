package ua.tsisar.wboard.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoardDTO {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pub")
    @Expose
    private Boolean pub;

    /**
     * No args constructor for use in serialization
     *
     */
    public BoardDTO() {
    }

    /**
     *
     * @param id
     * @param name
     * @param pub
     */
    public BoardDTO(Integer id, String name, Boolean pub) {
        super();
        this.id = id;
        this.name = name;
        this.pub = pub;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPub() {
        return pub;
    }

    public void setPub(Boolean pub) {
        this.pub = pub;
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"id\": " + id +",\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"pub\": " + pub + "\n" +
                "}";
    }

}