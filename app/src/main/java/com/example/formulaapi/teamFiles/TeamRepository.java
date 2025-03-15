package com.example.formulaapi.teamFiles;

import com.example.formulaapi.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import io.reactivex.rxjava3.core.Observable;

public class TeamRepository {
    private static TeamRepository instance;
    private final ApiService apiService;

    // Caché en memoria para los datos
    private final HashMap<String, Team> teamCache = new HashMap<>();
    private final List<Team> teamListCache = new ArrayList<>();

    // Controladores de paginación
    private boolean isLastPage = false; // Indica si ya se obtuvieron todos los datos
    private int currentOffset = 0; // Desplazamiento actual
    private static final int LIMIT = 30; // Tamaño de página definido por la API

    // Constructor privado para el patrón Singleton
    private TeamRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    // Obtener una instancia única del repositorio
    public static synchronized TeamRepository getInstance(ApiService apiService) {
        if (instance == null) {
            instance = new TeamRepository(apiService);
        }
        return instance;
    }

    /**
     * Obtener la lista completa de equipos de la temporada 2025 desde la API.
     */
    public Observable<List<Team>> getTeams2025() {
        return apiService.getTeams2025()
                .map(response -> {
                    List<Team> teams = response.getItems(); // Recolecta los equipos
                    return teams;
                });
    }

    /**
     * Obtener un equipo específico por su ID.
     *
     * Primero busca en la caché local y, si no se encuentra, realiza una llamada a la API.
     */
    public Observable<Team> getTeam(String teamId) {
        if (teamCache.containsKey(teamId)) {
            return Observable.just(teamCache.get(teamId)); // Retorna el equipo desde la caché
        }

        return apiService.getTeam(teamId)
                .map(response -> {
                    List<Team> teams = response.getItems();
                    if (teams != null && !teams.isEmpty()) {
                        Team team = teams.get(0); // Extrae el único equipo
                        teamCache.put(teamId, team); // Lo guarda en la caché
                        return team;
                    }
                    throw new NoSuchElementException("No se encontró el equipo con ID: " + teamId);
                });
    }

    /**
     * Obtener la siguiente página de equipos manejando paginación.
     */
    public Observable<List<Team>> getNextTeamsPage() {
        if (isLastPage) {
            return Observable.just(new ArrayList<>()); // Devuelve una lista vacía si ya no hay más páginas
        }

        return apiService.getTeams(currentOffset, LIMIT)
                .map(response -> {
                    List<Team> teams = response.getItems();

                    if (teams == null || teams.isEmpty()) {
                        isLastPage = true;
                        return new ArrayList<>();
                    }

                    for (Team team : teams) {
                        if (!teamCache.containsKey(team.getId())) {
                            teamCache.put(team.getId(), team);
                            teamListCache.add(team);
                        }
                    }

                    currentOffset += teams.size(); // Incrementar el offset basado en la cantidad real obtenida
                    return teams;
                });
    }

    /**
     * Obtener la lista completa de equipos actualmente cargados.
     */
    public List<Team> getCachedTeams() {
        return new ArrayList<>(teamListCache);
    }

    /**
     * Vaciar la caché y reiniciar la paginación.
     */
    public void resetPagination() {
        teamCache.clear();
        teamListCache.clear();
        currentOffset = 0;
        isLastPage = false;
    }
}
