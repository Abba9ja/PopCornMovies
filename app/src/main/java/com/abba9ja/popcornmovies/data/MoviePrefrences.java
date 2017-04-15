package com.abba9ja.popcornmovies.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.abba9ja.popcornmovies.R;

/**
 * Created by Abba9ja on 4/4/2017.
 */

public class MoviePrefrences {

    /*
    * The order in which to sort the movie
    * */
    public static boolean isHighRated(Context context) {
        //Return true if the user's preference for Sort order is High Rated,
        // false otherwise
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String keyForSort = context.getString(R.string.pref_sort_key);
        String defaultSort = context.getString(R.string.pref_sort_rating);
        String preferredSort = prefs.getString(keyForSort,defaultSort);
        String highRated = context.getString(R.string.pref_sort_rating);
        boolean userPrefersHighRated;
        if(highRated.equals(preferredSort)){
            userPrefersHighRated = true;
        }else {
            userPrefersHighRated = false;
        }

        return userPrefersHighRated;

    }

}
