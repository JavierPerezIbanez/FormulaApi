package com.example.formulaapi.circuitFiles;

import com.example.formulaapi.baseFiles.BaseResponse;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Gestiona la respuesta de la API, que devuelve una lista de circuitos.
 */
public class CircuitResponse extends BaseResponse<Circuit> {
    @SerializedName("circuits")
    private List<Circuit> circuits;

    @Override
    public List<Circuit> getItems() {
        return circuits;
    }
}
