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
        if(userName.isEmpty() && password.isEmpty()){
            loginView.setError("Email or Password is Empty");
        } else if(userName.isEmpty()){
            loginView.setError("Email Cannot be Empty");
        } else if(password.isEmpty()){
            loginView.setError("Password Cannot be Empty");
        } else {
            if(isValidEmail(userName)){
                getLogin(new SubmitLogin(deviceid, userName, password));
            } else {
                loginView.setError("Ooops! Your email is invalid");
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
                Log.e("x", "> "+ error);
            }

            @Override
            public void onSuccess(DataLogin dataLogin) {
                Log.e("x", "> "+ dataLogin.message + " " + dataLogin.statusLogin + " " + dataLogin.token);
            }
        });

        subscriptions.add(subscription);
    }
}
