package com.example.formulaapi.circuitFiles;

import com.example.formulaapi.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import io.reactivex.rxjava3.core.Observable;

public class CircuitRepository {
    private static CircuitRepository instance;
    private final ApiService apiService;

    // Caché en memoria para los datos
    private final HashMap<String, Circuit> circuitCache = new HashMap<>();
    private final List<Circuit> circuitListCache = new ArrayList<>();

    // Controladores de paginación
    private boolean isLastPage = false; // Indica si ya se obtuvieron todos los datos
    private int currentOffset = 0; // Desplazamiento actual
    private static final int LIMIT = 30; // Tamaño de página definido por la API

    // Constructor privado para el patrón Singleton
    private CircuitRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    // Obtener una instancia única del repositorio
    public static synchronized CircuitRepository getInstance(ApiService apiService) {
        if (instance == null) {
            instance = new CircuitRepository(apiService);
        }
        return instance;
    }

    /**
     * Obtener un circuito específico por su ID.
     *
     * Primero busca en la caché local y, si no se encuentra, realiza una llamada a la API.
     */
    public Observable<Circuit> getCircuit(String circuitId) {
        if (circuitCache.containsKey(circuitId)) {
            return Observable.just(circuitCache.get(circuitId)); // Retorna el circuito desde la caché
        }

        return apiService.getCircuit(circuitId)
                .map(response -> {
                    List<Circuit> circuits = response.getItems();
                    if (circuits != null && !circuits.isEmpty()) {
                        Circuit circuit = circuits.get(0); // Extrae el único circuito
                        circuitCache.put(circuitId, circuit); // Lo guarda en la caché
                        return circuit;
                    }
                    throw new NoSuchElementException("No se encontró el circuito con ID: " + circuitId);
                });
    }

    /**
     * Obtener la siguiente página de circuitos manejando paginación.
     */
    public Observable<List<Circuit>> getNextCircuitsPage() {
        if (isLastPage) {
            return Observable.just(new ArrayList<>()); // Devuelve una lista vacía si ya no hay más páginas
        }

        return apiService.getCircuits(currentOffset, LIMIT)
                .map(response -> {
                    List<Circuit> circuits = response.getItems();

                    if (circuits == null || circuits.isEmpty()) {
                        isLastPage = true;
                        return new ArrayList<>();
                    }

                    for (Circuit circuit : circuits) {
                        if (!circuitCache.containsKey(circuit.getId())) {
                            circuitCache.put(circuit.getId(), circuit);
                            circuitListCache.add(circuit);
                        }
                    }

                currentOffset += circuits.size(); // Incrementar el offset basado en la cantidad real obtenida
                return circuits;
            });
    }
    /**
     * Obtener la lista completa de circuitos actualmente cargados.
     */
    public List<Circuit> getCachedCircuits() {
        return new ArrayList<>(circuitListCache);
    }

    /**
     * Vaciar la caché y reiniciar la paginación.
     */
    public void resetPagination() {
        circuitCache.clear();
        circuitListCache.clear();
        currentOffset = 0;
        isLastPage = false;
    }
}
