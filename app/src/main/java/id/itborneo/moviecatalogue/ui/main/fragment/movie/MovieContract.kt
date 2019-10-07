package id.itborneo.moviecatalogue.ui.main.fragment.movie

import androidx.lifecycle.LiveData
import id.itborneo.moviecatalogue.ui.model.MovieTv
import java.util.*


interface MovieContract {
    
    interface Presenter {
        fun onAttach(view: View)
        fun getMovie()
        fun setMovie(): LiveData<ArrayList<MovieTv>>
        fun searchMovie(title:String)
    }
    
    interface View {
        fun showLoading(isLoading: Boolean)
    }
    
}

