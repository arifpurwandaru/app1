package un.app1.pagelogin;

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
