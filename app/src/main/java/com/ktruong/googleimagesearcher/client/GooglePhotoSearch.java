package com.ktruong.googleimagesearcher.client;


import android.content.Context;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.ktruong.googleimagesearcher.R;
import com.ktruong.googleimagesearcher.activities.RequestActivity;
import com.ktruong.googleimagesearcher.adapter.PhotoSearchAdapter;
import com.ktruong.googleimagesearcher.converter.PhotoConverter;
import com.ktruong.googleimagesearcher.models.Photo;
import com.ktruong.googleimagesearcher.models.SearchPreference;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public class GooglePhotoSearch {

    private final String searchUrl;

    public GooglePhotoSearch() {
        this.searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0";
    }

    public void search(SearchQuery searchQuery, final PhotoSearchAdapter photoSearchAdapter, final SearchPreference searchPreference, final Context context) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        String filterQueryParam = buildFilterQueryParam(searchPreference);
        String url = searchUrl + "&q=" + searchQuery.getQuery() + "&rsz=" + searchQuery.getSize() + "&start=" + searchQuery.getStart() + filterQueryParam;

        Log.i("GooglePhotoSearch", url);
        asyncHttpClient.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("INFO", "result " + response.toString());
                try {
                    int responseStatus = response.getInt("responseStatus");
                    if (responseStatus == 200) {
                        JSONObject responseData = response.getJSONObject("responseData");

                        List<Photo> results = PhotoConverter.fromJson(responseData.getJSONArray("results"));
                        if (results != null && results.isEmpty()) {
                            Toast.makeText(context, context.getString(R.string.no_search_result), Toast.LENGTH_LONG);
                            photoSearchAdapter.addAll(results);
                        } else {
                            photoSearchAdapter.addAll(results); // add and notify
                        }
                    } else {
                        Log.w(this.getClass().getName(), "got response status code " + responseStatus);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(context, context.getString(R.string.no_network), Toast.LENGTH_LONG);
            }
        });
    }

    private String buildFilterQueryParam(SearchPreference searchPreference) {
        StringBuilder paramBuilder = new StringBuilder();

        if (searchPreference != null) {
            String colorFilter = searchPreference.getColorFilter();
            if (!SearchPreference.ANY.equals(colorFilter)) {
                paramBuilder.append("&imgcolor=").append(colorFilter.trim());
            }

            String imageSizeFilter = searchPreference.getImageSizeFilter();
            if (!SearchPreference.ANY.equals(imageSizeFilter)) {
                paramBuilder.append("&imgsz=").append(imageSizeFilter.trim());
            }

            String imageTypeFiler = searchPreference.getImageTypeFiler();
            if (!SearchPreference.ANY.equals(imageTypeFiler)) {
                paramBuilder.append("&imgtype=").append(imageTypeFiler);
            }

            String siteFilter = searchPreference.getSiteFilter();
            if (imageTypeFiler != null && !imageSizeFilter.trim().isEmpty()) {
                paramBuilder.append("&as_sitesearch=").append(siteFilter);
            }

        }

        return paramBuilder.toString();
    }
}
