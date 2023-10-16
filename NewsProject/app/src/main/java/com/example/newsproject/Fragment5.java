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

public class Fragment5 extends Fragment {
    EnterPreferences main;
    String userDomain = "";
    public Fragment5() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView5 =  inflater.inflate(R.layout.activity_fragment5, container, false);
        EditText domainPreference = fragmentView5.findViewById(R.id.id_fragment5_domain);
        Button skip5 = fragmentView5.findViewById(R.id.id_fragment5_skip);
        skip5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("skip5", "clicked");
                userDomain = "";
                main.saveDomain(userDomain);
                main.getViewPager().setCurrentItem(main.getViewPager().getCurrentItem() +1, true);
            }
        });
        domainPreference.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userDomain = s.toString();
                Log.d("userDomain", userDomain);
            }
        });
        domainPreference.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == android.view.KeyEvent.ACTION_DOWN) && (keyCode == android.view.KeyEvent.KEYCODE_ENTER)){
                    main.getViewPager().setCurrentItem(main.getViewPager().getCurrentItem() +1, true);
                }
                main.saveDomain(userDomain);
                return false;
            }
        });
        return fragmentView5;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("context", String.valueOf(context));
        main = (EnterPreferences) context;
    }

}