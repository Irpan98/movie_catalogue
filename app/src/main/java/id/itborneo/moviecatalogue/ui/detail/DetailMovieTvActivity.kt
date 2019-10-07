package id.itborneo.moviecatalogue.ui.detail

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.data.db.favorite.Favorite
import id.itborneo.moviecatalogue.ui.model.MovieTv
import id.itborneo.moviecatalogue.utils.*
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.jetbrains.anko.toast

class DetailMovieTvActivity : AppCompatActivity(),
    ConnectivityReceiver.ConnectivityReceiverListener, DetailContract.View {
    override fun showLoading(isLoading: Boolean) {
    
        if (isLoading) {
            showView.showLoading(true, progressBar)
        } else {
            showView.showLoading(false, progressBar)
        }
    }
    
    private lateinit var progressBar: ProgressBar
    private lateinit var fabFavorite: FloatingActionButton
    private lateinit var movie: MovieTv
    private lateinit var snackBar: Snackbar
    private lateinit var showView: ShowView
    private lateinit var presenter: DetailPresenter
    private lateinit var favorited: List<Favorite>
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        
        showView = ShowView(this)
        progressBar = findViewById(R.id.progressBar)
        showLoading(true)
        
//        registerConnectionReceiver()
        showView = ShowView(applicationContext)
        showView.changeAppBar(window)
        activeSnackBar()
        getView()
        
        favorited = mutableListOf()
        
        val appDatabase = InjectorUtils.provideAppDatabase(this)
        val favoriteDao = InjectorUtils.provideFavoriteDao(appDatabase)
        
        movie = intent.getParcelableExtra(MOVIE)
        
        presenter = DetailPresenter(favoriteDao)
        presenter.onAttach(baseContext, this)
        presenter.getFavoritedById(movie.idMovieTv)
        
        val favorite =
            Favorite(
                movie.idMovieTv,
                movie.titleMovieTv.toString(),
                movie.imageMovieTv.toString(),
                movie.descMovieTv.toString(),
                movie.dateMovieTv.toString(),
                movie.voteAverage as Double,
                getKnowActivity()
            )
        fabFavorite = fab_favorite
        
        fabFavorite.setOnClickListener {
            if (favorited.isEmpty()) {
                presenter.addMovieToFavorite(favorite)
            } else {
                presenter.delFromFavorite(favorite)
            }
        }
    }
    
    private fun getKnowActivity(): String {
        val isMovie = intent.getBooleanExtra(IS_MOVIE, true)
    
        return if (isMovie) {
            Log.d("DetailMovieTvActivty", "this is movie")
    
            setTextActionBar(getString(R.string.detailMovie))
            MOVIE
        } else {
            Log.d("DetailMovieTvActivty", "this is TV SHow")
    
            TV_SHOW
        }
        
    }
    
    private fun setTextActionBar(text: String) {
        supportActionBar?.title = text
    }
    
    
    private fun getView() {
        val movie: MovieTv = intent.getParcelableExtra(MOVIE)
        showLoading(false)
        txtTitleMovie.text = movie.titleMovieTv
        txtSummary.text = movie.descMovieTv
        txtDateDetail.text = movie.dateMovieTv
        
        val imageSize = "w185"
        val urlImage = "https://image.tmdb.org/t/p/$imageSize${movie.imageMovieTv}"
        
        Log.d("DetailMovieTvActivity ", movie.imageMovieTv)
        
        Glide.with(this)
            .load(urlImage)
            .apply(RequestOptions().override(200, 300))
            .into(imgPosterMovie)
    }
    
    private fun registerConnectionReceiver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val receiver = ConnectivityReceiver()
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
            registerReceiver(receiver, intentFilter)
        }
    }
    
    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }
    
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        snackBar.let { showView.showNetworkMessage(isConnected, it) }
    }
    
    private fun activeSnackBar() {
        snackBar =
            Snackbar.make(findViewById(R.id.rootLayout), getString(R.string.noInternetText), Snackbar.LENGTH_LONG)
        snackBar.let { showView.showNetworkMessage(true, it) }
    }
    
    override fun showMovie() {
        
        txtTitleMovie.text = movie.titleMovieTv
        txtSummary.text = movie.descMovieTv
        txtDateDetail.text = movie.dateMovieTv
        
        val imageSize = "w185"
        val urlImage = "https://image.tmdb.org/t/p/$imageSize${movie.imageMovieTv}"
        
        Log.d("DetailMovieTvActivity ", movie.imageMovieTv)
        
        
        Glide.with(this)
            .load(urlImage)
            .apply(RequestOptions().override(200, 300))
            .into(imgPosterMovie)
    }
    
    override fun isFavorited(favorite: Favorite) {
        
        favorited = listOf(favorite)
        fabFavorite.setImageResource(R.drawable.ic_favorite)
        
    }
    
    override fun isDeletedFavorite(msg: String) {
        fabFavorite.setImageResource(R.drawable.ic_favorite_border)
        favorited = emptyList()
        toast(msg)
    }
    
    override fun addFavoriteSuccess(msg: String) { toast(msg) }
    override fun addFavoriteFailed(msg: String) { toast(msg) }
    
}
