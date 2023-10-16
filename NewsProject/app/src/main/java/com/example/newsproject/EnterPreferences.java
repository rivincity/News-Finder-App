package com.example.newsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class EnterPreferences extends AppCompatActivity {
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private String country = "";
    public static final String COUNTRY_KEY = "country";
    private String query = "";
    public static final String QUERY_KEY = "query";
    private String category = "";
    public static final String CATEGORY_KEY = "category";
    private String domain = "";
    public static final String DOMAIN_KEY = "domain";
    private String language = "";
    public static final String LANGUAGE_KEY = "language";
    private int page;
    public static final String PAGE_KEY = "page";

    public static final String USERNAME_KEY = "username";

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_preferences);
        viewPager = findViewById(R.id.id_viewpager);
        tabLayout = findViewById(R.id.tab_layout);

        username = getIntent().getStringExtra(Login.intentKey);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        Fragment4 fragment4 = new Fragment4();
        Fragment5 fragment5 = new Fragment5();
        Fragment6 fragment6 = new Fragment6();
        fragmentTransaction.add(R.id.id_viewpager, fragment1);
        fragmentTransaction.add(R.id.id_viewpager, fragment2);
        fragmentTransaction.add(R.id.id_viewpager, fragment3);
        fragmentTransaction.add(R.id.id_viewpager, fragment4);
        fragmentTransaction.add(R.id.id_viewpager, fragment5);
        fragmentTransaction.add(R.id.id_viewpager, fragment6);
        fragmentTransaction.commit();


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.add(new Fragment1(), "Country");
        viewPagerAdapter.add(new Fragment2(), "Query");
        viewPagerAdapter.add(new Fragment3(), "Language");
        viewPagerAdapter.add(new Fragment4(), "Category");
        viewPagerAdapter.add(new Fragment5(), "Domain");
        viewPagerAdapter.add(new Fragment6(), "Page Count");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    public void saveCountry (String str) {
        Log.d("country", str);
        country = str;

    }

    public void saveQuery (String str) {
        Log.d("query", str);
        query = str;

    }

    public void saveCategory (String str) {
        Log.d("category", str);
        category = str;
    }

    public void saveDomain (String str) {
        Log.d("domain", str);
        domain = str;
    }

    public void saveLanguage (String str) {
        Log.d("language", language);
        language = str;
    }

    public void savePageCount (int i) {
        Log.d("page count", String.valueOf(page));
        page = i;
    }


    public void sendDataToNews () {
        Intent intentToLoadNews = new Intent(EnterPreferences.this, NewsScreen.class);
        intentToLoadNews.putExtra(COUNTRY_KEY, country);
        intentToLoadNews.putExtra(QUERY_KEY, query);
        intentToLoadNews.putExtra(CATEGORY_KEY, category);
        intentToLoadNews.putExtra(DOMAIN_KEY, domain);
        intentToLoadNews.putExtra(LANGUAGE_KEY, language);
        intentToLoadNews.putExtra(PAGE_KEY, page);
        intentToLoadNews.putExtra(USERNAME_KEY, username);
        startActivity(intentToLoadNews);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

}