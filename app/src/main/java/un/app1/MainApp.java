package un.app1;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import un.app1.deps.AppComponents;
import un.app1.deps.AppModule;
import un.app1.deps.DaggerAppComponents;
import un.app1.network.internet.ConnectivityReceiver;

public class MainApp extends Application {

    private AppComponents appComponents;
    private static MainApp mainApp;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(MainApp.this);
        mainApp = MainApp.this;
        appComponents = DaggerAppComponents.builder().appModule(new AppModule()).build();
    }

    public AppComponents providesAppComponents() {
        return appComponents;
    }

    public static synchronized MainApp getInstance() {
        return mainApp;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {

    }

}
