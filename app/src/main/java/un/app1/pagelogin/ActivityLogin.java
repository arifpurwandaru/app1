package un.app1.pagelogin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import un.app1.MainApp;
import un.app1.R;
import un.app1.apphome.ActivityHome;
import un.app1.apphome.HomePresenter;
import un.app1.network.service.MainService;

public class ActivityLogin extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        ((MainApp) getApplication()).providesAppComponents().inject(ActivityLogin.this);
//        MainApp.getInstance().setConnectivityListener(ActivityLogin.this);
//
//        binding = DataBindingUtil.setContentView(ActivityLogin.this, R.layout.activity_login);
//        mainService = new MainService(retBuilder.service());
//        homePresenter = new HomePresenter(ActivityHome.this, mainService);
//        binding.setPresenter(homePresenter);

    }

}
