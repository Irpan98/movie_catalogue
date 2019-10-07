package id.itborneo.moviecatalogue.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.ui.model.MovieTv


class MovieTvAdapter(private var context: Context) : RecyclerView.Adapter<MovieTvAdapter.CategoryViewHolder>() {
    
    private var movies: ArrayList<MovieTv>? = null
    
    fun listMoviesTv(listMovies: ArrayList<MovieTv>) {
        this.movies = listMovies
    }
    
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_movie, p0, false)
        return CategoryViewHolder(view)
    }
    
    override fun getItemCount(): Int {
        return movies!!.size
    }
    
    override fun onBindViewHolder(p0: CategoryViewHolder, p1: Int) {
        
        val voteValue = movies?.get(p1)?.voteAverage
        
        p0.txtNameA.text = movies?.get(p1)?.titleMovieTv
        p0.txtDescA.text = movies?.get(p1)?.descMovieTv
        p0.txtDateA.text = movies?.get(p1)?.dateMovieTv
        p0.txtProgress.text = voteValue.toString()
        voteValue?.toInt()?.let { p0.progressBar.progress = it * 10 }
        
        val imageSize = "w185"
        val urlImage = "https://image.tmdb.org/t/p/$imageSize${movies?.get(p1)?.imageMovieTv}"
        
        
        Glide.with(context)
            .load(urlImage)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .apply(RequestOptions().override(100, 150))
            .into(p0.imgMovieA)
    }
    
    
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txtNameA: TextView = itemView.findViewById(R.id.txtName)
        internal var txtDescA: TextView = itemView.findViewById(R.id.txtDescMovie)
        internal var imgMovieA: ImageView = itemView.findViewById(R.id.imgMovie)
        internal var txtDateA: TextView = itemView.findViewById(R.id.txtDateDetail)
        internal var txtProgress: TextView = itemView.findViewById(R.id.txtProgress)
        internal var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    }
    
    fun setData(items: ArrayList<MovieTv>?) {
        clearData()
        items?.let { movies?.addAll(it) }
        notifyDataSetChanged()
    }
    
    private fun clearData() {
        movies?.clear()
    }
    
}
