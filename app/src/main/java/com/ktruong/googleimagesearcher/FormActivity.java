package com.ktruong.googleimagesearcher;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class FormActivity extends ActionBarActivity {

    private EditText ageText;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_activityy);
        
        // child extract
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
   
        // Inflate the menu; this adds items to the action bar if it is present.
//        Button submit = (Button) findViewById(R.id.submit);
//
//        ageText = (EditText) findViewById(R.id.searchText);
//        if(user != null && user.getAge() > 0) {
//            ageText.setText(String.valueOf(user.getAge()));
//        }
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                user.setAge(Integer.parseInt(ageText.getText().toString()));
//
//                Intent responseData = new Intent();
//
//                responseData.putExtra("user", user);
//
//                setResult(RESULT_OK, responseData);
//                // go back
//                finish();
//            }
//        });

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
