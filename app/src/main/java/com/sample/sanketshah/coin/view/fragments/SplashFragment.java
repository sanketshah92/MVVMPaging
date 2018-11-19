package com.sample.sanketshah.coin.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.sanketshah.R;
import com.sample.sanketshah.coin.viewmodel.CoinViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class SplashFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_splash, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            CoinViewModel coinViewModel = new CoinViewModel(getActivity().getApplication());
            //ViewModelProviders.of(this, new CoinViewModel.CoinViewModelFactory(getActivity().getApplication())).get(CoinViewModel.class);
            coinViewModel.callCoinList();


            coinViewModel.coins1.observe(this, cointEntities -> {
                Log.e(":->", "" + cointEntities.size());
                /*if (!cointEntities.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("coinViewModel", coinViewModel);
                    Navigation.findNavController(view).navigate(R.id.action_coinList, bundle);
                }*/
                Bundle bundle = new Bundle();
                bundle.putParcelable("coinViewModel", coinViewModel);
                Navigation.findNavController(view).navigate(R.id.action_coinList, bundle);
            });

        }

    }
}
