package com.example.covidtrackerapi;

public class CovidData {
    //Data members
    private String name;
    private String ConfirmedCases;
    private String activeCases;
    private String deaths;
    private String recoveredCases;

    //Constructor
    public CovidData(String stateName, String confirmedCases, String activeCases, String deaths, String recoveredCases) {
        this.name = stateName;
        ConfirmedCases = confirmedCases;
        this.activeCases = activeCases;
        this.deaths = deaths;
        this.recoveredCases = recoveredCases;
    }

    public String getName() {
        return name;
    }

    public String getConfirmedCases() {
        return Refractor.format(ConfirmedCases);
    }

    public String getActiveCases() {
        return Refractor.format(activeCases);
    }

    public String getDeaths() {
        return Refractor.format(deaths);
    }

    public String getRecoveredCases() {
        return Refractor.format(recoveredCases);
    }
}
