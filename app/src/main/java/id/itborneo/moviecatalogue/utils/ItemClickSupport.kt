package id.itborneo.moviecatalogue.utils

import android.view.View
import id.itborneo.moviecatalogue.R

class ItemClickSupport private constructor(private val mRecyclerView: androidx.recyclerview.widget.RecyclerView) {
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null
    private val mOnClickListener = View.OnClickListener { v ->
        if (mOnItemClickListener != null) {
            val holder = mRecyclerView.getChildViewHolder(v)
            mOnItemClickListener!!.onItemClicked(mRecyclerView, holder.adapterPosition, v)
        }
    }
    private val mOnLongClickListener = object : View.OnLongClickListener {
        override fun onLongClick(v: View): Boolean {
            if (mOnItemLongClickListener != null) {
                val holder = mRecyclerView.getChildViewHolder(v)
                return mOnItemLongClickListener!!.onItemLongClicked(mRecyclerView, holder.adapterPosition, v)
            }
            return false
        }
    }
    private val mAttachListener = object : androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener)
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener)
            }
        }
        
        override fun onChildViewDetachedFromWindow(view: View) {}
    }
    
    init {
        mRecyclerView.setTag(R.id.itemClickSupport, this)
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener)
    }
    
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mOnItemClickListener = listener
    }
    
    interface OnItemClickListener {
        fun onItemClicked(recyclerView: androidx.recyclerview.widget.RecyclerView, position: Int, v: View)
    }
    
    interface OnItemLongClickListener {
        fun onItemLongClicked(recyclerView: androidx.recyclerview.widget.RecyclerView, position: Int, v: View): Boolean
    }
    
    companion object {
        fun addTo(view: androidx.recyclerview.widget.RecyclerView): ItemClickSupport {
            return ItemClickSupport(view)
        }
        
    }
}