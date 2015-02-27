package com.ktruong.googleimagesearcher;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by ktruong on 2/25/15.
 */
public class Photo implements Serializable {
    private int width;
    private int height;
    private String imageUrl;
    private String thumbUrl;
    private String title;
    private int thumbWidth;
    private int thumbHeight;
    
    public Photo() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(int thumbWidth) {
        this.thumbWidth = thumbWidth;
    }

    public int getThumbHeight() {
        return thumbHeight;
    }

    public void setThumbHeight(int thumbHeight) {
        this.thumbHeight = thumbHeight;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Photo{");
        sb.append("width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", thumbUrl='").append(thumbUrl).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", thumbWidth=").append(thumbWidth);
        sb.append(", thumbHeight=").append(thumbHeight);
        sb.append('}');
        return sb.toString();
    }
}
