package com.merp.randomquotes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "PREM_DEBUG";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
    }

    public void quotesClickMenu(View view) {
        startActivity(new Intent(this, RandomQuoteActivity.class));
    }

    public void listOfMemesClickMenu(View view) {
        startActivity(new Intent(this,RandomMemeActivity.class));
    }



    public void logMe(String comment){
        Log.i(TAG,comment);
    }

}
