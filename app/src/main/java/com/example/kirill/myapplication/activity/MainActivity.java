package com.example.kirill.myapplication.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kirill.myapplication.R;
import com.example.kirill.myapplication.adapter.FeedAdapter;
import com.example.kirill.myapplication.parser.FeedXmlParser;
import com.example.kirill.myapplication.vo.FeedVO;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView listView;
    private FeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.rootView = (ViewGroup) this.findViewById(android.R.id.content);

        listView = (ListView) findViewById(R.id.listView);
        feedAdapter = new FeedAdapter();
        listView.setAdapter(feedAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FeedVO item = feedAdapter.getItem(position);
                Intent i = new Intent(getApplicationContext(), VideoActivity.class);
                i.putExtra("item", item);
                startActivity(i);
            }
        });

        List<FeedVO> feeds = new ArrayList<>();
        feedAdapter.setItemList(feeds);
        loadFeed();
    }

    private void loadFeed() {
        FeedListViewLoader loader = new FeedListViewLoader();
        loader.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FeedListViewLoader extends AsyncTask<Void, Void, List<FeedVO>> {

        private Exception e;

        @Override
        protected void onPostExecute(List<FeedVO> feedVOs) {
            hideLoading();
            if (e == null) {
                feedAdapter.setItemList(feedVOs);
                return;
            }
            if (e instanceof IOException) {
                showTryConnection(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideTryConnection();
                        loadFeed();
                    }
                });
            }
        }

        @Override
        protected void onPreExecute() {
            showLoading();
        }

        @Override
        protected List<FeedVO> doInBackground(Void... params) {
            this.e = null;
            try {
                URL url = new URL("http://feeds.feedburner.com/tedtalks_video");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                try {
                    InputStream is = connection.getInputStream();
                    FeedXmlParser parser = new FeedXmlParser();
                    List<FeedVO> feeds = parser.parse(is);
                    return feeds;
                } finally {
                    connection.disconnect();
                }
            } catch (Exception e) {
                Log.e("task", "Exception in doInBackground", e);
                this.e = e;
            }
            return null;
        }

    }
}
