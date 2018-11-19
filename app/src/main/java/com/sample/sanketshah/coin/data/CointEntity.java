package com.sample.sanketshah.coin.data;

import java.io.Serializable;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "coins", indices = {@Index(value = {"c_name", "c_id", "c_fname", "c_algo", "c_url"}, unique = true)})
public class CointEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int c_coinId;

    @ColumnInfo(name = "c_name")
    public String coinName;
    @ColumnInfo(name = "c_url")
    public String coinImageUrl;
    @ColumnInfo(name = "c_fname")
    public String coinFullName;
    @ColumnInfo(name = "c_algo")
    public String coinAlgorithm;
    @ColumnInfo(name = "c_id")
    public String coinId;


    public int getC_coinId() {
        return c_coinId;
    }

    public void setC_coinId(int c_coinId) {
        this.c_coinId = c_coinId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinImageUrl() {
        return coinImageUrl;
    }

    public void setCoinImageUrl(String coinImageUrl) {
        this.coinImageUrl = coinImageUrl;
    }

    public String getCoinFullName() {
        return coinFullName;
    }

    public void setCoinFullName(String coinFullName) {
        this.coinFullName = coinFullName;
    }

    public String getCoinAlgorithm() {
        return coinAlgorithm;
    }

    public void setCoinAlgorithm(String coinAlgorithm) {
        this.coinAlgorithm = coinAlgorithm;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        CointEntity cointEntity = (CointEntity) obj;
        return cointEntity.getCoinName().equalsIgnoreCase(this.getCoinName());
    }
}
