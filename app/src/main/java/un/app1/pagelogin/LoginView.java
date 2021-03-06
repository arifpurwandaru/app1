package un.app1.pagelogin;

public interface LoginView {

    void onLoginClick();

    void onCloseActivity();

    void setTextAlert(String alert);

    void animFadeInTextAlert();

    void animFadeOutTextAlert();

    void animFadeInTextLogin();

    void animFadeOutTextLogin();

    void animFadeInArcLoader();

    void animFadeOutArcLoader();

    void setEnableButtonLogin();

    void setDisableButtonLogin();

    void hideSoftKeyboard();

    boolean isValidEmail(CharSequence target);

    void onResponseOk();

}
