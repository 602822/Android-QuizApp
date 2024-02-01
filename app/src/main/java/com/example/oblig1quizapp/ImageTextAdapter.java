package com.example.oblig1quizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageTextAdapter extends BaseAdapter {

    private Context context;
    private List<Dog> dogs;

    public ImageTextAdapter(Context context, List<Dog> dogs) {
        this.context = context;
        this.dogs = dogs;
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
            view = inflater.inflate(R.layout.grid_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.imageView);
            viewHolder.textView = view.findViewById(R.id.textView);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // Set data to views based on the position
        Dog data = dogs.get(position);

        // Check if the Dog instance has a resource ID
        if (data.getImageResource() != 0) {
            // Use Glide to load the image from resource ID into an ImageView
            Glide.with(context).load(data.getImageResource()).into(viewHolder.imageView);
        } else if (data.getImageUri() != null) {
            // Use Glide to load the image from Uri into an ImageView
            Glide.with(context).load(data.getImageUri()).into(viewHolder.imageView);
        }

        viewHolder.textView.setText(data.getImageText());

        return view;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
