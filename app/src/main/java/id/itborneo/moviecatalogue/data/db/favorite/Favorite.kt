package id.itborneo.moviecatalogue.data.db.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "dateRelease") val dateRelease: String,
    @ColumnInfo(name = "voteAverage") val voteAverage: Double,
    @ColumnInfo(name = "category") val category: String
)