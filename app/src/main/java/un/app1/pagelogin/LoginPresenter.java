package un.app1.pagelogin;

import android.text.TextUtils;
import android.util.Log;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import un.app1.apphome.model.Banner;
import un.app1.apphome.model.SubmitBanner;
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

    void checkFirst(String deviceid, String userName, String password){
        loginView.animFadeOutTextAlert();
        if(userName.isEmpty() && password.isEmpty()){
            loginView.setTextAlert("Email or Password is Empty");
            loginView.animFadeInTextAlert();
        } else if(userName.isEmpty()){
            loginView.setTextAlert("Email Cannot be Empty");
            loginView.animFadeInTextAlert();
        } else if(password.isEmpty()){
            loginView.setTextAlert("Password Cannot be Empty");
            loginView.animFadeInTextAlert();
        } else {
            if(isValidEmail(userName)){
                getLogin(new SubmitLogin(deviceid, userName, password));
                loginView.animFadeOutTextLogin();
                loginView.animFadeInArcLoader();
            } else {
                loginView.setTextAlert("Ooops! Your email is invalid");
                loginView.animFadeInTextAlert();
            }
        }
    }


    public final boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    void getLogin(SubmitLogin submitLogin) {
        Subscription subscription = service.requestLogin(submitLogin, new MyCallBack.CallLogin() {

            @Override
            public void onError(String error) {
                loginView.animFadeOutArcLoader();
                loginView.animFadeInTextLogin();
                loginView.setTextAlert(error);
                loginView.animFadeInTextLogin();
            }

            @Override
            public void onSuccess(DataLogin dataLogin) {
                loginView.animFadeOutArcLoader();
                loginView.animFadeInTextLogin();
                loginView.animFadeInTextAlert();
                loginView.setTextAlert("> "+ dataLogin.message + " " + dataLogin.statusLogin + " " + dataLogin.token);
            }
        });

        subscriptions.add(subscription);
    }
}
