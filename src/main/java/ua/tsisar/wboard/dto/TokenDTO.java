package ua.tsisar.wboard.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TokenDTO {

    @SerializedName("id_token")
    @Expose
    private String idToken;

    /**
     * No args constructor for use in serialization
     *
     */
    public TokenDTO() {
    }

    /**
     *
     * @param idToken
     */
    public TokenDTO(String idToken) {
        super();
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getIdTokenEx(){
        return "Bearer " + idToken;
    }

    public void resetToken(){idToken = null;}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}