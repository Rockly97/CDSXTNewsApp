package com.example.zy_cp.cdsxtnewsapp.gson;

import com.google.gson.annotations.SerializedName;


public class News {
    @SerializedName("ctime")
    public String time;

    public String title;

    public String description;

    public String picUrl;

    public String url;

}
