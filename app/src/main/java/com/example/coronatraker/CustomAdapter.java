package com.example.coronatraker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<countryModel> {
    private Context context;
    private List<countryModel> countryModelList;


    public CustomAdapter(Context context, List<countryModel> countryModelList) {
        super(context, R.layout.list_custum_item,countryModelList);
        this.context = context;
        this.countryModelList = countryModelList;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custum_item,null ,true);
            TextView tvcountryname = view.findViewById(R.id.tvcountryname);
            ImageView imageView = view .findViewById(R.id.imageflag);
            tvcountryname.setText(countryModelList.get(position).getCountry());
            Glide.with(context).load(countryModelList.get(position).getFlag()).into(imageView);

        return view;
    }
}
