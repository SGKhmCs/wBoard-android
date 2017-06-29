package ua.tsisar.wboard;

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

    public BoardDTO(){}

    public BoardDTO(String name){
        this.name = name;
        this.pub = false;
    }

    public BoardDTO(String name, boolean bup){
        this.name = name;
        this.pub = bup;
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

}