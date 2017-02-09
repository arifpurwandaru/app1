package un.app1.apphome;

import java.util.ArrayList;
import java.util.List;

import un.app1.apphome.model.ArrayBanner;
import un.app1.apphome.model.Banner;

public interface HomeView {

    void setUserName(String username);

    void setLogin(String login);

    void showClickRetry();

    void hideClickRetry();

    void setShowUserImage();

    void setUserImage(String imageUrl);

    void goToActivity();

    void setLayoutChecking();

    void setLayoutSignOut();

    void setLayoutSignIn();

    void bannserSize(List<ArrayBanner> arrayBanners);

    void animFadeInSignIn();

    void animFadeInBanner();

    void onClickRetry();

    void onClickLogin();

    void onClickRegister();
}
