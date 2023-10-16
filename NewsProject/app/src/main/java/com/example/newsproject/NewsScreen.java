package com.example.newsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class NewsScreen extends AppCompatActivity {
    TextView welcomeMessage;
    ImageButton viewLikes;
    ImageButton viewFavorites;
    Button startOver;

    String country = "";
    String q = "";
    String category = "";
    String language = "";
    String domain = "";
    int page = 0;
    String urlCountry = "";
    String urlQ = "";
    String urlCategory = "";
    String urlLanguage = "";
    String urlDomain = "";
    String urlPage = "";
    String username;
    String boldUsername;
    int heartClickCount = 0;
    int starClickCount = 0;

    ArrayList<News> entries;
    ArrayList<News> savedLikedArticles;
    ListView listView;

    String title;
    String link;
    String description;
    String pubDate;
    Integer totalResults = 0;
    CustomAdapter customAdapter;
    CustomAdapter likedCustomAdapter;
    CustomAdapter savedCustomAdapter;

    public static final String COUNT_KEY = "count";
    public static final String ERROR_KEY = "error";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_screen);

        savedLikedArticles = new ArrayList<>();
        entries = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Liked Articles");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                savedLikedArticles.clear();
                Object newsObj = new Object();

                HashMap newsMap = null;

                for (DataSnapshot shot : snapshot.getChildren()) {

                    try {
                        String savedLinks;
                        String savedTitles;
                        String savedDescriptions;
                        String savedPubDates;
                        newsMap = (HashMap) shot.getValue(newsObj.getClass());
                        savedLinks = String.valueOf(newsMap.get("link"));
                        savedDescriptions = String.valueOf(newsMap.get("description"));
                        savedTitles = String.valueOf(newsMap.get("title"));
                        savedPubDates = String.valueOf(newsMap.get("pubDate"));
                        Log.d("liked links", String.valueOf(newsMap.get("link")));
                        Log.d("liked description", String.valueOf(newsMap.get("description")));
                        Log.d("liked title", String.valueOf(newsMap.get("title")));
                        Log.d("liked pubDate", String.valueOf(newsMap.get("pubDate")));
                        News savedNews = new News(savedTitles, savedDescriptions, savedLinks, savedPubDates);
                        savedLikedArticles.add(savedNews);

                    } catch (Exception ex) {
                        continue;
                    }
                }
                Log.d("size", String.valueOf(savedLikedArticles.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        welcomeMessage = findViewById(R.id.id_news_welcome);
        listView = findViewById(R.id.id_news_listView);
        viewLikes = findViewById(R.id.id_news_viewLikes);
        viewFavorites = findViewById(R.id.id_news_favorite);
        startOver = findViewById(R.id.id_news_goBack);
        viewFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starClickCount++;
                if(starClickCount%2!=0)
                {
                    getListView().setAdapter(savedCustomAdapter);
                    viewFavorites.setImageResource(R.drawable.greystar);
                }
                else
                {
                    getListView().setAdapter(customAdapter);
                    viewFavorites.setImageResource(R.drawable.yellowstar);
                }
            }
        });
        startOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToReenterParameters = new Intent(NewsScreen.this, EnterPreferences.class);
                startActivity(intentToReenterParameters);
            }
        });

        username = getIntent().getStringExtra(EnterPreferences.USERNAME_KEY);
        country = getIntent().getStringExtra(EnterPreferences.COUNTRY_KEY);
        q = getIntent().getStringExtra(EnterPreferences.QUERY_KEY);
        category = getIntent().getStringExtra(EnterPreferences.CATEGORY_KEY);
        language = getIntent().getStringExtra(EnterPreferences.LANGUAGE_KEY);
        domain = getIntent().getStringExtra(EnterPreferences.DOMAIN_KEY);
        page = getIntent().getIntExtra(EnterPreferences.PAGE_KEY, page);

        if (country.length() > 0)
            urlCountry = "&country=" + country;
        if (q.length() > 0)
            urlQ = "&q=" + q;
        if (category.length() > 0)
            urlCategory = "&category=" + category;
        if (language.length() > 0)
            urlLanguage = "&language=" + language;
        if (domain.length() > 0)
            urlDomain = "&domain=" + domain;
        if (page > 0)
            urlPage = "&page=" + page;
        Log.d("urlCountry", urlCountry);
        Log.d("urlQ", urlQ);
        Log.d("urlCategory", urlCategory);
        Log.d("urlLanguage", urlLanguage);
        Log.d("urlDomain", urlDomain);
        Log.d("urlPage", urlPage);

        new NewsScreen.AsynchTaskDownloader().execute();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        customAdapter = new CustomAdapter(NewsScreen.this, NewsScreen.this, R.layout.activity_custom_adapter, entries);
        likedCustomAdapter = new CustomAdapter(NewsScreen.this, NewsScreen.this, R.layout.activity_custom_adapter, customAdapter.getLikedArticles());
        savedCustomAdapter = new CustomAdapter(NewsScreen.this, NewsScreen.this, R.layout.activity_custom_adapter, savedLikedArticles);
        getListView().setAdapter(customAdapter);
        boldUsername = "<b>" + username + "</b>";
        viewLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartClickCount++;
                if(heartClickCount %2 != 0)
                {
                    getListView().setAdapter(likedCustomAdapter);
                    likedCustomAdapter.setLikedArticles(savedLikedArticles);
                    viewLikes.setImageResource(R.drawable.grayheart);
                    uploadList(welcomeMessage);
                }
                else
                {
                    likedCustomAdapter.setLikedArticles(savedLikedArticles);
                    getListView().setAdapter(customAdapter);
                    viewLikes.setImageResource(R.drawable.redheart);
                }
            }
        });
    }

    public ListView getListView () {
        return listView;
    }


    public void uploadList(View v) {
        myRef.setValue(customAdapter.getLikedArticles()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               // if(task.isSuccessful())
                    //Toast.makeText(getApplicationContext(), "list is uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class AsynchTaskDownloader extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            welcomeMessage.setText(Html.fromHtml("Here are <b>" + totalResults + "</b> news articles matching your preferences:",Html.FROM_HTML_MODE_COMPACT));
        }
        @Override
        protected Void doInBackground(Void... strings) {
            String inputLine = "";
            int errorCode;
            try {

                String url1 = "https://newsdata.io/api/1/news?apikey=pub_80814d6278e4c73ad0536301697efbe16914" + urlCountry + urlQ + urlCategory + urlLanguage + urlDomain + urlPage;
                Log.d("url", url1);
                URL myUrl = new URL(url1);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                Log.d("connection", String.valueOf(connection.getResponseCode()));
                errorCode = connection.getResponseCode();
                if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                {
                    Intent intentToReenterParameters = new Intent(NewsScreen.this, EnterPreferences.class);
                    intentToReenterParameters.putExtra(ERROR_KEY, errorCode);
                    startActivity(intentToReenterParameters);
                    //Toast.makeText(getApplicationContext(), "Your search returned no results due to " + errorCode, Toast.LENGTH_SHORT).show();
                }
                InputStream response = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response));
                String aa;
                aa = reader.readLine();
                while (aa != null) {
                    inputLine += aa;
                    aa = reader.readLine();
                }
                Log.d("TAG", inputLine);
                try {
                    JSONObject news = new JSONObject(inputLine);
                    JSONArray results = news.getJSONArray("results");
                    totalResults = results.length();
                    Log.d("results", String.valueOf(results.length()));
                    for(int i = 0; i < totalResults; i++)
                    {
                        title = results.getJSONObject(i).getString("title");
                        link = results.getJSONObject(i).getString("link");
                        description = results.getJSONObject(i).getString("description");
                        pubDate = results.getJSONObject(i).getString("pubDate");
                        entries.add(new News(title, description, link, pubDate));
                        Log.d("index", String.valueOf(i));
                        Log.d("title", entries.get(i).getTitle());
                        Log.d("link", entries.get(i).getLink());
                        Log.d("description", entries.get(i).getDescription());
                    }
                    Log.d("entry size", String.valueOf(entries.size()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}