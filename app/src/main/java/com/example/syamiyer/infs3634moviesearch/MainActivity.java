package com.example.syamiyer.infs3634moviesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText searchField;
    TextView title;
    TextView year;
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchField = findViewById(R.id.searchField);
        title = findViewById(R.id.title);
        year = findViewById(R.id.year);
        poster = findViewById(R.id.poster);

    }

    public void search(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String searchTerm = searchField.getText().toString();
        String convertedSearch = searchTerm.replace(" ", "+");
        String url ="http://www.omdbapi.com/?apikey=f18479e9&t=" + convertedSearch;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.isEmpty()) {
                            parseJSON(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    void parseJSON(String response) {
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(response);
            title.setText(jsonObj.get("Title").toString());
            year.setText(jsonObj.get("Year").toString());
            Glide.with(this).load(jsonObj.get("Poster").toString()).into(poster);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
