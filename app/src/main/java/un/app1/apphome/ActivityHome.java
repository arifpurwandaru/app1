package un.app1.apphome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stephentuso.welcome.WelcomeHelper;

import java.util.List;

import javax.inject.Inject;

import rebus.permissionutils.AskAgainCallback;
import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;
import rebus.permissionutils.SmartCallback;
import un.app1.MainApp;
import un.app1.R;
import un.app1.apphome.adapter.MainMenuAdapter;
import un.app1.apphome.adapter.ProductAdapter;
import un.app1.apphome.model.ArrayBanner;
import un.app1.apphome.model.SubmitBanner;
import un.app1.apphome.model.SubmitQuickPreview;
import un.app1.databinding.ActivityHomeBinding;
import un.app1.network.internet.ConnectivityReceiver;
import un.app1.network.service.MainService;
import un.app1.network.service.RetBuilder;
import un.app1.pagelogin.ActivityLogin;
import un.app1.splash.AppWelcome;

public class ActivityHome extends AppCompatActivity implements HomeView, ConnectivityReceiver.ConnectivityReceiverListener {

    @Inject
    public RetBuilder retBuilder;

    @Inject
    public ProductAdapter productAdapter;

    @Inject
    public MainMenuAdapter menuAdapter;

    ActivityHomeBinding binding;
    HomePresenter presenter;
    HomeViewModel viewModel;
    MainService mainService;

    boolean islogin = false;

    static int REQUEST_CODE_LOGIN = 1;
    static int WELCOME_SCREEN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        ((MainApp) getApplication()).providesAppComponents().inject(ActivityHome.this);
        MainApp.getInstance().setConnectivityListener(ActivityHome.this);

        binding = DataBindingUtil.setContentView(ActivityHome.this, R.layout.activity_home);
        mainService = new MainService(retBuilder.service());
        viewModel = new HomeViewModel(new HomeMainModel());
        presenter = new HomePresenter(ActivityHome.this, ActivityHome.this, mainService);
        binding.setViewModel(viewModel);
        binding.setPresenter(presenter);

        presenter.getHomeBanner(new SubmitBanner("deviceId", "token"));
        presenter.isUserLogin(islogin);

