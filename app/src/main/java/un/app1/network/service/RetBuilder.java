package un.app1.network.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import un.app1.constants.ConsService;

public class RetBuilder {

    public NetService service(){
        OkHttpClient okhttpBuilder = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okhttpBuilder)
                .baseUrl("https://private-1e1e5e-app118.apiary-mock.com/")
                .build();

        return retrofit.create(NetService.class);
    }

}
