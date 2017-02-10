package un.app1.deps;

import javax.inject.Singleton;

import dagger.Component;
import un.app1.apphome.ActivityHome;
import un.app1.apphome.adapter.ProductAdapter;
import un.app1.pagelistrik.ActivityListrik;
import un.app1.pagelogin.ActivityLogin;

@Singleton
@Component(modules = {AppModule.class})

public interface AppComponents {

    void inject(ActivityHome activityHome);

    void inject(ActivityLogin activitylogin);

    void inject(ActivityListrik activityListrik);

}
