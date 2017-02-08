package un.app1.pagelogin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import un.app1.MainApp;
import un.app1.R;
import un.app1.databinding.ActivityLoginBinding;
import un.app1.network.internet.ConnectivityReceiver;

public class ActivityLogin extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MainApp) getApplication()).providesAppComponents().inject(ActivityLogin.this);
        MainApp.getInstance().setConnectivityListener(ActivityLogin.this);

        binding = DataBindingUtil.setContentView(ActivityLogin.this, R.layout.activity_login);
//        mainService = new MainService(retBuilder.service());
//        homePresenter = new HomePresenter(ActivityHome.this, mainService);
//        binding.setPresenter(homePresenter);

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
}
