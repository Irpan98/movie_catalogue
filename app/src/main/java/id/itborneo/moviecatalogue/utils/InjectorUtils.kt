package id.itborneo.moviecatalogue.utils

import android.content.Context
import id.itborneo.moviecatalogue.data.db.AppDatabase
import id.itborneo.moviecatalogue.data.db.favorite.FavoriteDao

object InjectorUtils {
    fun provideAppDatabase(context: Context)
            : AppDatabase = AppDatabase.getInstance(context)
    
    fun provideFavoriteDao(appDatabase: AppDatabase)
            : FavoriteDao = appDatabase.favoriteDao()
}