package com.sample.sanketshah.coin.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sample.sanketshah.R;
import com.sample.sanketshah.appexceptions.NoConnectionException;
import com.sample.sanketshah.coin.data.CointEntity;
import com.sample.sanketshah.coin.view.util.BlurLayout;
import com.sample.sanketshah.coin.viewmodel.CoinCalculationViewModel;
import com.sample.sanketshah.network.Urls;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class CoinDetailFragment extends Fragment {

    private View view;
    private BlurLayout blurLayout;
    private AppCompatTextView txtCoinName;
    private AppCompatImageView imgCoinBig;
    private AppCompatTextView txtResult;
    private AppCompatButton btnCalculate;
    private AppCompatSpinner spCurrency, spQty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coindetail, container, false);
        blurLayout = view.findViewById(R.id.blurLayout);
        txtCoinName = view.findViewById(R.id.txtCoinName);
        imgCoinBig = view.findViewById(R.id.imgCoinBig);
        btnCalculate = view.findViewById(R.id.btnCalculate);
        txtResult = view.findViewById(R.id.txtResult);
        spCurrency = view.findViewById(R.id.spCurrency);
        spQty = view.findViewById(R.id.spQty);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CointEntity cointEntity = (CointEntity) getArguments().getSerializable("coin");
        Log.e("DATA:::", "->" + cointEntity.getCoinName());
        txtCoinName.setText(cointEntity.getCoinFullName());
        Picasso.get().load(Urls.IMAGEPREFIX + cointEntity.coinImageUrl).resize(680, 680).into(imgCoinBig);
        CoinCalculationViewModel coinCalculationViewModel = new CoinCalculationViewModel(getActivity().getApplication());
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    coinCalculationViewModel.calculate(cointEntity.getCoinName(), spCurrency.getSelectedItem().toString(), spQty.getSelectedItem().toString());

                } catch (NoConnectionException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        coinCalculationViewModel.calculatedTotal.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtResult.setText(s);
            }
        });
    }
}
