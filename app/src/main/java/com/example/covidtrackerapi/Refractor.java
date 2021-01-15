package com.example.covidtrackerapi;

import androidx.annotation.NonNull;

public class Refractor {

    public static String format(@NonNull String string){
        StringBuilder modified = new StringBuilder();
        for(int i=0; i<string.length(); i++){
            if(i%3==0 && i!=0)
                modified.insert(0, string.charAt((string.length()-1-i)) + ",");
            else
                modified.insert(0, string.charAt((string.length()-1-i)));
        }
        return modified.toString();
    }

}
