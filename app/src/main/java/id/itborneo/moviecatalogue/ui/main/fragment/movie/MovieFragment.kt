package id.itborneo.moviecatalogue.ui.main.fragment.movie


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import id.itborneo.moviecatalogue.ui.main.model.Movie
import id.itborneo.moviecatalogue.MovieTvAdapter
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.utils.ShowView
import id.itborneo.moviecatalogue.ui.main.fragment.movie.MoviePresenter.MovieViewModel


class MovieFragment : Fragment(), MovieContract.View {
    override fun showMovie(movies: MutableLiveData<java.util.ArrayList<Movie>>) {


    }

    private  var movieAdapter: MovieTvAdapter? = null
    private var progressBar: ProgressBar? = null
    private var showView : ShowView? = null
    private lateinit var presenter: MoviePresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("MovieFragment","onCreateView Called")
        showView = context?.let {ShowView(it) }

        val movFragment = inflater.inflate(R.layout.fragment_movie, container, false)
        progressBar = movFragment.findViewById(R.id.progressBar)
        val recViewMovie  = movFragment.findViewById<RecyclerView>(R.id.recyMovie)

        showLoading(true)

        presenter = MoviePresenter()
        presenter.onAttach(this)
        presenter.getMovie()
        presenter.setMovie()


        val movieTvs = ArrayList<Movie>()
        movieAdapter = context?.let { MovieTvAdapter(it) }

        prepare()
        movieAdapter?.let { showView?.showRecyclerList(movieTvs, context.let { recViewMovie}, it) }
        return movFragment
    }

    private fun prepare() {

        Log.d("MovieFragment","prepare Called")

        val getMoviebyViewModel = ViewModelProviders.of(this).get(MoviePresenter::class.java)

        getMoviebyViewModel.getMovie().observe(this, movieVM)
//        Log.d("MovieFragment","prepare Called ${getMoviebyViewModel.listMovie.value?.get(1)}")


        getMoviebyViewModel.setMovie()
    }


    private val movieVM =
        Observer<ArrayList<Movie>> {
        Log.d("MovieFragment","getMovie Called")

        if (it != null) {
            Log.d("MovieFragment","getMovie ${it[2]}")

            movieAdapter?.setData(it)
            showLoading(false)
        }
    }
    override fun showLoading(isLoading: Boolean) {

        if( isLoading){
            showView?.showLoading(true, progressBar)
        }else{
            showView?.showLoading(false, progressBar)
        }
    }




//    override fun showMovie(movies: MutableLiveData<ArrayList<Movie>>) {
//        Log.d("MovieFragment","prepare Called")
//
//        val getMoviebyViewModel = ViewModelProviders.of(this).get(MoviePresenter::class.java)
//
//        getMoviebyViewModel.listMovie.observe(this, movieVM)
//        Log.d("MovieFragment","prepare Called ${getMoviebyViewModel.listMovie.value?.get(1)}")
//
//
//
//
//        getMoviebyViewModel.setMovie()
//
//    }




}
