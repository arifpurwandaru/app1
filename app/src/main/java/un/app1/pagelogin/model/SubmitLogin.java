package un.app1.pagelogin.model;

public class SubmitLogin {

    private String deviceId;
    private String userName;
    private String password;

    public SubmitLogin(String deviceId, String userName, String password) {
        this.deviceId = deviceId;
        this.userName = userName;
        this.password = password;
    }

}
