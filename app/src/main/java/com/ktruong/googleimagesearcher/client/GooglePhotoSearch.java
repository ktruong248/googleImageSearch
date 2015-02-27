package com.ktruong.googleimagesearcher.client;


import android.util.Log;
import android.widget.Adapter;

import com.ktruong.googleimagesearcher.adapter.PhotoSearchAdapter;
import com.ktruong.googleimagesearcher.converter.PhotoConverter;
import com.ktruong.googleimagesearcher.models.Photo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GooglePhotoSearch {

    private final String searchUrl;

    public GooglePhotoSearch() {
        this.searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0";
    }
    
    public void search(SearchQuery searchQuery, final PhotoSearchAdapter photoSearchAdapter) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        String url = searchUrl + "&q=" + searchQuery.getQuery() + "&rsz="+searchQuery.getSize() + "&start=" + searchQuery.getStart();
        Log.i("GooglePhotoSearch", url);
        asyncHttpClient.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("INFO", "result " + response.toString());
                try {
                    JSONObject responseData = response.getJSONObject("responseData");

                    List<Photo> results = PhotoConverter.fromJson(responseData.getJSONArray("results"));
                    photoSearchAdapter.addAll(results); // add and notify
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // TODO
            }
        });        
    }
}
