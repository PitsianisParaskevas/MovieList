package com.example.movielist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.animation.AnimatableView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.ErrorListener;

public class MainActivity extends AppCompatActivity {

    private final static String URL = "https://jsonplaceholder.typicode.com/posts";
    private final static String URL_STRING = "";

    private final static String URL_EQ = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/1.0_hour.geojson";

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Log.d("Message: ", "Hellow World");

        queue = Volley.newRequestQueue(this);
//       getStringObject(URL_STRING);
        getJsonObject(URL_EQ);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i =0; i<response.length(); i++) {
                    try {
                        JSONObject movieObject = response.getJSONObject(i);
                        Log.d("Items: ", movieObject.getString("id") + "/" + "Title" + movieObject.getString("title"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

//                Log.d("Response: ", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", error.getMessage() );
            }
        });
        queue.add(arrayRequest);
    }

    private void getJsonObject(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Object: ", response.getString("type").toString());

                    JSONObject metadata = response.getJSONObject("metadata");
                    Log.d("Metadata", metadata.toString());
                    Log.d("MetadInfo", metadata.getString("title").toString());


                    /// jsonArray
                    JSONArray features = response.getJSONArray("features");

                   for(int i = 0; i < features.length(); i++) {
                       // Get objects
                       JSONObject propertiesObj = features.getJSONObject(i).getJSONObject("properties");
                       Log.d("Place: ", propertiesObj.getString("place"));

                   }



                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    public void getStringObject(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("My String: ", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);
    }

}