package un.app1.apphome;

public interface HomeView {

    void setUserName(String username);

    void setLogin(String login);

    void showClickRetry();

    void hideClickRetry();

    void setShowUserImage();

    void setUserImage(String imageUrl);

    void goToActivity();

}
