package com.example.covidtrackerapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Ref;
import java.util.ArrayList;

public class StateWiseDetails extends AppCompatActivity {

    private RecyclerView homeRCV;
    private ArrayList<CovidData> statesDataList = new ArrayList<>();
    private DataAdapter adapter;
    private TextView totalActive, totalRecovered, totalDeaths, textView1;

    private String mTotalActive, mTotalRecovered, mTotalDeaths, mTotalConfirmed;
    RelativeLayout loadScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_wise_details);

        totalActive = findViewById(R.id.totalActive);
        totalRecovered = findViewById(R.id.totalRecovered);
        totalDeaths = findViewById(R.id.totalDeaths);
        textView1 = findViewById(R.id.textView1);
        loadScreen = findViewById(R.id.loadScreen);
        homeRCV = findViewById(R.id.homeRCV);
        homeRCV.setLayoutManager(new LinearLayoutManager(this));

        new loadData().execute();
    }

    public class loadData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            ReaderClass reader = new ReaderClass(getResources().getString(R.string.state_wise));
            String data = reader.getData();
            try {
                JSONObject object = new JSONObject(data);
                JSONArray actualData = object.getJSONArray("statewise");
                for(int i=0; i<actualData.length(); i++){
                    JSONObject stateInfo = (JSONObject) actualData.get(i);
                    String active = stateInfo.getString("active");
                    String confirmed = stateInfo.getString("confirmed");
                    String recovered = stateInfo.getString("recovered");
                    String deaths = stateInfo.getString("deaths");
                    String state = stateInfo.getString("state");
                    if(state.equals("Total")){
                        mTotalActive = Refractor.format(active);
                        mTotalRecovered = Refractor.format(recovered);
                        mTotalDeaths = Refractor.format(deaths);
                        mTotalConfirmed = Refractor.format(confirmed);
                    }
                    else{
                        statesDataList.add(new CovidData(state, confirmed, active, deaths, recovered));
                    }
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
            adapter = new DataAdapter(statesDataList, StateWiseDetails.this, 0);
            homeRCV.setAdapter(adapter);
            loadScreen.setVisibility(View.GONE);
        }
    }
}