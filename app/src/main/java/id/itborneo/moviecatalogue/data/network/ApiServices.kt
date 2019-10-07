package id.itborneo.moviecatalogue.data.network

import id.itborneo.moviecatalogue.BuildConfig.API_KEY
import id.itborneo.moviecatalogue.data.response.MoviesResponse
import id.itborneo.moviecatalogue.data.response.TvShowResponse
import id.itborneo.moviecatalogue.utils.API_SEARCH_LAST
import id.itborneo.moviecatalogue.utils.API_URL_LAST
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("3/discover/movie/$API_URL_LAST")
    fun getMovie(): Call<MoviesResponse>
    
    @GET("3/discover/tv/$API_URL_LAST")
    fun getTvShow(): Call<TvShowResponse>
    
    @GET("3/search/movie$API_SEARCH_LAST")
    fun searchMovie(
        @Query("query") title:String
    ): Call<MoviesResponse>
    
    @GET("3/search/tv$API_SEARCH_LAST")
    fun searchTv(
        @Query("query") title:String
    ): Call<TvShowResponse>
    
    
    @GET("http://api.themoviedb.org/3/discover/movie?api_key=$API_KEY")
    fun movieReleaseToday(
        @Query("&primary_release_date.gte") date1: String,
        @Query("&primary_release_date.lte=")date2:String
    ):Call<MoviesResponse>
}