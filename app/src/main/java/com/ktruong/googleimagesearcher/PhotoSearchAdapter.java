package com.ktruong.googleimagesearcher;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ktruong on 2/25/15.
 */
public class PhotoSearchAdapter extends ArrayAdapter<Photo> {

    private static class ViewHolder {
        public ImageView imageView;
        public TextView imageTitle;
    }

    public PhotoSearchAdapter(Context context, List<Photo> photos) {
        super(context, R.layout.item_photo_grid, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        Log.i("INFO", "get view " + position);
        
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo_grid, parent, false);

            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.photoImageView);
            viewHolder.imageTitle = (TextView) convertView.findViewById(R.id.imageTitle);
            
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        Photo photo = getItem(position);

        viewHolder.imageTitle.setText(Html.fromHtml(photo.getTitle()));
        viewHolder.imageView.setImageResource(0);
        
        String imageUrl = photo.getImageUrl();
        Log.i("INFO", "imageUrl " + imageUrl);
//        Picasso.with(getContext()).load(imageUrl).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(viewHolder.imageView);
        Picasso.with(getContext()).load(imageUrl).fit().placeholder(R.mipmap.ic_launcher).into(viewHolder.imageView);

        return convertView;
    }
}
