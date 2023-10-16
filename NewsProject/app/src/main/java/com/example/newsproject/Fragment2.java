package com.example.newsproject;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {
    EnterPreferences main;
    String userQuery = "";
    public Fragment2() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView2 =  inflater.inflate(R.layout.activity_fragment2, container, false);
        EditText queryPreference = fragmentView2.findViewById(R.id.id_fragment2_query);
        Button skip2 = fragmentView2.findViewById(R.id.id_fragment2_skip);
        skip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("skip2", "clicked");
                userQuery = "";
                main.getViewPager().setCurrentItem(main.getViewPager().getCurrentItem() +1, true);
            }
        });
        queryPreference.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userQuery = s.toString();
                Log.d("userQuery", userQuery);
            }
        });
        queryPreference.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == android.view.KeyEvent.ACTION_DOWN) && (keyCode == android.view.KeyEvent.KEYCODE_ENTER)){
                    main.getViewPager().setCurrentItem(main.getViewPager().getCurrentItem() +1, true);
                }
                main.saveQuery(userQuery);
                return false;
            }
        });
        return fragmentView2;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("context", String.valueOf(context));
        main = (EnterPreferences) context;
    }

}