package id.itborneo.moviecatalogue.ui.main.fragment.tvShow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.itborneo.moviecatalogue.data.network.ApiClient
import id.itborneo.moviecatalogue.data.response.TvShowResponse
import id.itborneo.moviecatalogue.ui.model.MovieTv
import id.itborneo.moviecatalogue.utils.mapper.getTvShowMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class TvShowPresenter : TvShowContract.Presenter, ViewModel() {
    override fun searchTv(title: String) {
        ApiClient.create().searchTv(title).enqueue(object : Callback<TvShowResponse> {
            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.d("TvShowPresenter", "onfailure")
            }
            
            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                val tvShow = response.body() as TvShowResponse
                
                val tvShowResult = tvShow.results
                Log.d("MoviePresenter", "tvShow result $tvShowResult")
                listItems = getTvShowMapper(tvShowResult) as ArrayList<MovieTv>
                listMovieTv.postValue(listItems)
                
            }
            
        })
    }
    
    private val listMovieTv = MutableLiveData<ArrayList<MovieTv>>()
    var listItems = ArrayList<MovieTv>()
    private var mView: TvShowContract.View? = null
    
    override fun setTv(): LiveData<ArrayList<MovieTv>> {
        return listMovieTv
    }
    
    override fun onAttach(view: TvShowContract.View) {
        mView = view
    }
    
    override fun getTv() {
        
        ApiClient.create().getTvShow().enqueue(object : Callback<TvShowResponse> {
            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.d("TvShowPresenter", "onresponse called")
            }
            
            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                val tvShow = response.body() as TvShowResponse
                
                val tvShowResult = tvShow.results
                Log.d("TvShowPresenter", "movie result $tvShowResult")
                listItems = getTvShowMapper(tvShowResult) as ArrayList<MovieTv>
                listMovieTv.postValue(listItems)
            }
            
        })
        
        
    }
}
