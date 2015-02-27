package com.ktruong.googleimagesearcher.activities;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.ktruong.googleimagesearcher.client.GooglePhotoSearch;
import com.ktruong.googleimagesearcher.client.SearchQuery;
import com.ktruong.googleimagesearcher.converter.PhotoConverter;
import com.ktruong.googleimagesearcher.adapter.PhotoSearchAdapter;
import com.ktruong.googleimagesearcher.R;
import com.ktruong.googleimagesearcher.listener.EndlessScrollListener;
import com.ktruong.googleimagesearcher.models.Photo;
import com.ktruong.googleimagesearcher.models.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


//public class RequestActivity extends ActionBarActivity implements SearchView.OnQueryTextListener  {
public class RequestActivity extends ActionBarActivity  {

    private List<Photo> photos;

    public static final int FORM_REQUEST_CODE = 20;
    private User user;
    private PhotoSearchAdapter photoSearchAdapter;
    private SearchView searchView;
    private String searchQuery;
    private GooglePhotoSearch googlePhotoSearch;

    /**
     *      * never create object outside of onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        googlePhotoSearch = new GooglePhotoSearch();
        
        photos = new ArrayList<>();

        photoSearchAdapter = new PhotoSearchAdapter(this, photos);

        final StaggeredGridView resultView = (StaggeredGridView) findViewById(R.id.resultView);
        resultView.setAdapter(photoSearchAdapter);
        resultView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //launch second actifity
                //create intent
                //get image result
                // since anonhmous need to get more context
                Photo photo = photos.get(position); /// get the photo at the clicked item
                
                Intent intent = new Intent(RequestActivity.this, FullScreenActivity.class);
                intent.putExtra("photo", photo);  /// todo Parcelable

                startActivity(intent);
            }
        });

//        resultView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(!hasFocus){
//                    hideKeyboard();
//                }
//            }
//
//            private void hideKeyboard() {
//                InputMethodManager imm = (InputMethodManager)RequestActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(resultView.getWindowToken(), 0);
//            }
//        });

        resultView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(totalItemsCount, searchQuery);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
       
        
        
        user = new User();
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_request, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                photos.clear();
                searchQuery = query;
                Log.i("INFO", "search query " + query);
                googlePhotoSearch.search(new SearchQuery(query,0), photoSearchAdapter);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Log.i("INFO", "text change " + newText);
                return false;
            }
        });
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.miRequest) {
            // INTENTS
            // 1. construct the intent
            // 2. pass the bundle (query string)
            // 3. execute the intent
//            getIntent().getSerializableExtra()
            Intent intent = new Intent(this, FormActivity.class);
            intent.putExtra("user", user);
            
//            startActivity(intent); // fire and forget
            startActivityForResult(intent, FORM_REQUEST_CODE); // fire expect result back
        }else if(item.getItemId() == R.id.action_search) {
           searchView.setIconified(false);
            return true;
        }

//        return super.onOptionsItemSelected(item);
        return false;
    }


    /**
     * * 
     * @param requestCode parent send code
     * @param resultCode child code
     * @param data response data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FORM_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                User userResponse = (User)data.getSerializableExtra("user");
                user.setAge(userResponse.getAge());
                int age = userResponse.getAge();
                if(age > 21 ) {
                    Toast.makeText(this, "Click " + age, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Blah " + age, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset, String query) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        Log.i("INFO", "offset " + offset);
        googlePhotoSearch.search(new SearchQuery(query, offset), photoSearchAdapter);
    }
}
