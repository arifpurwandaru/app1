package un.app1.pagelogin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import un.app1.MainApp;
import un.app1.R;
import un.app1.databinding.ActivityLoginBinding;
import un.app1.network.internet.ConnectivityReceiver;
import un.app1.network.service.MainService;
import un.app1.network.service.RetBuilder;

public class ActivityLogin extends AppCompatActivity implements LoginView, ConnectivityReceiver.ConnectivityReceiverListener {

    @Inject
    public RetBuilder retBuilder;

    ActivityLoginBinding binding;
    LoginPresenter presenter;
    MainService mainService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MainApp) getApplication()).providesAppComponents().inject(ActivityLogin.this);
        MainApp.getInstance().setConnectivityListener(ActivityLogin.this);

        binding = DataBindingUtil.setContentView(ActivityLogin.this, R.layout.activity_login);
        mainService = new MainService(retBuilder.service());
        presenter = new LoginPresenter(ActivityLogin.this, mainService);
        binding.setPresenter(presenter);

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
}
