package id.itborneo.moviecatalogue.ui.main.fragment.favorite.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.ui.main.fragment.favorite.childFragment.MovieFavoriteFragment
import id.itborneo.moviecatalogue.ui.main.fragment.favorite.childFragment.TvShowFavoriteFragment

class FavoritePagerAdapter(val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
            
                MovieFavoriteFragment()
    
            }
            else -> {
                TvShowFavoriteFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.movieFavorite)
            else -> context.getString(R.string.tvFavorite)
        }
    }

}