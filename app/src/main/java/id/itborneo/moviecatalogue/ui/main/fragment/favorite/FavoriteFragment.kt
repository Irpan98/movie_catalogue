package id.itborneo.moviecatalogue.ui.main.fragment.favorite


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.ui.main.fragment.favorite.adapter.FavoritePagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.view.*

class FavoriteFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragView = inflater.inflate(R.layout.fragment_favorite, container, false)
        
        
        val childFragAdapter = FavoritePagerAdapter(context as Context, childFragmentManager)
        
        fragView.view_pager.adapter = childFragAdapter
        
        fragView.tabLayout.setupWithViewPager(fragView.view_pager)
        
        
        
        return fragView
        
    }
    
    
}
