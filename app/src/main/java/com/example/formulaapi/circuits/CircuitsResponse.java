package com.example.formulaapi.circuits;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Gestiona la respuesta de la API, que devuelve una lista de circuitos.
 */
public class CircuitsResponse {
    @SerializedName("circuits")
    private List<Circuit> circuits;

    public List<Circuit> getCircuits() {
        return circuits;
    }
}
