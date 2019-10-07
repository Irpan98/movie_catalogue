package id.itborneo.moviecatalogue.ui.main.fragment.tvShow

import androidx.lifecycle.LiveData
import id.itborneo.moviecatalogue.ui.model.MovieTv
import java.util.*

interface TvShowContract {
    
    interface Presenter {
        fun onAttach(view: View)
        fun getTv()
        fun setTv(): LiveData<ArrayList<MovieTv>>
        fun searchTv(title:String)
    }
    
    interface View {
        fun showLoading(isLoading: Boolean)
    }
    
}