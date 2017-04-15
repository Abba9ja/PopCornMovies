# PopCornMovies
Popular Movies Stage 1 A part of the Android Developer Nanodegree Program
This app will:
Upon launch, present the user with an grid arrangement of movie posters.
Allow your user to change sort order via a setting:
The sort order can be by most popular, or by top rated
Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
original title
movie poster image thumbnail
A plot synopsis (called overview in the api)
user rating (called vote_average in the api)
release date

I used the Image Library - Picasso that handle image loading
To be able to run this app you need to use the API from themoviedb.org.
If you don’t already have an account, you will need to create one in order to request an API Key. You will need to provide some personal information to complete the request. Once you submit your request, you should receive your key via email shortly after.
Once you obtain your key, you append it to your HTTP request as a URL parameter like so:
http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]
You will extract the movie id from this request. You will need this in subsequent requests.
