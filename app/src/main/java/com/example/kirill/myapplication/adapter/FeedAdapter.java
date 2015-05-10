package com.example.kirill.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kirill.myapplication.R;
import com.example.kirill.myapplication.vo.FeedVO;

import java.util.List;

public class FeedAdapter extends BaseAdapter {

    private List<FeedVO> itemList;

    public FeedAdapter() {
    }

    public int getCount() {
        if (itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    public FeedVO getItem(int position) {
        if (itemList == null) {
            return null;
        }
        return itemList.get(position);
    }

    public long getItemId(int position) {
        if (itemList == null) {
            return 0;
        }
        return itemList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout v = (LinearLayout) convertView;
        if (v == null) {
            v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_item, null);
        }
        FeedVO feed = getItem(position);
        TextView titleTextView = (TextView) v.findViewById(R.id.titleTextView);
        titleTextView.setText(feed.getTitle());

        TextView descTextView = (TextView) v.findViewById(R.id.descTextView);
        descTextView.setText(feed.getDescription());

        return v;
    }

    public void setItemList(List<FeedVO> itemList) {
        this.itemList = itemList;
        this.notifyDataSetChanged();
    }
}
