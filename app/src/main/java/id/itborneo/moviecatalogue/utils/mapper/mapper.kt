package id.itborneo.moviecatalogue.utils.mapper

import id.itborneo.moviecatalogue.ui.main.model.Movie
import id.itborneo.moviecatalogue.data.response.result

fun getMovieMapper(movies: List<result>?): MutableList<Movie> {
    val listMovie : MutableList<Movie> = mutableListOf()

    movies?.forEach {
        listMovie.add(
            Movie(
                it.title,
                it.poster_path,
                it.overview,
                it.release_date,
                it.vote_average
            )
        )
    }
    return listMovie
}


