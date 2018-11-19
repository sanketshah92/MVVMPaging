package com.sample.sanketshah.coin.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseWrapper {
    @SerializedName("Response")
    @Expose
    private String response;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("BaseImageUrl")
    @Expose
    private String baseImageUrl;
    @SerializedName("BaseLinkUrl")
    @Expose
    private String baseLinkUrl;
    /*@SerializedName("DefaultWatchlist")
    @Expose
    private DefaultWatchlist defaultWatchlist;*/
    @SerializedName("SponosoredNews")
    @Expose
    private List<Object> sponosoredNews = null;
    @SerializedName("Data")
    @Expose
    private List<ResponseData> data = null;
    @SerializedName("Type")
    @Expose
    private Integer type;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    public String getBaseLinkUrl() {
        return baseLinkUrl;
    }

    public void setBaseLinkUrl(String baseLinkUrl) {
        this.baseLinkUrl = baseLinkUrl;
    }

/*    public DefaultWatchlist getDefaultWatchlist() {
        return defaultWatchlist;
    }

    public void setDefaultWatchlist(DefaultWatchlist defaultWatchlist) {
        this.defaultWatchlist = defaultWatchlist;
    }*/

    public List<Object> getSponosoredNews() {
        return sponosoredNews;
    }

    public void setSponosoredNews(List<Object> sponosoredNews) {
        this.sponosoredNews = sponosoredNews;
    }

    public List<ResponseData> getData() {
        return data;
    }

    public void setData(List<ResponseData> data) {
        this.data = data;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
