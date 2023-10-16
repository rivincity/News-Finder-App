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

public class Fragment4 extends Fragment implements AdapterView.OnItemSelectedListener{
    EnterPreferences main;
    String userCategory = "";
    public Fragment4() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView4 =  inflater.inflate(R.layout.activity_fragment4, container, false);
        Button skip4 = fragmentView4.findViewById(R.id.id_fragment4_skip);
        Spinner categoryChoice = fragmentView4.findViewById(R.id.id_fragment4_spinner);
        categoryChoice.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryChoice.setAdapter(adapter);
        skip4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("skip4", "clicked");
                userCategory = "";
                main.saveCategory(userCategory);
                main.getViewPager().setCurrentItem(main.getViewPager().getCurrentItem() +1, true);
            }
        });
        return fragmentView4;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("context", String.valueOf(context));
        main = (EnterPreferences) context;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        userCategory = String.valueOf(parent.getItemAtPosition(position));
        main.saveCategory(userCategory);
        if(position!=0)
            main.getViewPager().setCurrentItem(main.getViewPager().getCurrentItem() +1, true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}