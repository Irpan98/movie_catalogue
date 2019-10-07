package id.itborneo.moviecatalogue.ui.main.fragment.movie


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.ui.adapter.MovieTvAdapter
import id.itborneo.moviecatalogue.ui.model.MovieTv
import id.itborneo.moviecatalogue.utils.MOVIE
import id.itborneo.moviecatalogue.utils.ShowView
import id.itborneo.moviecatalogue.ui.settings.SettingsActivity
class MovieFragment : androidx.fragment.app.Fragment(), MovieContract.View {
    
    
    private lateinit var movieAdapter: MovieTvAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var showView: ShowView
    private lateinit var presenter: MoviePresenter
    private lateinit var  movieTvs: ArrayList<MovieTv>
    private lateinit var  recViewMovie :RecyclerView
    private val movieVM =
        Observer<ArrayList<MovieTv>> {
            if (it != null) {
                movieAdapter.setData(it)
                showLoading(false)
            }
        }
    
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        showView = ShowView(context as Context)
        val movFragment = inflater.inflate(R.layout.fragment_movie, container, false)
         recViewMovie = movFragment.findViewById(R.id.recyMovie)
        progressBar = movFragment.findViewById(R.id.progressBar)
        showLoading(true)
        
         movieTvs = ArrayList()
        movieAdapter = MovieTvAdapter(context as Context)
        
        
        presenter = MoviePresenter()
        presenter.onAttach(this)
        presenter.getMovie()
        presenter.setMovie()
        
        
        movieViewModel()
        showRecylerView()
        return movFragment
    }
    
    private fun showRecylerView(){
        showView.showRecyclerList(movieTvs, recViewMovie, movieAdapter, MOVIE)
    
    }
    
    override fun showLoading(isLoading: Boolean) {
        
        if (isLoading) {
            showView.showLoading(true, progressBar)
        } else {
            showView.showLoading(false, progressBar)
        }
    }
    private lateinit var  getMoviebyViewModel: MoviePresenter
    private fun movieViewModel() {
        
         getMoviebyViewModel = ViewModelProviders.of(this).get(MoviePresenter::class.java)
        getMoviebyViewModel.setMovie().observe(this, movieVM)
        getMoviebyViewModel.getMovie()
        
        movieAdapter.notifyDataSetChanged()
    }
    
    private fun searchViewSetUp(menu: Menu?){
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.search)?.actionView as SearchView).apply{
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    showLoading(true)
                    movieTvs.clear()
                    getMoviebyViewModel.searchMovie(query as String)

                    Toast.makeText(requireActivity(), query, Toast.LENGTH_SHORT).show()
                    return true
                }
            
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.option_menu, menu)
        Log.d("MovieFragment","onCreateOptionMenu called")
    
        searchViewSetUp(menu)
        
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    
    }
    
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        
        when(item?.itemId){
            R.id.menu_setting -> {
                val intent = Intent(requireContext(), SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        
        return false
    }
}
