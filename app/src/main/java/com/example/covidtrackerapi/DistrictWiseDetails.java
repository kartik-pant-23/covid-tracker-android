package com.example.covidtrackerapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DistrictWiseDetails extends AppCompatActivity {

    private RecyclerView districtRCV;
    private ArrayList<CovidData> districtsDataList = new ArrayList<>();
    private DataAdapter adapter;
    private TextView stateName, totalActive, totalRecovered, totalDeaths, textView1;

    private String mStateName, mTotalActive, mTotalRecovered, mTotalDeaths, mTotalConfirmed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_wise_details);

        stateName = findViewById(R.id.stateName);
        totalActive = findViewById(R.id.totalActive);
        totalRecovered = findViewById(R.id.totalRecovered);
        totalDeaths = findViewById(R.id.totalDeaths);
        textView1 = findViewById(R.id.textView1);
        districtRCV = findViewById(R.id.districtRCV);
        districtRCV.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = this.getIntent();
        mStateName = intent.getStringExtra("name");
        mTotalConfirmed = intent.getStringExtra("confirmed");
        mTotalRecovered = intent.getStringExtra("recovered");
        mTotalActive = intent.getStringExtra("active");
        mTotalDeaths = intent.getStringExtra("deaths");

        stateName.setText(mStateName);
        totalActive.setText(mTotalActive);
        totalRecovered.setText(mTotalRecovered);
        totalDeaths.setText(mTotalDeaths);
        textView1.setText(mTotalConfirmed);

        new loadData().execute();
    }

    public class loadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            ReaderClass reader = new ReaderClass(getResources().getString(R.string.district_wise));
            String data = reader.getData();
            try {
                JSONObject object = new JSONObject(data);
                //JSONArray districtData = (object.getJSONObject(mStateName)).getJSONArray("districtData");
                JSONObject districtData = (object.getJSONObject(mStateName)).getJSONObject("districtData");
                Iterator<String> names =  districtData.keys();
                while (names.hasNext()) {
                    String district = names.next();
                    JSONObject districtInfo = (JSONObject) districtData.get(district);
                    String active = districtInfo.getString("active");
                    String confirmed = districtInfo.getString("confirmed");
                    String recovered = districtInfo.getString("recovered");
                    String deaths = districtInfo.getString("deceased");
                    districtsDataList.add(new CovidData(district, confirmed, active, deaths, recovered));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            totalActive.setText(mTotalActive);
            totalDeaths.setText(mTotalDeaths);
            totalRecovered.setText(mTotalRecovered);
            textView1.setText(mTotalConfirmed);
            adapter = new DataAdapter(districtsDataList, DistrictWiseDetails.this, 1);
            districtRCV.setAdapter(adapter);
        }
    }
}