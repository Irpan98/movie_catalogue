package id.itborneo.moviecatalogue.ui.main.fragment.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import id.itborneo.moviecatalogue.data.response.result
import id.itborneo.moviecatalogue.ui.main.model.Movie
import java.util.ArrayList


interface MovieContract {

    interface Presenter{
        fun onAttach(view: View)
        fun getMovie(): LiveData<ArrayList<Movie>>
        fun setMovie()
    }

    interface View{
        fun showLoading(isLoading: Boolean)
        fun showMovie(movies: MutableLiveData<ArrayList<Movie>>)
    }

}

