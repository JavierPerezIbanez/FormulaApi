package com.example.formulaapi.driverFiles;

import android.util.Log;

import com.example.formulaapi.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import io.reactivex.rxjava3.core.Observable;

public class DriverRepository {
    private static DriverRepository instance;
    private final ApiService apiService;

    // Caché en memoria para los datos
    private final HashMap<String, Driver> driverCache = new HashMap<>();
    private final List<Driver> driverListCache = new ArrayList<>();

    // Controladores de paginación
    private boolean isLastPage = false; // Indica si ya se obtuvieron todos los datos
    private int currentOffset = 0; // Desplazamiento actual
    private static final int LIMIT = 30; // Tamaño de página definido por la API

    // Constructor privado para el patrón Singleton
    private DriverRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    // Obtener una instancia única del repositorio
    public static synchronized DriverRepository getInstance(ApiService apiService) {
        if (instance == null) {
            instance = new DriverRepository(apiService);
        }
        return instance;
    }

    /**
     * Obtener una lista de pilotos para un equipo específico de 2025.
     */
    public Observable<List<Driver>> getTeamDrivers2025(String teamId) {
        return apiService.getTeamDrivers2025(teamId)
                .map(response -> {
                    List<Driver> drivers = new ArrayList<>();

                    // Extraer los objetos "Driver" desde los wrappers
                    for (TeamDriversResponse.DriverWrapper wrapper : response.getDrivers()) {
                        drivers.add(wrapper.getDriver());
                    }

                    // Guardar en la caché para accesos futuros
                    for (Driver driver : drivers) {
                        if (!driverCache.containsKey(driver.getId())) {
                            driverCache.put(driver.getId(), driver);
                            driverListCache.add(driver);
                        }
                    }

                    return drivers; // Devolver la lista de pilotos
                });
    }

    /**
     * Obtener un piloto específico por su ID.
     *
     * Primero busca en la caché local y, si no se encuentra, realiza una llamada a la API.
     */
    public Observable<Driver> getDriver(String driverId) {
        if (driverCache.containsKey(driverId)) {
            return Observable.just(driverCache.get(driverId)); // Retorna el piloto desde la caché
        }

        return apiService.getDriver(driverId)
                .map(response -> {
                    List<Driver> drivers = response.getItems();
                    if (drivers != null && !drivers.isEmpty()) {
                        Driver driver = drivers.get(0); // Extrae el único piloto
                        driverCache.put(driverId, driver); // Lo guarda en la caché
                        return driver;
                    }
                    throw new NoSuchElementException("No se encontró el piloto con ID: " + driverId);
                });
    }

    /**
     * Obtener la siguiente página de pilotos manejando paginación.
     */
    public Observable<List<Driver>> getNextDriversPage() {
        if (isLastPage) {
            return Observable.just(new ArrayList<>()); // Devuelve una lista vacía si ya no hay más páginas
        }

        return apiService.getDrivers(currentOffset, LIMIT)
                .map(response -> {
                    List<Driver> drivers = response.getItems();

                    if (drivers == null || drivers.isEmpty()) {
                        isLastPage = true;
                        return new ArrayList<>();
                    }

                    for (Driver driver : drivers) {
                        if (!driverCache.containsKey(driver.getId())) {
                            driverCache.put(driver.getId(), driver);
                            driverListCache.add(driver);
                        }
                    }

                    currentOffset += drivers.size(); // Incrementar el offset basado en la cantidad real obtenida
                    return drivers;
                });
    }

    /**
     * Obtener la lista completa de pilotos actualmente cargados.
     */
    public List<Driver> getCachedDrivers() {
        return new ArrayList<>(driverListCache);
    }

    /**
     * Vaciar la caché y reiniciar la paginación.
     */
    public void resetPagination() {
        driverCache.clear();
        driverListCache.clear();
        currentOffset = 0;
        isLastPage = false;
    }
}
