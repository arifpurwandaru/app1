package un.app1.pagelogin;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;


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

    private String deviceId(){
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    @Override
    public void setTextAlert(String alert){
        binding.textAlert.setText(alert);
    }

    @Override
    public void animFadeInTextAlert() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.textAlert.setVisibility(View.VISIBLE);
        binding.textAlert.startAnimation(alphaAnimation);
    }

    @Override
    public void animFadeOutTextAlert() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.textAlert.startAnimation(alphaAnimation);
        binding.textAlert.setVisibility(View.GONE);
    }

    @Override
    public void animFadeInTextLogin() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.textLogin.setVisibility(View.VISIBLE);
        binding.textLogin.startAnimation(alphaAnimation);
    }

    @Override
    public void animFadeOutTextLogin() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.textLogin.startAnimation(alphaAnimation);
        binding.textLogin.setVisibility(View.GONE);
    }

    @Override
    public void animFadeInArcLoader() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.arcLoader.setVisibility(View.VISIBLE);
        binding.arcLoader.startAnimation(alphaAnimation);
    }

    @Override
    public void animFadeOutArcLoader() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.arcLoader.startAnimation(alphaAnimation);
        binding.arcLoader.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

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
