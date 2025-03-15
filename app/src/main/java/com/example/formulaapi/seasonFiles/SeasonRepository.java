package com.example.formulaapi.seasonFiles;

import com.example.formulaapi.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import io.reactivex.rxjava3.core.Observable;

public class SeasonRepository {
    private static SeasonRepository instance;
    private final ApiService apiService;

    // Caché en memoria para los datos
    private final HashMap<String, Season> seasonCache = new HashMap<>();
    private final List<Season> seasonListCache = new ArrayList<>();

    // Controladores de paginación
    private boolean isLastPage = false; // Indica si ya se obtuvieron todos los datos
    private int currentOffset = 0; // Desplazamiento actual
    private static final int LIMIT = 30; // Tamaño de página definido por la API

    // Constructor privado para el patrón Singleton
    private SeasonRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    // Obtener una instancia única del repositorio
    public static synchronized SeasonRepository getInstance(ApiService apiService) {
        if (instance == null) {
            instance = new SeasonRepository(apiService);
        }
        return instance;
    }

    /**
     * Obtener un piloto específico por su ID.
     *
     * Primero busca en la caché local y, si no se encuentra, realiza una llamada a la API.
     */
    public Observable<Season> getSeason(String seasonId) {
        if (seasonCache.containsKey(seasonId)) {
            return Observable.just(seasonCache.get(seasonId)); // Retorna el piloto desde la caché
        }

        return apiService.getSeason(seasonId)
                .map(response -> {
                    List<Season> seasons = response.getItems();
                    if (seasons != null && !seasons.isEmpty()) {
                        Season season = seasons.get(0); // Extrae el único piloto
                        seasonCache.put(seasonId, season); // Lo guarda en la caché
                        return season;
                    }
                    throw new NoSuchElementException("No se encontró el piloto con ID: " + seasonId);
                });
    }

    /**
     * Obtener la siguiente página de pilotos manejando paginación.
     */
    public Observable<List<Season>> getNextSeasonsPage() {
        if (isLastPage) {
            return Observable.just(new ArrayList<>()); // Devuelve una lista vacía si ya no hay más páginas
        }

        return apiService.getSeasons(currentOffset, LIMIT)
                .map(response -> {
                    List<Season> seasons = response.getItems();

                    if (seasons == null || seasons.isEmpty()) {
                        isLastPage = true;
                        return new ArrayList<>();
                    }

                    System.out.println("Equipos obtenidos en esta página: " + seasons.size());
                    System.out.println("Total en caché: " + seasonListCache.size());

                    for (Season season : seasons) {
                        if (!seasonCache.containsKey(season.getId())) {
                            seasonCache.put(season.getId(), season);
                            seasonListCache.add(season);
                        }
                    }

                    currentOffset += seasons.size(); // Incrementar el offset basado en la cantidad real obtenida
                    return seasons;
                });
    }

    /**
     * Obtener la lista completa de pilotos actualmente cargados.
     */
    public List<Season> getCachedSeasons() {
        return new ArrayList<>(seasonListCache);
    }

    /**
     * Vaciar la caché y reiniciar la paginación.
     */
    public void resetPagination() {
        seasonCache.clear();
        seasonListCache.clear();
        currentOffset = 0;
        isLastPage = false;
    }
}
