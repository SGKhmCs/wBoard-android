package ua.tsisar.wboard.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    @SerializedName("createdDate")
    @Expose
    private Object createdDate;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;

    /**
     * No args constructor for use in serialization
     *
     */
    public BoardDTO() {
    }

    /**
     *
     * @param id
     * @param createdBy
     * @param name
     * @param createdDate
     * @param pub
     */
    public BoardDTO(Integer id, String name, Boolean pub, Object createdDate, String createdBy) {
        super();
        this.id = id;
        this.name = name;
        this.pub = pub;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }

    public BoardDTO(String name, Boolean pub) {
        super();
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

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    @Override
    public String toString(){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(this);
    }
}