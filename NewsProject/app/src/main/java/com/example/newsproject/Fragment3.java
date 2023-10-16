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

public class Fragment3 extends Fragment implements AdapterView.OnItemSelectedListener{
    EnterPreferences main;
    String userLanguage = "";
    public Fragment3() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView3 =  inflater.inflate(R.layout.activity_fragment3, container, false);
        Button skip3 = fragmentView3.findViewById(R.id.id_fragment3_skip);
        Spinner languageChoices = fragmentView3.findViewById(R.id.id_fragment3_spinner);
        languageChoices.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageChoices.setAdapter(adapter);
        skip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("skip3", "clicked");
                userLanguage = "";
                main.saveLanguage(userLanguage);
                main.getViewPager().setCurrentItem(main.getViewPager().getCurrentItem() +1, true);
            }
        });
        return fragmentView3;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("context", String.valueOf(context));
        main = (EnterPreferences) context;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        userLanguage = String.valueOf(parent.getItemAtPosition(position));
        main.saveLanguage(userLanguage);
        if(position!=0)
            main.getViewPager().setCurrentItem(main.getViewPager().getCurrentItem() +1, true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}