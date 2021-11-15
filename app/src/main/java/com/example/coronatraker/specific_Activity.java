package com.example.coronatraker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class specific_Activity extends AppCompatActivity {

    TextView cases,todaycases,deaths,todaydeaths,recovered,active,critical;
    int positioncountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);
        Intent intent =  getIntent();
        positioncountry = intent.getIntExtra("position",7);
        getSupportActionBar().setTitle("Detail Of "+affectedcounties.countrymodellist.get(positioncountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        cases = findViewById(R.id.tvcases);
        todaycases = findViewById(R.id.tvtodaycases);
        deaths = findViewById(R.id.tvdeaths);
        todaydeaths = findViewById(R.id.tvtodaydeaths);
        recovered = findViewById(R.id.tvrecovered);
        active = findViewById(R.id.tvactive);
        critical = findViewById(R.id.tvcritical);

        cases.setText(affectedcounties.countrymodellist.get(positioncountry).getCases());
        todaycases.setText(affectedcounties.countrymodellist.get(positioncountry).getTodaycases());
        deaths.setText(affectedcounties.countrymodellist.get(positioncountry).getDeaths());
        todaydeaths.setText(affectedcounties.countrymodellist.get(positioncountry).getTodaydeaths());
        recovered.setText(affectedcounties.countrymodellist.get(positioncountry).getRecovered());
        active.setText(affectedcounties.countrymodellist.get(positioncountry).getActive());
        critical.setText(affectedcounties.countrymodellist.get(positioncountry).getCritical());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}