package id.itborneo.moviecatalogue.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.itborneo.moviecatalogue.data.db.favorite.Favorite
import id.itborneo.moviecatalogue.data.db.favorite.FavoriteDao

@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun favoriteDao(): FavoriteDao
    
    companion object {
        private var INSTANCE: AppDatabase? = null
        
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "db_favorite"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE as AppDatabase
            
        }
    }
}