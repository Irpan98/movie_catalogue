package id.itborneo.moviecatalogue.activities

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.itborneo.moviecatalogue.ui.main.model.Movie
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.utils.ConnectivityReceiver
import id.itborneo.moviecatalogue.utils.ShowView
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieTvActivity : AppCompatActivity()  ,
    ConnectivityReceiver.ConnectivityReceiverListener{
    private var snackBar: Snackbar? = null
    private lateinit var showView: ShowView

    companion object {
        const val MOVIE = "movie"
        const val IS_MOVIE = "movie_or_tv"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        registerConnectionReceiver()
        showView = ShowView(applicationContext)



        showView.changeAppBar(window)
        activeSnackBar()
        val isMovie = intent.getBooleanExtra(IS_MOVIE, true)
        if (isMovie) {
            supportActionBar?.title = getString(R.string.detailMovie)
        } else {
            supportActionBar?.title = getString(R.string.detailTvShow)
        }

        val movie: Movie = intent.getParcelableExtra(MOVIE)
        txtTitleMovie.text = movie.titleMovieTv
        txtSummary.text = movie.descMovieTv
        txtDateDetail.text = movie.dateMovieTv
        Glide.with(this)
            .load(movie.imageMovieTv)
            .apply(RequestOptions().override(200, 300))
            .into(imgPosterMovie)
    }

    private fun registerConnectionReceiver(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            val receiver = ConnectivityReceiver()
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
            registerReceiver(receiver, intentFilter)
        }
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener =this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        snackBar?.let { showView.showNetworkMessage(isConnected, it) }
    }

    private fun activeSnackBar() {
        snackBar = Snackbar.make(findViewById(R.id.rootLayout), getString(R.string.noInternetText), Snackbar.LENGTH_LONG)
        snackBar?.let { showView.showNetworkMessage(true, it) }
    }
}
