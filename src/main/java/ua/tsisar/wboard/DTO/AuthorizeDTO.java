package ua.tsisar.wboard.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AuthorizeDTO {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("rememberMe")
    @Expose
    private Boolean rememberMe;

    /**
     * No args constructor for use in serialization
     *
     */
    public AuthorizeDTO() {
    }

    /**
     *
     * @param username
     * @param password
     * @param rememberMe
     *
     */
    public AuthorizeDTO(String username, String password, Boolean rememberMe) {
        super();
        this.password = password;
        this.rememberMe = rememberMe;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}