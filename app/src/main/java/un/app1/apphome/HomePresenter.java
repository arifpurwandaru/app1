package un.app1.apphome;

import android.util.Log;

import java.util.ArrayList;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import un.app1.R;
import un.app1.apphome.adapter.ProductModel;
import un.app1.apphome.model.Banner;
import un.app1.apphome.model.SubmitBanner;
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

    ArrayList<ProductModel> getProduct(){
        ArrayList<ProductModel> productModels = new ArrayList<>();
        productModels.add(new ProductModel(R.drawable.ic_pulsa, "Pulsa"));
        productModels.add(new ProductModel(R.drawable.ic_listrik, "Listrik"));
        return productModels;
    }

    void getHomeBanner(SubmitBanner submitBanner){
        Subscription subscription = service.requestBanner(submitBanner, new MyCallBack.CallBanner() {

            @Override
            public void onError(String error) {

            }

            @Override
            public void onSuccess(Banner banner) {
                Log.e("x",">> " + banner.banner);
            }
        });

        subscriptions.add(subscription);
    }

    void getPreviewUser(){
        homeView.setUserName("User name");
        homeView.setUserImage("http://tango.image-static.hipwee.com/wp-content/uploads/2014/05/20130801044011344-800x501.jpg");
        homeView.setShowUserImage();
    }

    void checkUserLogin(){
        homeView.setUserName("Login or Signup");
    }

    void unSubscribe() {
        subscriptions.unsubscribe();
    }

}
