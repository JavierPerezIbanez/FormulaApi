package com.example.formulaapi.circuits;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import com.example.formulaapi.ApiService;
import com.example.formulaapi.R;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.ArrayList;
import java.util.List;

public class CircuitsFragment extends Fragment {

    private RecyclerView recyclerView;
    private CircuitAdapter circuitAdapter;
    private ApiService apiService;
    private boolean isLoading = false;
    private int offset = 0;
    private final int limit = 30;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circuits, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        circuitAdapter = new CircuitAdapter(new ArrayList<>());
        recyclerView.setAdapter(circuitAdapter);

        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == circuitAdapter.getItemCount() - 1) {
                    loadCircuits(); // Cargar más circuitos cuando se llega al final
                    isLoading = true;
                }
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://f1api.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        loadCircuits();

        return view;
    }

    private void loadCircuits() {
        apiService.getCircuitsList(limit, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        circuitsResponse -> {
                            List<Circuit> circuits = circuitsResponse.getCircuits();
                            circuitAdapter.addCircuits(circuits);
                            offset += limit; // Incrementa el offset para la siguiente carga
                            isLoading = false;
                        },
                        error -> {
                            // Manejar el error
                            isLoading = false;
                        }
                );
    }
}
