package com.example.formulaapi.circuitFiles;

import com.example.formulaapi.baseFiles.BaseResponse;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Gestiona la respuesta de la API, que devuelve una lista de circuitos.
 */
public class CircuitResponse<T> extends BaseResponse<T> {
    @SerializedName("circuits")
    private List<T> circuits; // Para listas de circuitos

    @SerializedName("circuit")
    private List<T> circuit; // Para consultas de un único circuito

    @SerializedName("limit")
    private int limit;

    @SerializedName("offset")
    private int offset;

    @SerializedName("total")
    private int total;

    public List<T> getItems() {
        return circuits != null ? circuits : circuit;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getTotal() {
        return total;
    }
}
