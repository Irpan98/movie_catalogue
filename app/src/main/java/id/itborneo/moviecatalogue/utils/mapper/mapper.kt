package id.itborneo.moviecatalogue.utils.mapper

import id.itborneo.moviecatalogue.data.response.TvShowResult
import id.itborneo.moviecatalogue.data.response.result
import id.itborneo.moviecatalogue.ui.model.MovieTv

fun getMovieMapper(movies: List<result>?): MutableList<MovieTv> {
    val listMovie: MutableList<MovieTv> = mutableListOf()
    
    movies?.forEach {
        listMovie.add(
            MovieTv(
                it.title,
                it.poster_path,
                it.overview,
                it.release_date,
                it.vote_average,
                it.id
            )
        )
    }
    return listMovie
}

fun getTvShowMapper(tvShows: List<TvShowResult>?): MutableList<MovieTv> {
    val listTv: MutableList<MovieTv> = mutableListOf()
    
    tvShows?.forEach {
        listTv.add(
            MovieTv(
                it.name,
                it.poster_path,
                it.overview,
                it.first_air_date,
                it.vote_average,
                it.id
            )
        )
        
    }
    return listTv
}


