package un.app1.apphome.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Banner {

    @SerializedName("message")
    public String message;

    @SerializedName("statusCode")
    public String statusCode;

    @SerializedName("banner")
    public List<Banner> banner = null;

}