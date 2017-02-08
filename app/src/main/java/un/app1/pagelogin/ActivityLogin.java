package un.app1.pagelogin;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import un.app1.MainApp;
import un.app1.R;
import un.app1.databinding.ActivityLoginBinding;
import un.app1.network.internet.ConnectivityReceiver;
import un.app1.network.service.MainService;
import un.app1.network.service.RetBuilder;
import un.app1.pagelogin.model.SubmitLogin;

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

    private String deviceId(){
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

    @Override
    public void setError(String message) {

    }

    @Override
    public void onLoginClick() {
        presenter.checkFirst(deviceId(),
                binding.inputEmail.getText().toString(),
                binding.inputPassword.getText().toString());
    }

    @Override
    public void onCloseActivity() {
        this.finish();
    }

}
