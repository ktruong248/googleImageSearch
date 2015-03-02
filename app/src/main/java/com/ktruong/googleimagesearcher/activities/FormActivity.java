package com.ktruong.googleimagesearcher.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.ktruong.googleimagesearcher.R;
import com.ktruong.googleimagesearcher.models.SearchPreference;


public class FormActivity extends ActionBarActivity {

    private Spinner imageSize;
    private SearchPreference searchPreference;
    private Spinner colorFilter;
    private Spinner imageSizeOption;
    private Spinner imageTypeOption;
    private EditText siteFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_activity);

        imageSize = (Spinner) findViewById(R.id.imageSizeOption);
        imageSizeOption = (Spinner)findViewById(R.id.imageSizeOption);
        colorFilter = (Spinner)findViewById(R.id.colorFilterOption);
        imageTypeOption = (Spinner)findViewById(R.id.imageTypeOption);
        siteFilter = (EditText) findViewById(R.id.siteFilterText);
        
        // child extract
        Intent intent = getIntent();
        searchPreference = (SearchPreference) intent.getSerializableExtra("searchPreference");
        if(searchPreference == null) {
            searchPreference = new SearchPreference();
        }else{
            ArrayAdapter<CharSequence> imageSizeAdapter= ArrayAdapter.createFromResource(this, R.array.image_size_arrays, android.R.layout.simple_spinner_item);
            int imageSizePosition = imageSizeAdapter.getPosition(searchPreference.getImageSizeFilter());
            imageSizeOption.setSelection(imageSizePosition);

            ArrayAdapter<CharSequence> colorFilterAdapter= ArrayAdapter.createFromResource(this, R.array.color_filter_arrays, android.R.layout.simple_spinner_item);
            int colorFilterPosition = colorFilterAdapter.getPosition(searchPreference.getColorFilter());
            colorFilter.setSelection(colorFilterPosition);

            ArrayAdapter<CharSequence> imageTypeFilterAdapter= ArrayAdapter.createFromResource(this, R.array.image_type_arrays, android.R.layout.simple_spinner_item);
            int imageTypeFilterPosition = imageTypeFilterAdapter.getPosition(searchPreference.getImageTypeFiler());
            imageTypeOption.setSelection(imageTypeFilterPosition);

            siteFilter.setText(searchPreference.getSiteFilter());
        }
   
         
        // Inflate the menu; this adds items to the action bar if it is present.
        Button submit = (Button) findViewById(R.id.saveSearchQuery);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Object selectedItem = imageSize.getSelectedItem();
                searchPreference.setImageSizeFilter(selectedItem.toString());
                searchPreference.setColorFilter(colorFilter.getSelectedItem().toString());
                searchPreference.setImageTypeFiler(imageTypeOption.getSelectedItem().toString());
                searchPreference.setSiteFilter(siteFilter.getText().toString());
                
                Intent responseData = new Intent();

                responseData.putExtra("searchPreference", searchPreference);

                setResult(RESULT_OK, responseData);
                // go back
                finish();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
