package com.sample.sanketshah.network;


import android.annotation.SuppressLint;
import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sample.sanketshah.BuildConfig;
import com.sample.sanketshah.R;
import com.sample.sanketshah.appexceptions.NoConnectionException;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    /**
     * generate OKhttp client
     */
    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(logging);
            }
            builder.readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();
            okHttpClient = builder.build();

        }
        return okHttpClient;
    }

    /**
     * generate API interface object for foreground with progress bar
     */
    public static ApiCallInterface getApiClient(Context con, String url) throws NoConnectionException {
        mContext = con;
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(getOkHttpClient());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        retrofit = builder.build();

        if (NetworkUtil.getConnectionStatus(mContext) == NetworkUtil.NOT_CONNECTED) {
            throw new NoConnectionException(mContext.getResources().getString(R.string.no_internet));
        }

        return retrofit.create(ApiCallInterface.class);
    }


}
