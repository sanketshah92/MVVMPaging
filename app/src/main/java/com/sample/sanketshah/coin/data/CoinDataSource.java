package com.sample.sanketshah.coin.data;

import android.util.Log;

import com.google.gson.Gson;
import com.sample.sanketshah.coin.data.dao.CoinDao;
import com.sample.sanketshah.coin.repository.CoinRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;
import io.reactivex.disposables.CompositeDisposable;

public class CoinDataSource extends ItemKeyedDataSource<Integer, CointEntity> {
    private CoinRepository coinRepository;
    private int sourceIndex;
    private CompositeDisposable compositeDisposable;
    private CoinDao coinDao;

    public CoinDataSource(CoinRepository coinRepository, CompositeDisposable compositeDisposable) {
        this.coinRepository = coinRepository;
        this.compositeDisposable = compositeDisposable;
        coinDao = coinRepository.getCoinDao();
    }



    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<CointEntity> callback) {
        List<CointEntity> cointEntities = coinDao.getCoin(0, params.requestedLoadSize);
        callback.onResult(cointEntities, 0, cointEntities.size());
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<CointEntity> callback) {
        List<CointEntity> cointEntities = coinDao.getCoin(params.key, params.requestedLoadSize);
        Log.e(":::", "" + new Gson().toJson(cointEntities));
        callback.onResult(cointEntities);

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<CointEntity> callback) {
        List<CointEntity> cointEntities = coinDao.getCoin(0, params.requestedLoadSize);
        callback.onResult(cointEntities);
    }

    @NonNull
    @Override
    public Integer getKey(@NonNull CointEntity item) {
        return item.getC_coinId();
    }
}
