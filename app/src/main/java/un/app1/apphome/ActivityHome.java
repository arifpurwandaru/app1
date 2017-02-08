package un.app1.apphome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.goka.blurredgridmenu.BlurredGridMenuConfig;
import com.goka.blurredgridmenu.GridMenu;
import com.goka.blurredgridmenu.GridMenuFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ss.com.bannerslider.events.OnBannerClickListener;
import un.app1.MainApp;
import un.app1.R;
import un.app1.apphome.adapter.ProductAdapter;
import un.app1.apphome.model.ArrayBanner;
import un.app1.apphome.model.SubmitBanner;
import un.app1.apphome.model.SubmitQuickPreview;
import un.app1.databinding.ActivityHomeBinding;
import un.app1.network.internet.ConnectivityReceiver;
import un.app1.network.service.MainService;
import un.app1.network.service.RetBuilder;

public class ActivityHome extends AppCompatActivity implements HomeView, ConnectivityReceiver.ConnectivityReceiverListener {

    @Inject
    public RetBuilder retBuilder;

    @Inject
    public ProductAdapter productAdapter;

    ActivityHomeBinding binding;
    HomePresenter homePresenter;
    HomeViewModel homeViewModel;
    MainService mainService;

    private GridMenuFragment mGridMenuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        ((MainApp) getApplication()).providesAppComponents().inject(ActivityHome.this);
        MainApp.getInstance().setConnectivityListener(ActivityHome.this);

        binding = DataBindingUtil.setContentView(ActivityHome.this, R.layout.activity_home);
        mainService = new MainService(retBuilder.service());
        homeViewModel = new HomeViewModel(new HomeMainModel());
        homePresenter = new HomePresenter(ActivityHome.this, mainService);
        binding.setViewModel(homeViewModel);
        binding.setPresenter(homePresenter);

        setAdapter();
        onClickBanner();

        homePresenter.getHomeBanner(new SubmitBanner("deviceId", "token"));
        homePresenter.getQuickPreview(new SubmitQuickPreview("deviceId", "token"));

        makeBlurConfig();
        makeGridMenuFragment();

    }

    private void makeBlurConfig() {
        BlurredGridMenuConfig
                .build(new BlurredGridMenuConfig.Builder()
                        .radius(1)
                        .downsample(1)
                        .overlayColor(Color.parseColor("#AA000000")));
    }

    private void makeGridMenuFragment() {

        mGridMenuFragment = GridMenuFragment.newInstance(R.drawable.ic_menu_2);

        List<GridMenu> menus = new ArrayList<>();
        menus.add(new GridMenu("Home", R.drawable.ic_menu_2));
        menus.add(new GridMenu("Calendar", R.drawable.ic_menu_2));
        menus.add(new GridMenu("Overview", R.drawable.ic_menu_2));
        menus.add(new GridMenu("Groups", R.drawable.ic_menu_2));
        menus.add(new GridMenu("Lists", R.drawable.ic_menu_2));
        menus.add(new GridMenu("Profile", R.drawable.ic_menu_2));
        menus.add(new GridMenu("Timeline", R.drawable.ic_menu_2));
        menus.add(new GridMenu("Setting", R.drawable.ic_menu_2));
        menus.add(new GridMenu("Setting", R.drawable.ic_menu_2));

        mGridMenuFragment.setupMenu(menus);

        mGridMenuFragment.setOnClickMenuListener(new GridMenuFragment.OnClickMenuListener() {
            @Override
            public void onClickMenu(GridMenu gridMenu, int position) {
                Toast.makeText(ActivityHome.this, "Title:" + gridMenu.getTitle() + ", Position:" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onClickRetry(){
        binding.textRetry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                homePresenter.getHomeBanner(new SubmitBanner("deviceId", "token"));
            }
        });
    }

    private void onClickBanner(){
        binding.bannerSlider.setHideIndicators(false);
        binding.bannerSlider.setHideIndicators(true);
        binding.bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {

            @Override
            public void onClick(int position) {

                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.main_frame, mGridMenuFragment);
                tx.addToBackStack(null);
                tx.commit();
                Toast.makeText(ActivityHome.this, "" + String.valueOf(position) + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(){
        productAdapter.setViewData(ActivityHome.this);
        productAdapter.setProduct(homePresenter.getProduct());
        binding.recyclerProduct.setLayoutManager(new GridLayoutManager(ActivityHome.this, 2));
        binding.recyclerProduct.setAdapter(productAdapter);
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
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onDestroy(){
        homePresenter.unSubscribe();
        super.onDestroy();
    }

    @Override
    public void setShowUserImage(){
        Picasso.with(ActivityHome.this)
                .load(homeViewModel.getUserImage())
                .placeholder(R.drawable.ic_user)
                .resize(300, 300)
                .into(binding.imageUser);
    }

    @Override
    public void setUserImage(String imageUrl) {
        homeViewModel.setUserImage(imageUrl);
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
        homePresenter.setBanner(binding.bannerSlider, arrayBanners);
    }

    @Override
    public void setUserName(String username) {
        homeViewModel.setUserName(username);
    }

    @Override
    public void setLogin(String login) {
        homeViewModel.setUserName(login);
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

}
