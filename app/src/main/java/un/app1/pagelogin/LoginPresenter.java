package un.app1.pagelogin;

import rx.subscriptions.CompositeSubscription;
import un.app1.network.service.MainService;

public class LoginPresenter {

    private LoginView loginView;
    private MainService service;
    private CompositeSubscription subscriptions;

    LoginPresenter(LoginView loginView, MainService service) {
        this.loginView = loginView;
        this.service = service;
        subscriptions = new CompositeSubscription();
    }
}
