package com.example.formulaapi.circuits;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CircuitsResponse {
    @SerializedName("circuits")
    private List<Circuit> circuits;

    public List<Circuit> getCircuits() {
        return circuits;
    }
}
