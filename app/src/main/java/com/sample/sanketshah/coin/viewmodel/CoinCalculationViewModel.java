package com.sample.sanketshah.coin.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sample.sanketshah.appexceptions.NoConnectionException;
import com.sample.sanketshah.network.ApiCallInterface;
import com.sample.sanketshah.network.ApiClient;
import com.sample.sanketshah.network.Urls;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CoinCalculationViewModel extends AndroidViewModel {
    private Retrofit builder;
    private ApiCallInterface apiInterface;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private WeakReference<Context> mContext;
    public MutableLiveData<String> calculatedTotal;

    public CoinCalculationViewModel(@NonNull Application application) {
        super(application);
        mContext = new WeakReference<>(application.getApplicationContext());
        calculatedTotal = new MutableLiveData<>();
        calculatedTotal.setValue("");
    }


    public void calculate(String coinName, String currency, String qty) throws NoConnectionException {
        apiInterface = ApiClient.getApiClient(mContext.get(), Urls.GETPRICEAPI);
        Disposable disposable = apiInterface.getCalculatedPrice("ETH", "" + coinName + "," + currency)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    JSONObject json = new JSONObject(new Gson().toJson(jsonObject));
                    String val = json.getString("" + currency);
                    Log.e("VALUE IS:", "" + val);
                    float calculatedPrice = Float.parseFloat(val) * Float.parseFloat(qty);
                    BigDecimal bigDecimal = BigDecimal.valueOf(Float.parseFloat(val)).multiply(BigDecimal.valueOf(Float.parseFloat(qty)));
                    Log.e("VALUE IS:", "" + calculatedPrice);
                    //Log.e("VALUE IS:", "" + bigDecimal);
                    calculatedTotal.setValue(String.valueOf(calculatedPrice));
                }, throwable -> throwable.printStackTrace());
        compositeDisposable.add(disposable);

    }

}
