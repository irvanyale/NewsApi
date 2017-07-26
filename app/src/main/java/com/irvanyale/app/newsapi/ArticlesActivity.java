package com.irvanyale.app.newsapi;

import android.app.ProgressDialog;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.irvanyale.app.newsapi.adapter.ListArticlesAdapter;
import com.irvanyale.app.newsapi.api.ApiClient;
import com.irvanyale.app.newsapi.api.ApiInterface;
import com.irvanyale.app.newsapi.helper.ArticleHelper;
import com.irvanyale.app.newsapi.model.Article;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesActivity extends AppCompatActivity {

    private RecyclerView rvly_list_articles;
    private ListArticlesAdapter listArticlesAdapter;
    private ApiInterface client;
    private Article article = new Article();
    private List<Article.ListArticle> listArticles = new ArrayList<>();
    private List<Article.ListArticle> listArticleHelper = new ArrayList<>();
    private String source = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        client = ApiClient.createService(ApiInterface.class);

        rvly_list_articles = (RecyclerView) findViewById(R.id.rvly_list_articles);
        listArticlesAdapter = new ListArticlesAdapter(this, listArticles);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvly_list_articles.setLayoutManager(linearLayoutManager);
        rvly_list_articles.setAdapter(listArticlesAdapter);

        source = getIntent().getStringExtra("source");

        listArticleHelper = ArticleHelper.getArticle();
        listArticleHelper.clear();

        loadDataArticles(source);
    }

    private void loadDataArticles(String source){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        String apiKey = "116484b109be43f8b88d0dcca1bd39e6";

        Call<Article> api = client.getListArticle(source, apiKey);
        api.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if (response.isSuccessful()){
                    article = response.body();

                    if (article.getStatus().equals("ok")){
                        listArticles = article.getArticles();

                        for (Article.ListArticle data : listArticles){
                            listArticleHelper.add(data);
                        }
                        listArticlesAdapter.setListArticles(listArticles);
                    }

                } else {
                    Toast.makeText(ArticlesActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Toast.makeText(ArticlesActivity.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void loadDataSearchArticle(String query){

        listArticles.clear();

        CharSequence queryc = query;

        for (Article.ListArticle data : listArticleHelper){
            if (data.getTitle().trim().toLowerCase().contains(queryc)){
                Log.d("TAG", "loadDataSearchArticle: "+data.getTitle());
                listArticles.add(data);
            }
        }

        listArticlesAdapter.setListArticles(listArticles);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadDataSearchArticle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }
}
