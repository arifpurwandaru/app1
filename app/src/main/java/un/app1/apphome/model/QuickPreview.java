package un.app1.apphome.model;

import com.google.gson.annotations.SerializedName;

public class QuickPreview {

    @SerializedName("message")
    public String message;

    @SerializedName("statusCode")
    public String statusCode;

    @SerializedName("imageUrl")
    public String imageUrl;

    @SerializedName("user")
    public String user;

    @SerializedName("lastLogin")
    public String lastLogin;

}