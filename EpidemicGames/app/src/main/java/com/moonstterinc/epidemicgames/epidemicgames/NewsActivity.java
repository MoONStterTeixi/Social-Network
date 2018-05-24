package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private List<News> news;
    private NewsAdapter newsAdapter;
    private String newsTitle = "Noticias";
    private String newsText = "Texto";
    private String newsLabel = "Label";
    private String newsDate = "00/00/0000";

    //*
    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        news = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            News news = new News();

            news.setTitle(newsTitle);
            news.setText(newsText);
            news.setLabel(newsLabel);
            news.setDate(newsDate);


            this.news.add(news);

            //*
            mainGrid = findViewById(R.id.mainGrid);
            //Set Event
            //setSingleEvent(mainGrid);
        }

        //find view by id and attaching adapter for the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(recyclerView, news, this);
        recyclerView.setAdapter(newsAdapter);

        //set load more listener for the RecyclerView adapter
        newsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (news.size() <= 20) {
                    news.add(null);
                    newsAdapter.notifyItemInserted(news.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            news.remove(news.size() - 1);
                            newsAdapter.notifyItemRemoved(news.size());

                            //Generating more data
                            int index = news.size();
                            int end = index + 5;
                            for (int i = index; i < end; i++) {
                                News news = new News();

                                news.setTitle(newsTitle);
                                news.setText(newsText);
                                news.setLabel(newsLabel);
                                news.setDate(newsDate);

                               NewsActivity.this.news.add(news);
                            }
                            newsAdapter.notifyDataSetChanged();
                            newsAdapter.setLoaded();
                        }
                    }, 5000);
                } else {
                    Toast.makeText(NewsActivity.this, "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //*
    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NewsActivity.this,ActivityOne.class);
                    intent.putExtra("info","This is activity from card item index  "+finalI);
                    startActivity(intent);
                    }

                    //Un solo layout
                    /*Intent intent = new Intent(MainActivity.this,ActivityOne.class);
                    intent.putExtra("info","This is activity from card item index  "+finalI);
                    startActivity(intent);*/
            });
        }
    }
}
