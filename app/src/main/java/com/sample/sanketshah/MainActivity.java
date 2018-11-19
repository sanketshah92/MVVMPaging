package com.sample.sanketshah;

import android.os.Bundle;

import com.sample.sanketshah.coin.repository.CoinRepository;
import com.sample.sanketshah.coin.view.fragments.CoinListFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        Fragment currentFrag = navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else {
            if (currentFrag instanceof CoinListFragment) {
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onDestroy() {
        new CoinRepository(getApplication()).deleteCoin();
        super.onDestroy();
    }
}
