package com.ktruong.googleimagesearcher.models;

import java.io.Serializable;

public class SearchPreference implements Serializable {
    public static final String ANY = "any";
    
    private String imageSizeFilter;
    private String colorFilter;
    private String imageTypeFiler;
    private String siteFilter;

    public SearchPreference() {
        this.imageSizeFilter = ANY;
        this.colorFilter = ANY;
        this.imageTypeFiler = ANY;
        this.siteFilter = "google.com";
    }

    public String getImageSizeFilter() {
        return imageSizeFilter;
    }

    public void setImageSizeFilter(String imageSizeFilter) {
        this.imageSizeFilter = imageSizeFilter;
    }

    public String getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(String colorFilter) {
        this.colorFilter = colorFilter;
    }

    public String getImageTypeFiler() {
        return imageTypeFiler;
    }

    public void setImageTypeFiler(String imageTypeFiler) {
        this.imageTypeFiler = imageTypeFiler;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchPreference{");
        sb.append("imageSizeFilter='").append(imageSizeFilter).append('\'');
        sb.append(", colorFilter='").append(colorFilter).append('\'');
        sb.append(", imageTypeFiler='").append(imageTypeFiler).append('\'');
        sb.append(", siteFilter='").append(siteFilter).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
