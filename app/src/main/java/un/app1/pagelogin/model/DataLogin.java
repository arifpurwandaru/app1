package un.app1.pagelogin.model;

import com.google.gson.annotations.SerializedName;

public class DataLogin {

    @SerializedName("message")
    public String message;

    @SerializedName("statusCode")
    public String statusCode;

    @SerializedName("statusLogin")
    public String statusLogin;

    @SerializedName("token")
    public String token;

}
