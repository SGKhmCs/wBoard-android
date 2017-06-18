package ua.tsisar.wboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("id_token")
    @Expose
    private String idToken;

    public String getIdToken() {
        return idToken;
    }

    public String getIdTokenEx(){
        return "Bearer " + idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

}