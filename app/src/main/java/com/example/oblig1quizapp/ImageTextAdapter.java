package com.example.oblig1quizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImageTextAdapter extends BaseAdapter {

    private Context context;
    private List<Dog> dogs;
    private int layoutResource;

    public ImageTextAdapter(Context context, List<Dog> dataList, int layoutResource) {
        this.context = context;
        this.dogs = dataList;
        this.layoutResource = layoutResource;
    }

    @Override
    public int getCount() {
        return dogs.size();
    }

    @Override
    public Object getItem(int position) {
        return dogs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutResource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.imageView);
            viewHolder.textView = view.findViewById(R.id.textView);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // Set data to views based on the position
        Dog data = dogs.get(position);
        viewHolder.imageView.setImageResource(data.getImageResource());
        viewHolder.textView.setText(data.getImageText());

        return view;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
