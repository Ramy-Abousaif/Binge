package com.example.binge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrailerAdapter (mContext: Context, trailerList: List<Trailer>): RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {
    private var mContext: Context
    private var trailerList: List<Trailer>

    init {
        this.mContext = mContext
        this.trailerList = trailerList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TrailerAdapter.MyViewHolder
    {
        var view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.trailer_card, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TrailerAdapter.MyViewHolder, i: Int)
    {

    }

    override fun getItemCount(): Int
    {

    }

    public class MyViewHolder: RecyclerView.ViewHolder
    {
        public fun MyViewHolder(view: View)
        {

        }
    }
}