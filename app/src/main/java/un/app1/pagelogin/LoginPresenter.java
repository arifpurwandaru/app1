package un.app1.pagelogin;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import un.app1.network.service.MainService;
import un.app1.network.service.MyCallBack;
import un.app1.pagelogin.model.DataLogin;
import un.app1.pagelogin.model.SubmitLogin;

public class LoginPresenter {

    private LoginView loginView;
    private MainService service;
    private CompositeSubscription subscriptions;

    LoginPresenter(LoginView loginView, MainService service) {
        this.loginView = loginView;
        this.service = service;
        subscriptions = new CompositeSubscription();
    }

    public void onLoginClick() {
        loginView.onLoginClick();
    }

    public void onCloseActivity() {
        loginView.onCloseActivity();
    }

    void checkFirst(String deviceid, String userName, String password) {
        loginView.setDisableButtonLogin();
        loginView.animFadeOutTextAlert();
        if (userName.isEmpty() && password.isEmpty()) {
            loginView.setTextAlert("Email or Password is Empty");
            loginView.animFadeInTextAlert();
            loginView.setEnableButtonLogin();
        } else if (userName.isEmpty()) {
            loginView.setTextAlert("Email Cannot be Empty");
            loginView.animFadeInTextAlert();
            loginView.setEnableButtonLogin();
        } else if (password.isEmpty()) {
            loginView.setTextAlert("Password Cannot be Empty");
            loginView.animFadeInTextAlert();
            loginView.setEnableButtonLogin();
        } else {
            if (loginView.isValidEmail(userName)) {
                getLogin(new SubmitLogin(deviceid, userName, password));
                loginView.animFadeOutTextLogin();
                loginView.animFadeInArcLoader();
                loginView.hideSoftKeyboard();
            } else {
                loginView.setTextAlert("Ooops! Your email is invalid");
                loginView.animFadeInTextAlert();
                loginView.setEnableButtonLogin();
            }
        }
    }

    void getLogin(SubmitLogin submitLogin) {
        Subscription subscription = service.requestLogin(submitLogin, new MyCallBack.CallLogin() {

            @Override
            public void onError(String error) {
                loginView.animFadeOutArcLoader();
                loginView.animFadeInTextLogin();
                loginView.setTextAlert(error);
                loginView.animFadeInTextLogin();
                loginView.setEnableButtonLogin();
            }

            @Override
            public void onSuccess(DataLogin dataLogin) {
                loginView.animFadeOutArcLoader();
                loginView.animFadeInTextLogin();
                loginView.animFadeInTextAlert();
                loginView.onCloseActivity();

            }
        });

        subscriptions.add(subscription);
    }

    void unSubscribe() {
        subscriptions.unsubscribe();
    }

}
