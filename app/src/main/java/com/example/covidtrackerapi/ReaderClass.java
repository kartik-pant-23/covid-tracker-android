package com.example.covidtrackerapi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReaderClass {

    private String data;
    private String url;

    public ReaderClass(String url){
        this.url = url;
    }

    public String getData(){
        try {
            URL url = new URL(this.url);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.connect();
            InputStream in = urlConn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();
            while(line != null){
                builder.append(line).append("\n");
                line = reader.readLine();
            }
            data = builder.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }

}
