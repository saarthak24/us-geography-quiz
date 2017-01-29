package com.app.saarthak.USGeographyQuiz;

/**
 * Created by Saarthak on 11/01/16.
 */
public class Score {
    private String mUsername;
    private double mValue;

    public Score()
    {
        mUsername = "";
        mValue = 0;
    }

    public Score(String username, double value)
    {
        mUsername = username;
        mValue = value;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public double getValue() {
        return mValue;
    }

    public void setValue(double value) {
        mValue = value;
    }
}
