package com.abba9ja.popcornmovies.utilities;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Abba9ja on 4/4/2017.
 */

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();



    /**
     * Builds the URL used to query Github.
     *
     * @param themoviedbSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the the movie db server.
     */
    public static URL buildUrl(String themoviedbSearchQuery) {
        String sortedUrl = "";
        //here we check for the boolean sort oder and determine which URL query are we to use
        if (themoviedbSearchQuery.equals("true")){
            sortedUrl = "http://api.themoviedb.org/3/3/movie/top_rated?api_key=[YOUR_API_KEY]";
        }else {
            sortedUrl = "http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]";
        }
        Uri builtUri = Uri.parse(sortedUrl).buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    @Nullable
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }



}
