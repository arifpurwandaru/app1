package un.app1.network.service;

import un.app1.apphome.model.Banner;
import un.app1.apphome.model.QuickPreview;
import un.app1.pagelogin.model.DataLogin;

public class MyCallBack {

    public interface CallBanner {

        void onError(String error);

        void onSuccess(Banner banner);
    }

    public interface CallQuickPreview {

        void onError(String error);

        void onSuccess(QuickPreview quickPreview);
    }

    public interface CallLogin {

        void onError(String error);

        void onSuccess(DataLogin dataLogin);
    }

}
