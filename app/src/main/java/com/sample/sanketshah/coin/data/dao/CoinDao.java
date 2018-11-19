package com.sample.sanketshah.coin.data.dao;

import com.sample.sanketshah.coin.data.CointEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addCoin(CointEntity cointEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertBulk(List<CointEntity> cointEntities);
//ORDER BY c_name ASC
    @Query("Select * From coins WHERE c_coinId >=:id LIMIT :size")
    List<CointEntity> getCoin(int id, int size);

    @Query("DELETE FROM coins")
    public void nukeTable();

}
