package id.itborneo.moviecatalogue.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie  (
    var titleMovieTv: String? = null,
    var imageMovieTv: String? = null,
    var descMovieTv: String? = null,
    var dateMovieTv : String? = null,
    var voteAverage : Double? = null
): Parcelable

