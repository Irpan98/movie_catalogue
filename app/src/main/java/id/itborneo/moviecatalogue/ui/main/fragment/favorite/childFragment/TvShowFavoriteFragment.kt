package id.itborneo.moviecatalogue.ui.main.fragment.favorite.childFragment


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.data.db.AppDatabase
import id.itborneo.moviecatalogue.ui.adapter.MovieTvAdapter
import id.itborneo.moviecatalogue.ui.model.MovieTv
import id.itborneo.moviecatalogue.utils.InjectorUtils
import id.itborneo.moviecatalogue.utils.ShowView
import id.itborneo.moviecatalogue.utils.TV_SHOW
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TvShowFavoriteFragment : Fragment() {
    
    private lateinit var disposables: CompositeDisposable
    private lateinit var tvShow: ArrayList<MovieTv>
    private lateinit var favoriteAdapter: MovieTvAdapter
    
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_tv_show_favorite, container, false)
        tvShow = ArrayList()
        getFavorite()
        val recViewMovie = fragView.findViewById<RecyclerView>(R.id.recyTvShow)
        
        favoriteAdapter = MovieTvAdapter(context as Context)
        
        ShowView(context as Context)
            .showRecyclerList(
                tvShow,
                recViewMovie,
                favoriteAdapter,
                TV_SHOW
            )
        favoriteAdapter.setData(tvShow)
        return fragView
    }
    
    
    private fun getFavorite() {
        
        val appDatabase = context?.let { InjectorUtils.provideAppDatabase(it) }
        val favoriteDao = InjectorUtils.provideFavoriteDao(appDatabase as AppDatabase)
        
        disposables = CompositeDisposable()
        disposables.add(
            favoriteDao.getMovieByCategory(TV_SHOW)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { it ->
                    tvShow.clear()
                    it.forEach {
                        val movie = MovieTv(
                            it.title,
                            it.imageUrl,
                            it.description,
                            it.dateRelease,
                            it.voteAverage,
                            it.id
                        )
                        Log.d("TvShowFavoriteFragment", "$movie")
                        tvShow.add(movie)
                    }
                    favoriteAdapter.notifyDataSetChanged()
                }
        )
        
    }
    
    override fun onResume() {
        super.onResume()
        getFavorite()
    }
    
    
}
