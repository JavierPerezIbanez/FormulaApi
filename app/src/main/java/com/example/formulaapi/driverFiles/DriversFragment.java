package com.example.formulaapi.driverFiles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaapi.ApiService;
import com.example.formulaapi.R;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriversFragment extends Fragment {

    private DriverRepository driverRepository;
    private DriverAdapter driverAdapter;
    private boolean isLoading = false; // Controla la carga de datos
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drivers, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewDrivers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        driverAdapter = new DriverAdapter(null);
        recyclerView.setAdapter(driverAdapter);

        setupAdapterClickListener();
        setupScrollListener();

        initRepository();
        loadNextPage();

        return view;
    }

    private void initRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://f1api.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        driverRepository = DriverRepository.getInstance(apiService);
        driverRepository.resetPagination();
    }

    private void setupAdapterClickListener() {
        driverAdapter.setOnItemClickListener(driver -> {
            DriverDetailFragment detailFragment = DriverDetailFragment.newInstance(driver.getId());
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void loadNextPage() {
        if (isLoading) return;

        isLoading = true;
        driverRepository.getNextDriversPage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(drivers -> {
                    driverAdapter.updateDrivers(driverRepository.getCachedDrivers());
                    isLoading = false;
                }, error -> {
                    isLoading = false;
                });
    }

    private void setupScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastVisibleItemPosition() == driverAdapter.getItemCount() - 1) {
                    loadNextPage();
                }
            }
        });
    }
}
