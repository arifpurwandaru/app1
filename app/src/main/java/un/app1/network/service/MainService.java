package un.app1.network.service;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import un.app1.apphome.model.Banner;
import un.app1.apphome.model.QuickPreview;
import un.app1.apphome.model.SubmitBanner;
import un.app1.apphome.model.SubmitQuickPreview;

public class MainService {

    private NetService service;

    public MainService(NetService service) {
        this.service = service;
    }

    public Subscription requestBanner(SubmitBanner submitBanner, final MyCallBack.CallBanner callback) {

        return service.reqBanner(submitBanner)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Banner>>() {
                    @Override
                    public Observable<? extends Banner> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Banner>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError("Something went wrong..");
                    }

                    @Override
                    public void onNext(Banner loginModel) {
                        callback.onSuccess(loginModel);
                    }
                });
    }

    public Subscription requestQuickPreview(SubmitQuickPreview submitQuickPreview, final MyCallBack.CallQuickPreview callback) {

        return service.reqBanner(submitQuickPreview)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends QuickPreview>>() {
                    @Override
                    public Observable<? extends QuickPreview> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<QuickPreview>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError("Something went wrong..");
                    }

                    @Override
                    public void onNext(QuickPreview quickPreview) {
                        callback.onSuccess(quickPreview);
                    }
                });
    }

}
