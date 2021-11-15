package com.example.coronatraker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    PieChart pieChart;
    ScrollView scrollView ;
    SimpleArcLoader loader;
    TextView tvcases,tvtotaldeath,tvactive,tvrecovered,tvcritical,tvtotalcases,tvtotalrecoverd,tvaffectedcountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvcases= findViewById(R.id.tvcases);
        tvactive= findViewById(R.id.tvactive);
        tvrecovered= findViewById(R.id.tvrecovered);
        tvcritical= findViewById(R.id.tvcritical);
        tvtotalcases= findViewById(R.id.tvtotalcases);
        tvtotaldeath= findViewById(R.id.tvtotaldeaths);
        tvtotalrecoverd= findViewById(R.id.tvtotalrecover);
        tvaffectedcountries= findViewById(R.id.tvaffectedcounties);

        pieChart =findViewById(R.id.piechart);
        scrollView =findViewById(R.id.scrollstats);
        loader =findViewById(R.id.loader);
        fetchDate();

    }

    private void fetchDate() {
        String url = "https://corona.lmao.ninja/v2/all/";
        loader.start();
        StringRequest request = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    tvcases.setText(jsonObject.getString("cases"));
                    tvactive.setText(jsonObject.getString("active"));
                    tvrecovered.setText(jsonObject.getString("recovered"));
                    tvcritical.setText(jsonObject.getString("critical"));
                    //need to change totalcases to toddaycases
                    tvtotalcases.setText(jsonObject.getString("todayCases"));
                    tvtotaldeath.setText(jsonObject.getString("deaths"));
                    //need to change totalrecovered to toddaydeath
                    tvtotalrecoverd.setText(jsonObject.getString("recovered"));
                    tvaffectedcountries.setText(jsonObject.getString("affectedCountries"));
                    pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvcases.getText().toString()),Color.parseColor("#FF03DAC5")));
                    pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvrecovered.getText().toString()),Color.parseColor("#17EF17")));
                    pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvtotaldeath.getText().toString()),Color.parseColor("#FF2C00")));
                    pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvactive.getText().toString()),Color.parseColor("#6c5231")));
                    pieChart.startAnimation();
                    loader.stop();
                    loader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    loader.stop();
                    loader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.stop();
                loader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void TrackCountries(View view) {
        Intent intent = new Intent(MainActivity.this,affectedcounties.class);
        startActivity(intent);
    }
}