package un.app1.deps;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ss.com.bannerslider.views.BannerSlider;
import un.app1.apphome.ActivityHome;
import un.app1.apphome.adapter.ProductAdapter;
import un.app1.network.internet.ConnectivityReceiver;
import un.app1.network.service.RetBuilder;
import un.app1.network.service.RetBuilder;

@Module
public class AppModule {

    @Provides
    @Singleton
    public ConnectivityReceiver providesConnectivityReceiver() {
        return new ConnectivityReceiver();
    }

    @Provides
    @Singleton
    public RetBuilder providesRetrofitBuilder() {
        return new RetBuilder();
    }

    @Provides
    @Singleton
    public ProductAdapter providesProductAdapter() {
        return new ProductAdapter();
    }

}
