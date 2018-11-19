package com.sample.sanketshah.coin.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.sample.sanketshah.coin.data.CointEntity;
import com.sample.sanketshah.coin.data.dao.CoinDao;
import com.sample.sanketshah.database.CoinDatabase;

import java.util.List;

public class CoinRepository {
    private CoinDao coinDao;

    public CoinRepository(Application application) {
        CoinDatabase coinDatabase = CoinDatabase.getCoinDatabase(application);
        coinDao = coinDatabase.coinDao();

    }

    public CoinDao getCoinDao() {
        return coinDao;
    }


    public void insertCoin(List<CointEntity> cointEntity) {
        new InsertTask(coinDao).execute(cointEntity);

    }

    public void deleteCoin() {
        new DeleteTask(coinDao).execute();
    }

    private static class InsertTask extends AsyncTask<List<CointEntity>, Void, Void> {
        private CoinDao coinDao;

        InsertTask(CoinDao coinDao) {
            this.coinDao = coinDao;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<CointEntity>... lists) {
            coinDao.insertBulk(lists[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


    private static class DeleteTask extends AsyncTask<Void, Void, Void> {
        private CoinDao coinDao;

        public DeleteTask(CoinDao coinDao) {
            this.coinDao = coinDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            coinDao.nukeTable();
            return null;
        }
    }
}
