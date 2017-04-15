package com.abba9ja.popcornmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.*;

import com.abba9ja.popcornmovies.utilities.ColorUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by Abba9ja on 4/5/2017.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{


    private String[] mMovieData;

    private Context fcontext;
    private int viewHolderCount;


    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final MovieAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(String eachMovieData);
    }

    /**
     * Creates a MovieAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
        viewHolderCount = 0;

    }

    public Context getContext() {
        return fcontext;
    }

    /**
     * Cache of the children views for a movie list item.
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener{


        public final ImageView mPoster;

        public MovieAdapterViewHolder(View view){
            super(view);
            mPoster = (ImageView) view.findViewById(R.id.ivPoster);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String eachMovie = mMovieData[adapterPosition];
            mClickHandler.onClick(eachMovie);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new ForecastAdapterViewHolder that holds the View for each list item
     */

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        fcontext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup,
                shouldAttachToParentImmediately);

        int backgroundColorForViewHolder = ColorUtils
                .getViewHolderBackgroundColorFromInstance(context, viewHolderCount);
        view.setBackgroundColor(backgroundColorForViewHolder);
        Random random = new Random();
        viewHolderCount = random.nextInt(18);
        return new MovieAdapterViewHolder(view);

    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the movie
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param movieAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {

        String posterPath, originTitle, overview, release_date;
        posterPath=null;
        String eachMovie = mMovieData[position];

        //getting the movies details in ( ) using Pattern to compile and Matcher to detect each
        //data
        List<String> list = new ArrayList<String>();
        Pattern regex = Pattern.compile("\\((.*?)\\)");
        Matcher regexMatcher = regex.matcher(eachMovie);
        String movie_poster, movie_title, movie_realse_date,movie_overview, movie_backdrop,
                movie_user_rating;
        movie_poster = movie_title = movie_realse_date = movie_overview = movie_backdrop=
                movie_user_rating = null;

        while(regexMatcher.find()){
            list.add(regexMatcher.group(1));
        }

        movie_poster = (list.get(0));
        movie_title = (list.get(1));
        movie_realse_date = (list.get(2));
        movie_overview = (list.get(3));
        movie_backdrop = (list.get(4));
        movie_user_rating = (list.get(5));

        Picasso.with(getContext()).load(Uri.parse("http://image.tmdb.org/t/p/w300/"+movie_poster))
                            .placeholder(R.drawable.ic_popcorn_placeholder)
                            .error(R.drawable.ic_no_poster_available)
                            .into(movieAdapterViewHolder.mPoster);

    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        if(null == mMovieData) return 0;
        return mMovieData.length;
    }

    /**
     * This method is used to set the movie data on a MovieAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new MovieAdapter to display it.
     *
     * @param movieData The new weather data to be displayed.
     */
    public void setmMovieData(String[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }

    
}
