package un.app1.network.service;

import un.app1.apphome.model.Banner;

public class MyCallBack {

    public interface CallBanner {

        void onError(String error);

        void onSuccess(Banner banner);
    }

}
