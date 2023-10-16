package com.example.newsproject;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<News> {
    private final NewsScreen newsScreen;
    Context main;
    List<News> mylist;
    int counter = 0;
    private List<News> likedArticles = new ArrayList<>();

    public CustomAdapter(NewsScreen newsScreen1, @NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
        this.newsScreen = newsScreen1;
        main = context;
        mylist = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) main.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_custom_adapter, null);
        TextView link = view.findViewById(R.id.id_adapter_link);
        TextView description = view.findViewById(R.id.id_adapter_description);
        TextView pubDate = view.findViewById(R.id.id_adapter_pubDate);
        ImageButton likeButton = view.findViewById(R.id.id_adapter_likeButton);
        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        String hyperlink = "<a href='" + mylist.get(position).getLink().trim() +  "'>" + mylist.get(position).getTitle().trim() + "</a>";
        link.setText(Html.fromHtml(hyperlink, Html.FROM_HTML_MODE_COMPACT));
        if(mylist.get(position).getDescription()!= null && mylist.get(position).getDescription().equalsIgnoreCase("null"))
            description.setText("");
        else
            description.setText(mylist.get(position).getDescription().trim());
        if(mylist.get(position).getDatePublished()!= null && mylist.get(position).getDatePublished().equalsIgnoreCase("null"))
            pubDate.setText("");
        else
            pubDate.setText(mylist.get(position).getDatePublished().trim());

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(CustomAdapter.this, "You liked this", Toast.LENGTH_SHORT).show();
                counter++;
                likedArticles.add(mylist.get(position));
                likeButton.setEnabled(false);
            }
        });
        newsScreen.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        return view;
    }

    public List<News> getLikedArticles() {
        return likedArticles;
    }

    public void setLikedArticles(List<News> list) {
        likedArticles = list;
    }

}