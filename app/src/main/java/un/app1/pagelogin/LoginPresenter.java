package un.app1.pagelogin;

import android.content.Context;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import un.app1.R;
import un.app1.network.service.MainService;
import un.app1.network.service.MyCallBack;
import un.app1.pagelogin.model.DataLogin;
import un.app1.pagelogin.model.SubmitLogin;

public class LoginPresenter {

    private LoginView loginView;
    private MainService service;
    private CompositeSubscription subscriptions;
    private Context context;

    LoginPresenter(Context context, LoginView loginView, MainService service) {
        this.loginView = loginView;
        this.service = service;
        this.context = context;
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
        loginView.animFadeInTextAlert();
        if (userName.isEmpty() && password.isEmpty()) {
            loginView.setTextAlert(context.getResources().getString(R.string.strEmptyEmailorPassword));
            loginView.animFadeInTextAlert();
            loginView.setEnableButtonLogin();
        } else if (userName.isEmpty()) {
            loginView.setTextAlert(context.getResources().getString(R.string.strEmailEmpty));
            loginView.animFadeInTextAlert();
            loginView.setEnableButtonLogin();
        } else if (password.isEmpty()) {
            loginView.setTextAlert(context.getResources().getString(R.string.strPasswordEmpty));
            loginView.animFadeInTextAlert();
            loginView.setEnableButtonLogin();
        } else {
            if (loginView.isValidEmail(userName)) {
                getLogin(new SubmitLogin(deviceid, userName, password));
                loginView.animFadeOutTextLogin();
                loginView.animFadeInArcLoader();
                loginView.hideSoftKeyboard();
            } else {
                loginView.setTextAlert(context.getResources().getString(R.string.strInvalidPassword));
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
                loginView.onResponseOk();

            }
        });

        subscriptions.add(subscription);
    }

    void unSubscribe() {
        subscriptions.unsubscribe();
    }

}
