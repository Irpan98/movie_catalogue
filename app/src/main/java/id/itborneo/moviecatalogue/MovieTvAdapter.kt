package id.itborneo.moviecatalogue

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.itborneo.moviecatalogue.ui.main.model.Movie


class MovieTvAdapter(private var context: Context) : RecyclerView.Adapter<MovieTvAdapter.CategoryViewHolder>() {

    private var movies : ArrayList<Movie>? =null

    fun listMoviesTv(listMovies : ArrayList<Movie>){
        this.movies =listMovies
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_movie,p0,false)
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
            .apply(RequestOptions().override(100, 150))
            .into(p0.imgMovieA)
    }


    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txtNameA : TextView = itemView.findViewById(R.id.txtName)
        internal var txtDescA : TextView = itemView.findViewById(R.id.txtDescMovie)
        internal var imgMovieA: ImageView = itemView.findViewById(R.id.imgMovie)
        internal var txtDateA: TextView = itemView.findViewById(R.id.txtDateDetail)
        internal var txtProgress: TextView = itemView.findViewById(R.id.txtProgress)
        internal var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    }

    fun setData(items: ArrayList<Movie>?) {
        clearData()
        items?.let { movies?.addAll(it) }
        notifyDataSetChanged()
    }

    private fun clearData() {
        movies?.clear()
    }

}
