package epitech.m_rahman.epicture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FullScreenImageActivity extends AppCompatActivity {

    private TextView title, views;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);

        title = (TextView) findViewById(R.id.title);
        views = (TextView) findViewById(R.id.views);
        image = (ImageView) findViewById(R.id.image);

        Intent myIntent = getIntent(); // gets the previously created intent
        String image_link = myIntent.getStringExtra("image_link");
        String image_title = myIntent.getStringExtra("image_title");
        int image_views = myIntent.getIntExtra("image_views", 0);

        Picasso.get().load(image_link).into(image);
        if (image_title != null)
            title.setText(image_title);
        else
            title.setText("Aucun titre");
        views.setText(Integer.toString(image_views) + " vues");
    }
}
