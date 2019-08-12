package id.itborneo.moviecatalogue.ui.main.fragment


import android.support.v4.app.Fragment

class TvShowFragment : Fragment() {
//
//    private var tvAdapter: MovieTvAdapter? = null
//    private var progressBar: ProgressBar? = null
//    private var showView : ShowView? = null
//    companion object {
//        private const val END_POINT = "tv"
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val fragView =  inflater.inflate(R.layout.fragment_tv_show, container, false)
//
//        progressBar = fragView.findViewById(R.id.progressBarTv)
//        val recViewTv  = fragView.findViewById<RecyclerView>(R.id.recyTvSeries)
//
//        val movieTvs = ArrayList<Movie>()
//        showView = context?.let {ShowView(it) }
//        tvAdapter = context?.let { MovieTvAdapter(it) }
//        showView?.showLoading(true, progressBar)
//
////        val char : Char = "a"
//        prepare()
//        tvAdapter?.let { showView?.showRecyclerList(movieTvs, context.let { recViewTv}, it) }
//        return fragView
//    }
//
//    private fun prepare() {
//        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
//        mainViewModel?.movieTv?.observe(this, getMovie)
//        mainViewModel?.setMovieTv(TvShowFragment.Companion.END_POINT)
//    }
//
//    private val getMovie = Observer<ArrayList<Movie>> {
//        if (it != null) {
//            tvAdapter?.setData(it)
//            showView?.showLoading(false, progressBar)
//        }
//    }
}
