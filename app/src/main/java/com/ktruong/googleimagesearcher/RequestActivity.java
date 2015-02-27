package com.ktruong.googleimagesearcher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
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


    /**
     *      * never create object outside of onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        photos = new ArrayList<>();

        photoSearchAdapter = new PhotoSearchAdapter(this, photos);

        final GridView resultView = (GridView) findViewById(R.id.resultView);
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

       
        
        
        user = new User();
    }

//    @Override
//    public boolean onQueryTextSubmit(String s) {
//        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
//        return true;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String s) {
//        Log.i("INFO", "text change " + s);
//        return false;
//    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_request, menu);
        getMenuInflater().inflate(R.menu.search_request, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                photos.clear();
                Log.i("INFO", "search query " + query);

                String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
                
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.get(searchUrl, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i("INFO", "result " + response.toString());
                        try {
                            JSONObject responseData = response.getJSONObject("responseData");

                            List<Photo> results = PhotoConverter.fromJson(responseData.getJSONArray("results"));
//                            photos.addAll(results);
                            photoSearchAdapter.addAll(results); // add and notify
//                            photoSearchAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("INFO", photos.toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        for (int i = 0; i < 5; i++) {
//                            InstagramPhoto instagramPhoto = new InstagramPhoto();
//                            instagramPhoto.setCaptionFromUserName("caption-user" + i);
//                            instagramPhoto.setCaption("some long caption text " + i);
//                            instagramPhoto.setUserFullName("my long name " + i);
//                            instagramPhoto.setName("user" + i);
//
//
//                            photos.add(instagramPhoto);
//                        }
//                        instagramPhotoAdapter.notifyDataSetChanged();
                    }
                });
                // do some search and send back data to the list view ?
                photoSearchAdapter.notifyDataSetChanged();


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("INFO", "text change " + newText);
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
}
