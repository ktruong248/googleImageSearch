package com.ktruong.googleimagesearcher.converter;

import com.ktruong.googleimagesearcher.models.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhotoConverter {
    public static List<Photo> fromJson(JSONArray results) {
        List<Photo> photos = new ArrayList<>();
        
        if(results != null) {
            int size = (results != null) ? results.length() : 0;
            try {
                for (int i = 0; i < size; i++) {
                    JSONObject imgData = results.getJSONObject(i);
                    String url = imgData.getString("url");
                    String thumbUrl = imgData.getString("tbUrl");
                    String title = imgData.getString("title");
                    int tbWidth = imgData.getInt("tbWidth");
                    int width = imgData.getInt("width");
                    int tbHeight = imgData.getInt("tbHeight");
                    int height = imgData.getInt("height");

                    Photo photo = new Photo();
                    photo.setImageUrl(url);
                    photo.setHeight(height);
                    photo.setThumbHeight(tbHeight);
                    photo.setWidth(width);
                    photo.setThumbWidth(tbWidth);
                    photo.setTitle(title);
                    photo.setThumbUrl(thumbUrl);
                    
                    photos.add(photo);
                }
            }catch (JSONException e) {
                throw new RuntimeException("failed to read json", e);
            }
        }
        
        return photos;
    }
}
