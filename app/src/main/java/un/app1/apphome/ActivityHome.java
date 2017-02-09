package un.app1.apphome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

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
        presenter.getQuickPreview(new SubmitQuickPreview("deviceId", "token"));
        binding.bannerSlider.setVisibility(View.GONE);

        setAdapter();
        onClickBanner();

    }

    private void setAdapter(){
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
    public void animFadeInSignIn(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.layoutHomeDashboardSignIn.startAnimation(alphaAnimation);
    }

    @Override
    public void animFadeInBanner(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.bannerSlider.setVisibility(View.VISIBLE);
        binding.bannerSlider.startAnimation(alphaAnimation);
        binding.arcLoader.setVisibility(View.GONE);


    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onDestroy(){
        presenter.unSubscribe();
        super.onDestroy();
    }

    @Override
    public void onClickLogin(){
        startActivityForResult(new Intent(ActivityHome.this, ActivityLogin.class), 1);
    }

    @Override
    public void onClickRegister() {
        startActivityForResult(new Intent(ActivityHome.this, ActivityLogin.class), 1);
    }

    @Override
    public void onClickRetry(){
        presenter.getHomeBanner(new SubmitBanner("deviceId", "token"));
    }

    private void onClickBanner(){
        binding.bannerSlider.setHideIndicators(false);
        binding.bannerSlider.setHideIndicators(true);
        binding.bannerSlider.setOnBannerClickListener(position -> Toast.makeText(ActivityHome.this, String.valueOf(position), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void setShowUserImage(){
        Picasso.with(ActivityHome.this)
                .load(viewModel.getUserImage())
                .placeholder(R.drawable.ic_user)
                .resize(300, 300)
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
    public void setLayoutChecking() {
        binding.layoutHomeDashboardChecking.setVisibility(View.VISIBLE);
        binding.layoutHomeDashboardSignIn.setVisibility(View.GONE);
        binding.layoutHomeDashboardSignOut.setVisibility(View.GONE);
    }

    @Override
    public void setLayoutSignOut() {
        binding.layoutHomeDashboardChecking.setVisibility(View.GONE);
        binding.layoutHomeDashboardSignIn.setVisibility(View.GONE);
        binding.layoutHomeDashboardSignOut.setVisibility(View.VISIBLE);
    }

    @Override
    public void setLayoutSignIn() {
        binding.layoutHomeDashboardChecking.setVisibility(View.GONE);
        binding.layoutHomeDashboardSignIn.setVisibility(View.VISIBLE);
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
    public void onBackPressed(){
        presenter.unSubscribe();
        super.onBackPressed();
    }

}
