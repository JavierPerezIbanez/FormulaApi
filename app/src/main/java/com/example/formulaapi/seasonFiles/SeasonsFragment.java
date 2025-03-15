package com.example.formulaapi.seasonFiles;

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

public class SeasonsFragment extends Fragment {

    private SeasonRepository seasonRepository;
    private SeasonAdapter seasonAdapter;
    private boolean isLoading = false; // Controla la carga de datos
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seasons, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewSeasons);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        seasonAdapter = new SeasonAdapter(null);
        recyclerView.setAdapter(seasonAdapter);
        System.out.println("Adaptador configurado con el RecyclerView.");


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
        seasonRepository = SeasonRepository.getInstance(apiService);
        seasonRepository.resetPagination();
    }

    private void setupAdapterClickListener() {
        seasonAdapter.setOnItemClickListener(season -> {
            SeasonDetailFragment detailFragment = SeasonDetailFragment.newInstance(season.getId());
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void loadNextPage() {
        if (isLoading) return;

        isLoading = true;
        seasonRepository.getNextSeasonsPage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(seasons -> {
                    seasonAdapter.updateSeasons(seasonRepository.getCachedSeasons());
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
                if (layoutManager != null && layoutManager.findLastVisibleItemPosition() == seasonAdapter.getItemCount() - 1) {
                    loadNextPage();
                }
            }
        });
    }
}
