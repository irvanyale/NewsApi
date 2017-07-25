package com.irvanyale.app.newsapi.api;

import com.irvanyale.app.newsapi.model.Article;
import com.irvanyale.app.newsapi.model.Source;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by WINDOWS 10 on 25/07/2017.
 */

public interface ApiInterface {

    @GET("sources")
    Call<Source> getListSource(@Query("language") String languange);

    @GET("articles")
    Call<Article> getListArticle(@Query("source") String source, @Query("apiKey") String apiKey);
}