        splashFirstRun();
        setAdapter();
        onClickBanner();
        askPermission();
    }

    private void askPermission(){
        PermissionManager.with(this)
                .key(101)
                .permission(PermissionEnum.READ_PHONE_STATE,
                        PermissionEnum.WRITE_EXTERNAL_STORAGE,
                        PermissionEnum.ACCESS_COARSE_LOCATION,
                        PermissionEnum.ACCESS_FINE_LOCATION,
                        PermissionEnum.READ_SMS)
                .askAgain(true)
                .askAgainCallback(new AskAgainCallback() {
                    @Override
                    public void showRequestPermission(UserResponse response) {
                        Log.e("app", "response= " + response);
                    }
                })
                .callback(new SmartCallback() {
                    @Override
                    public void result(boolean allPermissionsGranted, boolean somePermissionsDeniedForever) {

                    }
                })
                .ask();
    }

    @Override
    public void splashFirstRun(){
        new WelcomeHelper(ActivityHome.this, AppWelcome.class).forceShow(WELCOME_SCREEN);
    }

    private void setAdapter() {
        productAdapter.setViewData(ActivityHome.this);
        productAdapter.setProduct(presenter.getProduct());
        binding.recyclerProduct.setLayoutManager(new GridLayoutManager(ActivityHome.this, 3));
        binding.recyclerProduct.setAdapter(productAdapter);

        menuAdapter.setViewData(ActivityHome.this);
        menuAdapter.setMainMenu(presenter.getMainMenu());
        binding.recyclerMainMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerMainMenu.setAdapter(menuAdapter);

    }

    @Override
    public void userPreview(){
        presenter.getQuickPreview(new SubmitQuickPreview("deviceId", "token"));
    }

    @Override
    public void goToMenuActivity(int position) {
        presenter.toActivity(position);
    }

    @Override
    public void animFadeInSignIn() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.layoutHomeDashboardSignIn.setVisibility(View.VISIBLE);
        binding.layoutHomeDashboardSignIn.startAnimation(alphaAnimation);
    }

    @Override
    public void animFadeInBanner() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.bannerSlider.setVisibility(View.VISIBLE);
        binding.bannerSlider.startAnimation(alphaAnimation);
    }

    @Override
    public void animFadeInBlank() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.layoutHomeDashboardBlank.setVisibility(View.VISIBLE);
        binding.layoutHomeDashboardBlank.startAnimation(alphaAnimation);
    }

    @Override
    public void animFadeInChecking() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.layoutHomeDashboardChecking.setVisibility(View.VISIBLE);
        binding.layoutHomeDashboardChecking.startAnimation(alphaAnimation);
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
    public void animFadeOutChecking() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.layoutHomeDashboardChecking.startAnimation(alphaAnimation);
        binding.layoutHomeDashboardChecking.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        presenter.unSubscribe();
        super.onDestroy();
    }

    @Override
    public void onClickLogin() {
        startActivityForResult(new Intent(ActivityHome.this, ActivityLogin.class), REQUEST_CODE_LOGIN);
    }

    @Override
    public void onClickRegister() {
        startActivityForResult(new Intent(ActivityHome.this, ActivityLogin.class), 1);
    }

    @Override
    public void onClickRetry() {
        presenter.getHomeBanner(new SubmitBanner("deviceId", "token"));
    }

    private void onClickBanner() {
        binding.bannerSlider.setHideIndicators(false);
        binding.bannerSlider.setHideIndicators(true);
        binding.bannerSlider.setOnBannerClickListener(position -> Toast.makeText(ActivityHome.this, String.valueOf(position), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void setShowUserImage() {
        Picasso.with(ActivityHome.this)
                .load(viewModel.getUserImage())
                .placeholder(R.drawable.ic_user)
                .resize(350, 350)
                .into(binding.imageUser);
    }

    @Override
    public void setUserImage(String imageUrl) {
        viewModel.setUserImage(imageUrl);
    }

    @Override
    public void goToActivity() {

    }

    @Override
    public void setLayoutBlank(){
        binding.layoutHomeDashboardBlank.setVisibility(View.VISIBLE);
        binding.layoutHomeDashboardChecking.setVisibility(View.GONE);
        binding.layoutHomeDashboardSignIn.setVisibility(View.GONE);
        binding.layoutHomeDashboardSignOut.setVisibility(View.GONE);
    }

    @Override
    public void setLayoutChecking() {
        binding.layoutHomeDashboardChecking.setVisibility(View.VISIBLE);
        binding.layoutHomeDashboardBlank.setVisibility(View.GONE);
        binding.layoutHomeDashboardSignIn.setVisibility(View.GONE);
        binding.layoutHomeDashboardSignOut.setVisibility(View.GONE);
    }

    @Override
    public void setLayoutSignOut() {
        binding.layoutHomeDashboardSignOut.setVisibility(View.VISIBLE);
        binding.layoutHomeDashboardBlank.setVisibility(View.GONE);
        binding.layoutHomeDashboardChecking.setVisibility(View.GONE);
        binding.layoutHomeDashboardSignIn.setVisibility(View.GONE);
    }

    @Override
    public void setLayoutSignIn() {
        binding.layoutHomeDashboardSignIn.setVisibility(View.VISIBLE);
        binding.layoutHomeDashboardBlank.setVisibility(View.GONE);
        binding.layoutHomeDashboardChecking.setVisibility(View.GONE);
        binding.layoutHomeDashboardSignOut.setVisibility(View.GONE);
    }

    @Override
    public void bannserSize(List<ArrayBanner> arrayBanners) {
        presenter.setBanner(binding.bannerSlider, arrayBanners);
    }

    @Override
    public void setUserName(String username) {
        viewModel.setUserName(username);
    }

    @Override
    public void setLogin(String login) {
        viewModel.setUserName(login);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        presenter.checkInternet(isConnected);
    }

    @Override
    public void snackBar() {
        Snackbar.make(binding.coordinatorLayout, getResources().getString(R.string.strConnectionLost), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void ifBannerFailed() {
        presenter.getHomeBanner(new SubmitBanner("deviceId", "token"));
    }

    @Override
    public void ifUserPreviewFailed() {
        presenter.getQuickPreview(new SubmitQuickPreview("deviceId", "token"));
    }

    @Override
    public void showClickRetry() {
        binding.textRetry.setVisibility(View.VISIBLE);
        onClickRetry();
    }

    @Override
    public void hideClickRetry() {
        binding.textRetry.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.forResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        presenter.unSubscribe();
        super.onBackPressed();
    }

}
