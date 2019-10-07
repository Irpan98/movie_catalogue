package id.itborneo.moviecatalogue.ui.main.fragment.movie


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.itborneo.moviecatalogue.data.network.ApiClient
import id.itborneo.moviecatalogue.data.response.MoviesResponse
import id.itborneo.moviecatalogue.ui.main.fragment.movie.MovieContract.Presenter
import id.itborneo.moviecatalogue.ui.model.MovieTv
import id.itborneo.moviecatalogue.utils.mapper.getMovieMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MoviePresenter : Presenter, ViewModel() {
    
    
    private val listMovieTv = MutableLiveData<ArrayList<MovieTv>>()
    
    
    var listItems = ArrayList<MovieTv>()
    private var mView: MovieContract.View? = null
    
    override fun setMovie(): LiveData<ArrayList<MovieTv>> {
        return listMovieTv
    }
    
    
    override fun onAttach(view: MovieContract.View) {
        mView = view
    }
    
    
    override fun getMovie() {
        
        ApiClient.create().getMovie().enqueue(object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.d("MoviePresenter", "onfailure")
            }
            
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                val movie = response.body() as MoviesResponse
                mView?.showLoading(false)
                val movieResult = movie.results
                Log.d("MoviePresenter", "movie result $movieResult")
                listItems = getMovieMapper(movieResult) as ArrayList<MovieTv>
                listMovieTv.postValue(listItems)
            }
        })
    }
    
    override fun searchMovie(title: String) {
        ApiClient.create().searchMovie(title)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                    val movie = response.body() as MoviesResponse
                    
                    val movieResult = movie.results
                    Log.d("MoviePresenter", "movie result $movieResult")
                    listItems = getMovieMapper(movieResult) as ArrayList<MovieTv>
                    listMovieTv.postValue(listItems)
                }
                
                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
                
                
            })
    }
    
}
