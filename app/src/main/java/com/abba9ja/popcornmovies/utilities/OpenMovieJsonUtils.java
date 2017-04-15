package com.abba9ja.popcornmovies.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Abba9ja on 4/4/2017.
 */

public final class OpenMovieJsonUtils {

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the Movie in detail.
     * <p/>
     * @param movieJsonStr JSON response from server
     *
     * @return Array of Strings describing movie data
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static String[] getSimpleMovieStringFromJson (Context context, String movieJsonStr)
            throws JSONException {


        /* Movies information. Each movie info is an element of the "results" array */
        final String OWM_RESULTS = "results";
        final String OWM_TITLE = "original_title";
        final String OWM_POSTER = "poster_path";
        final String OWM_RELEASE_DATE = "release_date";
        final String OWM_SYNOPSIS = "overview";
        final String OWM_RATING = "vote_average";
        final String OWM_ID = "id";
        final String OWM_BACKDROP = "backdrop_path";

        /* String array to hold each movie detail in String */
        String[] parsedMovieData = null;
        JSONObject movieJson = new JSONObject(movieJsonStr);

        JSONArray movieArray = movieJson.getJSONArray(OWM_RESULTS);

        parsedMovieData = new String[movieArray.length()];


        for (int i = 0; i < movieArray.length(); i++){

            String origin_title, poster_path, release_date, overview, backdrop, vote_average
                    , id;

            JSONObject eachMovie = movieArray.getJSONObject(i);
            origin_title = eachMovie.getString(OWM_TITLE);
            poster_path = eachMovie.getString(OWM_POSTER);
            release_date = eachMovie.getString(OWM_RELEASE_DATE);
            overview = eachMovie.getString(OWM_SYNOPSIS);
            backdrop = eachMovie.getString(OWM_BACKDROP);
            vote_average = eachMovie.getString(OWM_RATING);
            id = eachMovie.getString(OWM_ID);

            parsedMovieData[i] = "("+poster_path+")"+"("+origin_title+")"
                    +"("+release_date+")"+"("+overview+")"+"("+backdrop+")"+"("+vote_average+")";

        }

        return parsedMovieData;
    }

}
