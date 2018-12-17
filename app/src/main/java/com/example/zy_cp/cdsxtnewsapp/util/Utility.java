package com.example.zy_cp.cdsxtnewsapp.util;

import com.example.zy_cp.cdsxtnewsapp.gson.NewsList;
import com.google.gson.Gson;


public class Utility {
    public static NewsList parseJsonWithGson(final String requestText){
        Gson gson = new Gson();
        return gson.fromJson(requestText, NewsList.class);
    }

}
