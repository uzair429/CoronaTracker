package com.example.coronatraker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class affectedcounties extends AppCompatActivity {

    EditText editText;
    ListView listView;
    SimpleArcLoader loader;
    public static List<countryModel> countrymodellist = new ArrayList<countryModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affectedcounties);
        editText = findViewById(R.id.edtsearch);
        listView = findViewById(R.id.list_view);
        loader = findViewById(R.id.loader);

        getSupportActionBar().setTitle("Affected Counties");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),specific_Activity.class).putExtra("position",position));

            }
        });
        fetchDate();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchDate() {
        String url = "https://corona.lmao.ninja/v2/countries/";
        loader.start();
        StringRequest request = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject =  jsonArray.getJSONObject(i);
                        String countryName = jsonObject.getString("country");
                        String cases = jsonObject.getString("cases");
                        String todayCases = jsonObject.getString("todayCases");
                        String deaths = jsonObject.getString("deaths");
                        String todaydeaths = jsonObject.getString("todayDeaths" );
                        String recovered = jsonObject.getString("recovered");
                        String active = jsonObject.getString( "active");
                        String critical = jsonObject.getString("critical");

                        JSONObject object = jsonObject.getJSONObject("countryInfo");
                        String flagurl = object.getString("flag");

                        countryModel cd = new countryModel(flagurl,countryName,cases,todayCases,deaths,todaydeaths,recovered,active,critical);
                        countrymodellist.add(cd);
                    }

                     CustomAdapter ca = new CustomAdapter(affectedcounties.this,countrymodellist);
                    listView.setAdapter(ca);
                    loader.stop();
                    loader.setVisibility(View.GONE);
                } catch (JSONException e) {
                    Toast.makeText(affectedcounties.this, "hello i am catch called ", Toast.LENGTH_SHORT).show();
                    loader.stop();
                    loader.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.stop();
                loader.setVisibility(View.GONE);

                Toast.makeText(affectedcounties.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}