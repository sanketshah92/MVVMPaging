package com.sample.sanketshah.coin.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sample.sanketshah.appexceptions.NoConnectionException;
import com.sample.sanketshah.coin.data.CoinDataSourceFactory;
import com.sample.sanketshah.coin.data.CointEntity;
import com.sample.sanketshah.coin.data.ResponseData;
import com.sample.sanketshah.coin.repository.CoinRepository;
import com.sample.sanketshah.network.ApiCallInterface;
import com.sample.sanketshah.network.ApiClient;
import com.sample.sanketshah.network.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


@SuppressLint("ParcelCreator")
public class CoinViewModel extends AndroidViewModel implements Parcelable {
    public CoinRepository coinRepository;
    public LiveData<PagedList<CointEntity>> coins1;
    private ApiCallInterface apiInterface;
    public CompositeDisposable compositeDisposable = new CompositeDisposable();
    private WeakReference<Context> mContext;


    public CoinViewModel(@NonNull Application application) {
        super(application);
        coinRepository = new CoinRepository(application);

        mContext = new WeakReference<>(application);
        initPaging(coinRepository, compositeDisposable);

    }



    private void insert(List<CointEntity> cointEntity) {
        coinRepository.insertCoin(cointEntity);
    }

    public void callCoinList() {
        try {
            apiInterface = ApiClient.getApiClient(mContext.get(), Urls.COINLISTAPI);
            Disposable disposable = apiInterface.getCoinList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<JsonObject>() {
                        @Override
                        public void accept(JsonObject jsonObject) throws Exception {
                            //Log.e("SUCCESS", "" + jsonObject.toString());
                            JSONObject json = new JSONObject(new Gson().toJson(jsonObject));
                            JSONObject data = json.getJSONObject("Data");
                            Iterator x = data.keys();
                            JSONArray jsonArray = new JSONArray();
                            while (x.hasNext()) {
                                String key = (String) x.next();
                                jsonArray.put(data.get(key));
                            }

                            Type listType = new TypeToken<List<ResponseData>>() {
                            }.getType();
                            List<ResponseData> responseData = new Gson().fromJson(jsonArray.toString(), listType);

                            //Log.e("UPDATED JSON::->", ":" + new Gson().toJson(responseData));
                            List<CointEntity> cointEntities = new ArrayList<>();
                            for (int i = 0; i < responseData.size(); i++) {
                                CointEntity cointEntity = new CointEntity();
                                cointEntity.setCoinName(responseData.get(i).getName());
                                cointEntity.setCoinFullName(responseData.get(i).getFullName());
                                cointEntity.setCoinAlgorithm(responseData.get(i).getAlgorithm());
                                cointEntity.setCoinImageUrl(responseData.get(i).getImageUrl());
                                cointEntity.setCoinId(responseData.get(i).getId());
                                cointEntities.add(cointEntity);
                            }
                            insert(cointEntities);

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            Toast.makeText(mContext.get(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            compositeDisposable.add(disposable);

        } catch (NoConnectionException e) {
            e.printStackTrace();
            Toast.makeText(mContext.get(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void initPaging(CoinRepository coinRepository, CompositeDisposable compositeDisposable) {
        CoinDataSourceFactory factory = new CoinDataSourceFactory(coinRepository, compositeDisposable);

        //create PagedList Config
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(50)
                .setPageSize(25).build();

        //create LiveData object using LivePagedListBuilder which takes
        //data source factory and page config as params
        Executor executors = Executors.newFixedThreadPool(5);
        coins1 = new LivePagedListBuilder<>(factory, config).setFetchExecutor(executors).build();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this);
    }

    public static class CoinViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        private Application mApplication;

        public CoinViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> viewModel) {
            return (T) new CoinViewModel(mApplication);
        }
    }
}
