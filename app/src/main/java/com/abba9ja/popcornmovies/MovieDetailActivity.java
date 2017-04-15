package com.abba9ja.popcornmovies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailActivity extends AppCompatActivity {

    private String mMoviePost,mtitle;
    //private ImageView ivPosterFor;
    //private TextView tvOverview, tvRealeseDate, tvRating;

    @BindView(R.id.ivPoster) ImageView ivPosterFor;
    @BindView(R.id.tvoverview) TextView tvOverview;
    @BindView(R.id.tvdate) TextView tvRealeseDate;
    @BindView(R.id.tvRating) TextView tvRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*ivPosterFor = (ImageView) findViewById(R.id.ivPoster);
        tvOverview = (TextView) findViewById(R.id.tvoverview);
        tvRealeseDate = (TextView) findViewById(R.id.tvdate);
        tvRating = (TextView) findViewById(R.id.tvRating);*/

        //this line shows back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {

                mMoviePost = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

                List<String> list = new ArrayList<String>();
                Pattern regex = Pattern.compile("\\((.*?)\\)");
                Matcher regexMatcher = regex.matcher(mMoviePost);
                String poster, title, date, overview, backdrop, rating;
                poster = title = date = overview = backdrop = rating = null;

                while (regexMatcher.find()) {
                    list.add(regexMatcher.group(1));
                }

                poster = (list.get(0));
                title = (list.get(1));
                date = (list.get(2));
                overview = (list.get(3));
                backdrop = (list.get(4));
                rating = (list.get(5));

                mtitle = title;

                Picasso.with(this).load(Uri.parse("http://image.tmdb.org/t/p/w300/" + backdrop))
                        .placeholder(R.drawable.ic_popcorn_placeholder)
                        .error(R.drawable.ic_no_poster_available)
                         .into(ivPosterFor);

                this.setTitle(title);
                tvOverview.setText(overview);
                tvRealeseDate.setText(date);
                tvRating.setText(rating);

            }
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setShareIntent();

            }
        });

    }
    private void setShareIntent() {
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(ivPosterFor);
        // Construct a ShareIntent with link to image
        Intent shareIntent = new Intent();
        // Construct a ShareIntent with link to image
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hea WhatsUp, Check Out this Movie: "+ mtitle + " ->Realese on:"
                +tvRealeseDate.getText());
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        // Launch share menu
        startActivity(Intent.createChooser(shareIntent, "Share Movies with Love ones"));
    }
    // Returns the URI path to the Bitmap displayed in  backdrop
    private Uri getLocalBitmapUri(ImageView poster) {
        Drawable drawable ;
        Bitmap bmp;
        drawable = poster.getDrawable();
        bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) poster.getDrawable()).getBitmap();
        } else {
            return null;
        }

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "Shared_Movie" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu_movie_detail, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
