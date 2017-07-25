package com.irvanyale.app.newsapi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.irvanyale.app.newsapi.R;
import com.irvanyale.app.newsapi.model.Article;
import com.irvanyale.app.newsapi.model.Source;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by WINDOWS 10 on 25/07/2017.
 */

public class ListArticlesAdapter extends RecyclerView.Adapter<ListArticlesAdapter.ViewHolder> {

    private Context context;
    private List<Article.ListArticle> listArticles;

    public ListArticlesAdapter(Context context, List<Article.ListArticle> listArticles) {
        this.context = context;
        this.listArticles = listArticles;
    }

    public Context getContext() {
        return context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView txtv_title;
        private TextView txtv_desc;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            txtv_title = (TextView) itemView.findViewById(R.id.txtv_title);
            txtv_desc = (TextView) itemView.findViewById(R.id.txtv_desc);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_articles, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Article.ListArticle listArticle = listArticles.get(position);

        holder.txtv_title.setText(listArticle.getTitle());
        holder.txtv_desc.setText(listArticle.getDescription());

        Picasso.with(getContext())
                .load(listArticle.getUrlToImage())
                .centerCrop()
                .resize(700,700)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return listArticles.size();
    }

    public void setListArticles(List<Article.ListArticle> listArticles){
        this.listArticles = listArticles;
        notifyDataSetChanged();
    }
}
