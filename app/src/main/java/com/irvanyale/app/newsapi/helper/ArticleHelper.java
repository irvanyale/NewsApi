package com.irvanyale.app.newsapi.helper;

import com.irvanyale.app.newsapi.model.Article;

import java.util.List;
import java.util.Vector;

/**
 * Created by WINDOWS 10 on 26/07/2017.
 */

public class ArticleHelper {
    private static List<Article.ListArticle> article;

    public static List<Article.ListArticle> getArticle(){
        if (article == null){
            article = new Vector<>();
        }
        return article;
    }
}
