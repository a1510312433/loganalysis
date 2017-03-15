package me.com.loganalysis;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by caobin on 2017/3/15.
 */

public class AnalysisResult implements Serializable{
    ArrayList<String> TimeSpan;
    ArrayList<String> TimeSpan1s;
    ArrayList<String> TimeSpan2s;
    ArrayList<String> TimeSpan5s;
    ArrayList<String> Fails;
    String results;

    public ArrayList<String> getTimeSpan2s() {
        return TimeSpan2s;
    }

    public void setTimeSpan2s(ArrayList<String> timeSpan2s) {
        TimeSpan2s = timeSpan2s;
    }

    public ArrayList<String> getTimeSpan5s() {
        return TimeSpan5s;
    }

    public void setTimeSpan5s(ArrayList<String> timeSpan5s) {
        TimeSpan5s = timeSpan5s;
    }

    public ArrayList<String> getFails() {
        return Fails;
    }

    public void setFails(ArrayList<String> fails) {
        Fails = fails;
    }

    public ArrayList<String> getTimeSpan1s() {
        return TimeSpan1s;
    }

    public void setTimeSpan1s(ArrayList<String> timeSpan1s) {
        TimeSpan1s = timeSpan1s;
    }

    public ArrayList<String> getTimeSpan() {
        return TimeSpan;
    }

    public void setTimeSpan(ArrayList<String> timeSpan) {
        TimeSpan = timeSpan;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
