package id.itborneo.moviecatalogue.ui.detail

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.data.db.favorite.FavoriteDao
import id.itborneo.moviecatalogue.ui.detail.DetailContract.Presenter
import id.itborneo.moviecatalogue.ui.detail.DetailContract.View
import id.itborneo.moviecatalogue.utils.ImageStorageManager
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import id.itborneo.moviecatalogue.data.db.favorite.Favorite as Favorite1

class DetailPresenter(private var favoriteDao: FavoriteDao) : Presenter {
    
    private lateinit var mView: View
    private lateinit var disposables: CompositeDisposable
    private lateinit var context: Context
    
    private val imageStorage = ImageStorageManager.Companion
    override fun addMovieToFavorite(favorite: Favorite1) {
        disposables.add(
            Completable.fromAction { favoriteDao.addFavorite(favorite) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    mView.isFavorited(favorite)
                    mView.addFavoriteSuccess(context.getString(R.string.berhasil))
                }, {
                    mView.addFavoriteFailed(context.getString(R.string.gagal))
                })
        )
        
        //save image to storage
        
        Glide.with(context)
            .asBitmap()
            .load(favorite.imageUrl)
            .into(object : CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {    }
    
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    imageStorage.saveToInternalStorage(context,resource,favorite.imageUrl)
    
                }
    
            })
        
        
    }
    
    override fun onAttach(context: Context, view: View) {
        this.context = context
        mView = view
        disposables = CompositeDisposable()
    }
    
    override fun getFavoritedById(idMovie: Int) {
        disposables.add(
            favoriteDao.getMovieById(idMovie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    mView.isFavorited(it[0])
                    
                }, {
                })
        )
    }
    
    override fun delFromFavorite(favorite: Favorite1) {
        disposables.add(
            Completable.fromAction { favoriteDao.deteleFavorite(favorite) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    mView.isDeletedFavorite(context.getString(R.string.berhasil_remove))
                }, {
                })
        )
    }
}