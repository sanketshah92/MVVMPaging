package com.sample.sanketshah.database;

import android.content.Context;

import com.sample.sanketshah.coin.data.CointEntity;
import com.sample.sanketshah.coin.data.dao.CoinDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CointEntity.class}, version = 1,exportSchema = false)
public abstract class CoinDatabase extends RoomDatabase {
    public abstract CoinDao coinDao();
    public static volatile CoinDatabase coinDatabase;
    public static CoinDatabase getCoinDatabase(Context mContext){
        if (coinDatabase==null){
            synchronized (CoinDatabase.class){
                coinDatabase = Room.databaseBuilder(mContext.getApplicationContext(),CoinDatabase.class,"coin_database").build();
            }
        }
        return coinDatabase;
    }
}
