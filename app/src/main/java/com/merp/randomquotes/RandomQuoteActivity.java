package com.merp.randomquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class RandomQuoteActivity extends AppCompatActivity {


        public static String PREM_TAG = "PREM_IS_LOGGING";
    Button shareButton;
    public TextView quoteTextView;
    public String quoteFromApi;
    URL apiUrl = null;
    HttpsURLConnection apiConnection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_quote_layout);


        shareButton = (Button) findViewById(R.id.shareButton);
        quoteTextView = (TextView) findViewById(R.id.quoteTextView);







    }

    @Override
    protected void onStart() {
        super.onStart();
        logMe("Onstart Time");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiUrl = "https://api.quotable.io/random";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String quoteFromApiV2 = jsonObject.getString("content");
                    logMe("THE respose from the sv :"+quoteFromApiV2);
//                    Toast.makeText(getApplicationContext(),quoteFromApiV2, Toast.LENGTH_LONG).show();
                    quoteTextView.setText(quoteFromApiV2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                logMe(error.getMessage());
            }
        });
        requestQueue.add(stringRequest);
    }

    void logMe(String comment){
        Log.i(PREM_TAG,comment);
    }


    public void shareButtonClicked(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,quoteTextView.getText());
        startActivity(Intent.createChooser(shareIntent,"Share Using:"));
    }

    public void nextButtonV2Clicked(View view) {

        logMe("next v2 clicked");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiUrl = "https://api.quotable.io/random";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String quoteFromApiV2 = jsonObject.getString("content");
                    logMe("THE respose from the sv :"+quoteFromApiV2);
//                    Toast.makeText(getApplicationContext(),quoteFromApiV2, Toast.LENGTH_LONG).show();
                    quoteTextView.setText(quoteFromApiV2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                logMe(error.getMessage());
            }
        });
        requestQueue.add(stringRequest);

    }
}