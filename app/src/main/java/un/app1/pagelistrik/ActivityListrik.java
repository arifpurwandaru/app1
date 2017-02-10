package un.app1.pagelistrik;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import un.app1.MainApp;
import un.app1.R;
import un.app1.databinding.ActivityListrikBinding;
import un.app1.network.internet.ConnectivityReceiver;

public class ActivityListrik extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ActivityListrikBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        ((MainApp) getApplication()).providesAppComponents().inject(ActivityListrik.this);
        MainApp.getInstance().setConnectivityListener(ActivityListrik.this);

        binding = DataBindingUtil.setContentView(ActivityListrik.this, R.layout.activity_listrik);
//        mainService = new MainService(retBuilder.service());
//        viewModel = new HomeViewModel(new HomeMainModel());
//        presenter = new HomePresenter(ActivityListrik.this, ActivityListrik.this, mainService);
//        binding.setViewModel(viewModel);
//        binding.setPresenter(presenter);

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
}
