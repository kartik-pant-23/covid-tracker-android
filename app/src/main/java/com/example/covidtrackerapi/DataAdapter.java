package com.example.covidtrackerapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter{

    private ArrayList<CovidData> covidData;
    private Context context;
    private int viewType;

    public DataAdapter(ArrayList<CovidData> covidData, Context context, int viewType){
        this.covidData = covidData;
        this.context = context;
        this.viewType = viewType;
    }

    public static class StatesItem extends RecyclerView.ViewHolder{
        private ConstraintLayout itemTile;
        private TextView stateName;
        public StatesItem(@NonNull View itemView) {
            super(itemView);
            itemTile = itemView.findViewById(R.id.itemTile);
            stateName = itemView.findViewById(R.id.stateName);
        }
    }

    public static class DistrictsItem extends RecyclerView.ViewHolder{
        private TextView districtName;
        private TextView confirmed;
        private TextView active;
        private TextView recovered;
        private TextView deaths;
        public DistrictsItem(@NonNull View itemView) {
            super(itemView);
            districtName = itemView.findViewById(R.id.districtName);
            confirmed = itemView.findViewById(R.id.confirmed);
            active = itemView.findViewById(R.id.active);
            recovered = itemView.findViewById(R.id.recovered);
            deaths = itemView.findViewById(R.id.deaths);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_rcv, null);
            return new StatesItem(itemView);
        }
        else{
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_district, null);
            return new DistrictsItem(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(viewType == 0){
            //binds states items
            StatesItem item = (StatesItem) holder;
            item.stateName.setText(covidData.get(position).getName());
            item.itemTile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DistrictWiseDetails.class);
                    intent.putExtra("name", covidData.get(position).getName());
                    intent.putExtra("confirmed", covidData.get(position).getConfirmedCases());
                    intent.putExtra("active", covidData.get(position).getActiveCases());
                    intent.putExtra("recovered", covidData.get(position).getRecoveredCases());
                    intent.putExtra("deaths", covidData.get(position).getDeaths());
                    context.startActivity(intent);
                }
            });
        }else {
            //binds district items
            DistrictsItem item = (DistrictsItem) holder;
            item.districtName.setText(covidData.get(position).getName());
            item.confirmed.setText(covidData.get(position).getConfirmedCases());
            item.active.setText(covidData.get(position).getActiveCases());
            item.recovered.setText(covidData.get(position).getRecoveredCases());
            item.deaths.setText(covidData.get(position).getDeaths());
        }
    }

    @Override
    public int getItemCount() {
        return covidData.size();
    }
}
