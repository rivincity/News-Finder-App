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

public class Fragment6 extends Fragment {
    EnterPreferences main;
    String userPageCount = "";
    public Fragment6() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView6 =  inflater.inflate(R.layout.activity_fragment6, container, false);
        EditText pagePreference = fragmentView6.findViewById(R.id.id_fragment6_page);
        Button skip6 = fragmentView6.findViewById(R.id.id_fragment6_skip);
        skip6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("skip6", "clicked");
                userPageCount = "";
                main.sendDataToNews();
            }
        });
        pagePreference.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userPageCount = s.toString();
                Log.d("userCategory", userPageCount);
            }
        });
        pagePreference.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == android.view.KeyEvent.ACTION_DOWN) && (keyCode == android.view.KeyEvent.KEYCODE_ENTER)){
                    main.savePageCount(Integer.parseInt(userPageCount));
                    main.sendDataToNews();
                }
                return false;
            }
        });
        return fragmentView6;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("context", String.valueOf(context));
        main = (EnterPreferences) context;
    }

}