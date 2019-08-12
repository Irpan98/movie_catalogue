package id.itborneo.moviecatalogue.data.network

import id.itborneo.moviecatalogue.data.response.MoviesResponse
import id.itborneo.moviecatalogue.utils.API_URL_LAST
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {
    @GET("3/discover/movie/$API_URL_LAST")
    fun getMovie(): Call<MoviesResponse>
//
//    @GET("3/discover/tv/$API_URL_LAST")
//    fun getTvShow(): Call<MoviesResponse>
//


}