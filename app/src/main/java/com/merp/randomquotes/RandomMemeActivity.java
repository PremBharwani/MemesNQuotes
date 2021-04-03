package com.merp.randomquotes;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;


public class RandomMemeActivity extends AppCompatActivity {

    public static String PREM_TAG = "PREM_IS_LOGGING";
    public String memeUrlToLoad = "https://i.redd.it/yykt3r9zsex11.png";
    URL memeApiUrl;
    HttpsURLConnection memeApiConnection;
    ImageView memeImageView;
    Button  shareButton;

    @Override
    protected void onStart() {
        super.onStart();
        logMe("On start new meme initialise");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String memeApiUrl = "https://meme-api.herokuapp.com/gimme";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, memeApiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String imageToLoadUrl = jsonObject.getString("url");
                    logMe("image url obtained : "+imageToLoadUrl);
                    Picasso.get().load(imageToLoadUrl).into(memeImageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                logMe(error.getMessage());
//                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_meme_layout);

        shareButton = findViewById(R.id.shareMemeButton);
        memeImageView = findViewById(R.id.memeImageView);

        displayImageUsingUrl();



        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //share the image here

                logMe("share meme button pressed");

                View content = findViewById(R.id.memeImageView);
                content.setDrawingCacheEnabled(true);

                Bitmap bitmap = content.getDrawingCache();
                File root = Environment.getExternalStorageDirectory();
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File cachePath = cw.getDir("imageDir",MODE_PRIVATE);
                File mypath = new File(cachePath,"temp.jpg");
                //File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
                try {
                    cachePath.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(mypath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    logMe("Exception while saving the image : "+e.getMessage());
                    e.printStackTrace();
                }


                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mypath));
                startActivity(Intent.createChooser(share,"Share via"));





        }
        });
    }

    void logMe(String comment) {
        Log.i(PREM_TAG, comment);
    }

    void displayImageUsingUrl() {
        Picasso.get().load(memeUrlToLoad).into(memeImageView);
    }

    public void nextMemeButtonV2Clicked(View view) {
        logMe("next meme v2 clicked");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String memeApiUrl = "https://meme-api.herokuapp.com/gimme";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, memeApiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String imageToLoadUrl = jsonObject.getString("url");
                    logMe("image url obtained : "+imageToLoadUrl);
                    Picasso.get().load(imageToLoadUrl).into(memeImageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                logMe(error.getMessage());
//                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}
