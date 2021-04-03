package com.merp.randomquotes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ListOfMemes extends AppCompatActivity {

    private static final String TAG = "PREM_DEBUG";


    private ArrayList<String> mNames = new ArrayList<>();

    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_memes);

        initialiseArrayLists();

        initBitmaps();

    }

    private void initBitmaps() {
        //update the names and urls

        Log.i(TAG, "initBitmaps: inside");

//            mImageUrls.add("https://i.redd.it/n9ch58s4c6o51.jpg");
//            mNames.add("1");
        tryAddDynamically();
    }

    private void initRecyclerView() {

        Log.i(TAG, "initRecyclerView: inside");
        RecyclerView recyclerView = findViewById(R.id.memesListRecyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void tryAddDynamically() {
        Log.i(TAG, "tryAddDynamically: inside tryAddDynamically");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://meme-api.herokuapp.com/gimme/4";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    int count = response.getInt("count");
//                    Log.i(TAG, "onResponse: count of response from api "+count);
                    JSONArray jsonArray = response.getJSONArray("memes");
                    for (int i = 0; i < 4; i++) {
                        JSONObject elementFromJsonArray = jsonArray.getJSONObject(i);
                        mImageUrls.set(i, elementFromJsonArray.getString("url"));
                        mNames.set(i, elementFromJsonArray.getString("title"));
                        Log.i(TAG, "onResponse: url response :" + elementFromJsonArray.getString("url"));
                        initRecyclerView();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: " + error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);

    }


    public void refreshButtonListMemes(View view) {
        initBitmaps();
    }

    public void initialiseArrayLists() {
        mNames.add("just for the sake of adding 4");
        mNames.add("just for the sake of adding 4");
        mNames.add("just for the sake of adding 4");
        mNames.add("just for the sake of adding 4");
        mImageUrls.add("just for the sake of adding 4");
        mImageUrls.add("just for the sake of adding 4");
        mImageUrls.add("just for the sake of adding 4");
        mImageUrls.add("just for the sake of adding 4");
    }
}
