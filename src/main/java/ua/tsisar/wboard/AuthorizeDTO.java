package ua.tsisar.wboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorizeDTO {

    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("rememberMe")
    @Expose
    private Boolean rememberMe;
    @SerializedName("username")
    @Expose
    private String username;

    public AuthorizeDTO(String username, String password, Boolean rememberMe){
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public AuthorizeDTO(){
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}