package com.sample.sanketshah.coin.view.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.sanketshah.R;
import com.sample.sanketshah.coin.data.CointEntity;
import com.sample.sanketshah.network.Urls;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CoinsAdapter extends PagedListAdapter<CointEntity, CoinsAdapter.ViewHolder>/*RecyclerView.Adapter<CoinsAdapter.ViewHolder>*/ {


    public CoinsAdapter() {
        super(DIFF_CALLBACK);
    }


    public static DiffUtil.ItemCallback<CointEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CointEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull CointEntity oldCoupon,
                                               @NonNull CointEntity newCoupon) {
                    return oldCoupon.getCoinName().equalsIgnoreCase(newCoupon.getCoinName());
                }

                @Override
                public boolean areContentsTheSame(@NonNull CointEntity oldCoupon,
                                                  @NonNull CointEntity newCoupon) {
                    return oldCoupon.equals(newCoupon);
                }
            };


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_coin_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CointEntity cointEntity = getItem(position);
        if (cointEntity != null) {
            holder.txtCoinName.setText(cointEntity.getCoinName());
            holder.txtCoinFullName.setText(cointEntity.getCoinFullName());
            holder.txtCoinAlgorithm.setText(cointEntity.getCoinAlgorithm());
            Picasso.get().load(Urls.IMAGEPREFIX + cointEntity.coinImageUrl).resize(380, 380).into(holder.imgCoin);
            holder.cardCoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("coin", cointEntity);
                    Navigation.findNavController(v).navigate(R.id.action_cointDetail, bundle);
                }
            });
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView txtCoinFullName, txtCoinName, txtCoinAlgorithm;
        AppCompatImageView imgCoin;
        CardView cardCoin;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCoinName = itemView.findViewById(R.id.txtCoinName);
            txtCoinFullName = itemView.findViewById(R.id.txtCoinFullName);
            txtCoinAlgorithm = itemView.findViewById(R.id.txtCoinAlgo);
            imgCoin = itemView.findViewById(R.id.imgCoin);
            cardCoin = itemView.findViewById(R.id.cardCoin);
        }

    }
}
