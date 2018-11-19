package com.sample.sanketshah.coin.data;

import com.sample.sanketshah.coin.repository.CoinRepository;

import androidx.paging.DataSource;
import io.reactivex.disposables.CompositeDisposable;

public class CoinDataSourceFactory extends DataSource.Factory<Integer, CointEntity> {

    private CoinDataSource coinDataSource;
    private CoinRepository coinRepository;
    private CompositeDisposable compositeDisposable;

    public CoinDataSourceFactory(CoinRepository coinRepository, CompositeDisposable compositeDisposable) {
        this.coinRepository = coinRepository;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public DataSource<Integer, CointEntity> create() {
        if (coinDataSource == null) {
            coinDataSource = new CoinDataSource(coinRepository, compositeDisposable);
        }
        return coinDataSource;
    }
}
