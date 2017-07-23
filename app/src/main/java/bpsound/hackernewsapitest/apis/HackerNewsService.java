package bpsound.hackernewsapitest.apis;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import bpsound.hackernewsapitest.common.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by elegantuniv on 2017. 7. 14..
 */

public class HackerNewsService {

    private HackerNewsService(){}

    public static HackerNewsApi createService() {
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Constants.SERVER_BASE_URL);
        return builder.build().create(HackerNewsApi.class);
    }
}
