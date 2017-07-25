package com.irvanyale.app.newsapi;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.irvanyale.app.newsapi.adapter.ListSourcesAdapter;
import com.irvanyale.app.newsapi.api.ApiClient;
import com.irvanyale.app.newsapi.api.ApiInterface;
import com.irvanyale.app.newsapi.model.Source;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvly_list_sources;
    private ListSourcesAdapter listSourcesAdapter;
    private ApiInterface client;
    private Source source = new Source();
    private List<Source.ListSource> listSources = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = ApiClient.createService(ApiInterface.class);

        rvly_list_sources = (RecyclerView) findViewById(R.id.rvly_list_sources);

        listSourcesAdapter = new ListSourcesAdapter(this, listSources);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvly_list_sources.setLayoutManager(linearLayoutManager);
        rvly_list_sources.setAdapter(listSourcesAdapter);

        loadDataSources();
    }

    private void loadDataSources(){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        Call<Source> api = client.getListSource("en");
        api.enqueue(new Callback<Source>() {
            @Override
            public void onResponse(Call<Source> call, Response<Source> response) {
                if (response.isSuccessful()){
                    source = response.body();

                    if (source.getStatus().equals("ok")){
                        listSources = source.getSources();

                        listSourcesAdapter.setListSources(listSources);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Source> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
