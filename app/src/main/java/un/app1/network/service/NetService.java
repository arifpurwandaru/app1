package un.app1.network.service;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import un.app1.apphome.model.Banner;
import un.app1.apphome.model.QuickPreview;
import un.app1.apphome.model.SubmitBanner;
import un.app1.apphome.model.SubmitQuickPreview;
import un.app1.pagelogin.model.DataLogin;
import un.app1.pagelogin.model.SubmitLogin;

interface NetService {

    @POST("getbannser")
    Observable<Banner> reqBanner(@Body SubmitBanner submitLogin);

    @POST("usrpreview")
    Observable<QuickPreview> reqBanner(@Body SubmitQuickPreview submitQuickPreview);

    @POST("userlogin")
    Observable<DataLogin> reqLogin(@Body SubmitLogin submitLogin);

}
