package id.itborneo.moviecatalogue.ui.main.fragment.tvShow


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
import id.itborneo.moviecatalogue.ui.settings.SettingsActivity
import id.itborneo.moviecatalogue.utils.ShowView
import id.itborneo.moviecatalogue.utils.TV_SHOW

class TvShowFragment : androidx.fragment.app.Fragment(), TvShowContract.View {
    
    private lateinit var tvShowAdapter: MovieTvAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var showView: ShowView
    private lateinit var presenter: TvShowPresenter
    private val tvShowViewM =
        Observer<ArrayList<MovieTv>> {
            if (it != null) {
                tvShowAdapter.setData(it)
                showLoading(false)
            }
        }
    

    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        
        when (item?.itemId) {
            R.id.menu_setting -> {
                val intent = Intent(requireContext(), SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        
        return false
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.option_menu, menu)
        Log.d("Tvshowfragment", "onCreateOptionMenu called")
        
        searchViewSetUp(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    lateinit var tvShow: ArrayList<MovieTv>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        showView = ShowView(context as Context)
        
        val fragView = inflater.inflate(R.layout.fragment_tv_show, container, false)
        
        val recViewTv = fragView.findViewById<RecyclerView>(R.id.recyTvShow)
        
        progressBar = fragView.findViewById(R.id.progressBar)
        showLoading(true)
        
        tvShow = ArrayList()
        tvShowAdapter = MovieTvAdapter(context as Context)
        
        
        presenter = TvShowPresenter()
        presenter.onAttach(this)
        presenter.getTv()
        presenter.setTv()
        
        tvShowViewModel()
        showView.showRecyclerList(tvShow, recViewTv, tvShowAdapter, TV_SHOW)
        
        return fragView
    }
    
    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            showView.showLoading(true, progressBar)
        } else {
            showView.showLoading(false, progressBar)
        }
    }
    
    lateinit var getTvShowByViewModel: TvShowPresenter
    private fun tvShowViewModel() {
        
        getTvShowByViewModel = ViewModelProviders.of(this).get(TvShowPresenter::class.java)
        getTvShowByViewModel.setTv().observe(this, tvShowViewM)
        getTvShowByViewModel.getTv()
        tvShowAdapter.notifyDataSetChanged()
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        
    }
    
    
    private fun searchViewSetUp(menu: Menu?) {
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    showLoading(true)
                    tvShow.clear()
                    getTvShowByViewModel.searchTv(query as String)
                    
                    Toast.makeText(requireActivity(), query, Toast.LENGTH_SHORT).show()
                    return true
                }
                
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }
    
}
