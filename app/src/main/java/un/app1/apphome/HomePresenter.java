package un.app1.apphome;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;
import un.app1.R;
import un.app1.apphome.adapter.ProductModel;
import un.app1.apphome.model.ArrayBanner;
import un.app1.apphome.model.Banner;
import un.app1.apphome.model.QuickPreview;
import un.app1.apphome.model.SubmitBanner;
import un.app1.apphome.model.SubmitQuickPreview;
import un.app1.network.service.MainService;
import un.app1.network.service.MyCallBack;

public class HomePresenter {

    private HomeView homeView;
    private MainService service;
    private CompositeSubscription subscriptions;

    HomePresenter(HomeView homeView, MainService service) {
        this.homeView = homeView;
        this.service = service;
        subscriptions = new CompositeSubscription();
    }

    ArrayList<ProductModel> getProduct() {
        ArrayList<ProductModel> productModels = new ArrayList<>();
        productModels.add(new ProductModel(R.drawable.ic_menu_1, "xxxxxx"));
        productModels.add(new ProductModel(R.drawable.ic_menu_2, "xxxxxx"));
        productModels.add(new ProductModel(R.drawable.ic_menu_2, "xxxxxx"));

        return productModels;
    }

    void getHomeBanner(SubmitBanner submitBanner) {
        Subscription subscription = service.requestBanner(submitBanner, new MyCallBack.CallBanner() {

            @Override
            public void onError(String error) {
                homeView.hideClickRetry();
            }

            @Override
            public void onSuccess(Banner banner) {
                homeView.bannserSize(banner.arrayBanners);
            }
        });

        subscriptions.add(subscription);
    }

    void getQuickPreview(SubmitQuickPreview submitQuickPreview) {
        Subscription subscription = service.requestQuickPreview(submitQuickPreview, new MyCallBack.CallQuickPreview() {

            @Override
            public void onError(String error) {

            }

            @Override
            public void onSuccess(QuickPreview quickPreview) {
//                homeView.setLayoutSignIn();
//                homeView.animFadeInSignIn();
                homeView.setUserName(quickPreview.user);
                homeView.setUserImage(quickPreview.imageUrl);
                homeView.setShowUserImage();
            }
        });

        subscriptions.add(subscription);
    }

    @SuppressWarnings("unused")
    void checkUserLogin() {
        homeView.setUserName("Login or Signup");
    }

    void setBanner(BannerSlider bannerSlider, List<ArrayBanner> arrayBanners) {
        homeView.animFadeInBanner();
        for (int i = 0; i < arrayBanners.size(); i++) {
            bannerSlider.addBanner(new RemoteBanner(arrayBanners.get(i).url));
        }
    }

    public void onClickRetry(){
        homeView.onClickRetry();
    }

    public void onClickLogin() {
        homeView.onClickLogin();
    }

    public void onClickRegister() {
        homeView.onClickRegister();
    }

    void unSubscribe() {
        subscriptions.unsubscribe();
    }

}
