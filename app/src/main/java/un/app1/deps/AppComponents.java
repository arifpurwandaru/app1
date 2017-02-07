package un.app1.deps;

import javax.inject.Singleton;

import dagger.Component;
import un.app1.apphome.ActivityHome;
import un.app1.apphome.adapter.ProductAdapter;

@Singleton
@Component(modules = {AppModule.class})

public interface AppComponents {

    void inject(ActivityHome activityHome);

}
