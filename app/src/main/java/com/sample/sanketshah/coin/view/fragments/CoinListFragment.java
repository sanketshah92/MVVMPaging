package com.sample.sanketshah.coin.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sample.sanketshah.R;
import com.sample.sanketshah.coin.data.CointEntity;
import com.sample.sanketshah.coin.view.adapter.CoinsAdapter;
import com.sample.sanketshah.coin.viewmodel.CoinViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CoinListFragment extends Fragment {

    private View view;
    private RecyclerView rvCoin;
    private CoinsAdapter coinsAdapter;
    private List<CointEntity> cointEntities;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coinlist, container, false);
        rvCoin = view.findViewById(R.id.rvCoin);
        progressBar = view.findViewById(R.id.progress);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvCoin.setLayoutManager(new LinearLayoutManager(getContext()));

        CoinViewModel coinViewModel = (CoinViewModel) getArguments().getParcelable("coinViewModel");

        cointEntities = new ArrayList<>();
        coinsAdapter = new CoinsAdapter();
        rvCoin.setAdapter(coinsAdapter);
        rvCoin.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);


        if (coinViewModel != null)

            coinViewModel.coins1.observe(this, cointEntities -> {
                Log.e(":::COINS", "" + cointEntities.size());
                progressBar.setVisibility(View.GONE);
                rvCoin.setVisibility(View.VISIBLE);
                coinsAdapter.submitList(cointEntities);

            });
    }
}
