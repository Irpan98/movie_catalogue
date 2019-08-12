//package id.itborneo.moviecatalogue.ui.main.fragment.movie
//
//import android.arch.lifecycle.LiveData
//import android.arch.lifecycle.MutableLiveData
//import android.arch.lifecycle.Observer
//import android.arch.lifecycle.ViewModel
//import android.util.Log
//import id.itborneo.moviecatalogue.ui.main.model.Movie
//
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//import id.itborneo.moviecatalogue.data.network.ApiClient
//import id.itborneo.moviecatalogue.data.response.MoviesResponse
//import id.itborneo.moviecatalogue.ui.main.fragment.movie.MovieContract.Presenter
//import id.itborneo.moviecatalogue.utils.mapper.getMovieMapper
//import java.util.ArrayList
//
//
//class MoviePresenter : Presenter , ViewModel(){
//
//    private val listMovieTv = MutableLiveData<ArrayList<Movie>>()
//
//
//    var listItems = ArrayList<Movie>()
//    private var mView : MovieContract.View? =null
//
//    override fun setMovie() {
//
//        mView?.showMovie(listMovieTv)
//
//    }
//
//
//    override fun onAttach(view: MovieContract.View) {
//        mView = view
//    }
//
//
//    override fun getMovie() {
//
//        ApiClient.create().getMovie().enqueue(object: Callback<MoviesResponse>{
//            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
//                Log.d("MoviePresenter", "onresponse called")
//            }
//
//            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
//                val movie = response.body() as MoviesResponse
//
//                val movieResult = movie.results
//                Log.d("MoviePresenter","movie result $movieResult")
//
//
//                listItems = getMovieMapper(movieResult) as ArrayList<Movie>
//                listMovieTv.postValue(listItems)
//
//
//            }
//
//        })
//
//
////        return listMovieTv
//    }
//
//    val listMovie: LiveData<ArrayList<Movie>>
//        get() {
//            return listMovieTv
//
//        }
//
//
//}

package id.itborneo.moviecatalogue.ui.main.fragment.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import id.itborneo.moviecatalogue.ui.main.model.Movie

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import id.itborneo.moviecatalogue.data.network.ApiClient
import id.itborneo.moviecatalogue.data.response.MoviesResponse
import id.itborneo.moviecatalogue.ui.main.fragment.movie.MovieContract.View
import id.itborneo.moviecatalogue.ui.main.fragment.movie.MovieContract.Presenter
import id.itborneo.moviecatalogue.utils.mapper.getMovieMapper
import java.util.ArrayList


class MoviePresenter : Presenter, ViewModel() {

    private val listMovieTv = MutableLiveData<ArrayList<Movie>>()

    var listItems = ArrayList<Movie>()

    override fun getMovie(): LiveData<ArrayList<Movie>>  {
        return listMovieTv

    }



    override fun setMovie() {
        ApiClient.create().getMovie().enqueue(object: Callback<MoviesResponse>{
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.d("MoviePresenter", "error $t")
            }

            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                val movie = response.body() as MoviesResponse

                val movieResult = movie.results
                Log.d("MoviePresenter","movie result $movieResult")

                listItems = getMovieMapper(movieResult) as ArrayList<Movie>
                Log.d("MoviePresenter","list item called $listItems")

                listMovieTv.postValue(listItems)
            }
        })

    }



    private var mView : View? =null

    override fun onAttach(view: View) {
        mView = view
    }


    class MovieViewModel: ViewModel() {


        fun setMovieTv(){



        }


    }

}