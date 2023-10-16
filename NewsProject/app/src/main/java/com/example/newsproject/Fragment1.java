package com.example.newsproject;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment implements AdapterView.OnItemSelectedListener {
    EnterPreferences main;
    String userCountry = "";
    public Fragment1() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView =  inflater.inflate(R.layout.activity_fragment1, container, false);
        Button skip1 = fragmentView.findViewById(R.id.id_fragment1_skip);
        Spinner countryChoices = fragmentView.findViewById(R.id.id_fragment1_spinner);
        countryChoices.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.countries_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryChoices.setAdapter(adapter);
        skip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("skip1", "clicked");
                userCountry = "";
                main.saveCountry(userCountry);
                main.getViewPager().setCurrentItem(main.getViewPager().getCurrentItem() +1, true);
            }
        });
        return fragmentView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("context", String.valueOf(context));
        main = (EnterPreferences) context;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        userCountry = String.valueOf(parent.getItemAtPosition(position));
        main.saveCountry(userCountry);
        if(position!=0)
            main.getViewPager().setCurrentItem(main.getViewPager().getCurrentItem() +1, true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}