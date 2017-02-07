package un.app1.deps;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import un.app1.apphome.ActivityHome;
import un.app1.apphome.adapter.ProductAdapter;
import un.app1.network.internet.ConnectivityReceiver;
import un.app1.network.service.RetrofitBuilder;

@Module
public class AppModule {

    @Provides
    @Singleton
    public ConnectivityReceiver providesConnectivityReceiver() {
        return new ConnectivityReceiver();
    }

    @Provides
    @Singleton
    public RetrofitBuilder providesRetrofitBuilder() {
        return new RetrofitBuilder();
    }

    @Provides
    @Singleton
    public ProductAdapter providesProductAdapter() {
        return new ProductAdapter();
    }

}
