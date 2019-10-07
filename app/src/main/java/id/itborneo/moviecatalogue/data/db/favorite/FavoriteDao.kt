package id.itborneo.moviecatalogue.data.db.favorite

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite WHERE id=:id")
    fun getMovieById(id: Int): Flowable<List<Favorite>>
    
    
    @Query("SELECT * FROM favorite WHERE category=:category")
    fun getMovieByCategory(category: String): Flowable<List<Favorite>>
    
    @Query("SELECT * FROM favorite WHERE category=:category")
    fun getWidgetFav(category: String):List<Favorite>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favorite: Favorite)
    
    @Delete
    fun deteleFavorite(favorite: Favorite)
}