package id.itborneo.moviecatalogue.ui.detail

import android.content.Context
import id.itborneo.moviecatalogue.data.db.favorite.Favorite

interface DetailContract {
    
    interface Presenter {
        fun onAttach(context: Context, view: View)
        fun getFavoritedById(idMovie: Int)
        fun addMovieToFavorite(favorite: Favorite)
        fun delFromFavorite(favorite: Favorite)
    }
    
    interface View {
        fun showMovie()
        fun addFavoriteSuccess(msg: String)
        fun addFavoriteFailed(msg: String)
        fun isFavorited(favorite: Favorite)
        fun isDeletedFavorite(msg: String)
        fun showLoading(isLoading: Boolean)
    
    }
    
    
}