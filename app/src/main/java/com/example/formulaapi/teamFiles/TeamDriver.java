package com.example.formulaapi.teamFiles;

import com.example.formulaapi.driverFiles.Driver;
import com.google.gson.annotations.SerializedName;

public class TeamDriver extends Driver {
    @SerializedName("points")
    private int points;
    @SerializedName("position")
    private int position;
    @SerializedName("wins")
    private int wins;

    public int getPoints() {
        return points;
    }

    public int getPosition() {
        return position;
    }

    public int getWins() {
        return wins;
    }
}
