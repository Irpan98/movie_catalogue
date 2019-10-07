package id.itborneo.moviecatalogue.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieTv(
    var titleMovieTv: String? = null,
    var imageMovieTv: String? = null,
    var descMovieTv: String? = null,
    var dateMovieTv: String? = null,
    var voteAverage: Double? = null,
    var idMovieTv: Int = 0
) : Parcelable

