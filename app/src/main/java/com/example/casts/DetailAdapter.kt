package com.example.casts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DetailAdapter(
    val context: Context,
    var dataList: ArrayList<DetailData.Data.Collection.ContentFeed?> = ArrayList(),
    val listener: HolderClickListener
) : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>(), View.OnClickListener {

    private fun createCastsHolder(parent: ViewGroup): DetailViewHolder {
        val cell = LayoutInflater.from(context).inflate(R.layout.item_detail, parent, false)
        cell.setOnClickListener(this)
        return DetailViewHolder(cell)
    }

    class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameView: TextView = itemView.findViewById(R.id.tv_name)

        fun setData(context: Context, data: DetailData.Data.Collection.ContentFeed?) {
            data?.let { feed ->
                nameView.text = feed.title?.replace(" | ","\n")
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailAdapter.DetailViewHolder {
        return createCastsHolder(parent)
    }

    override fun getItemCount(): Int {
        return if (dataList.isNullOrEmpty()) 0 else dataList.size
    }

    override fun onBindViewHolder(holder: DetailAdapter.DetailViewHolder, position: Int) {
        holder.itemView.tag = position
        holder.setData(context, dataList[position])
    }

    fun updateList(detailList: ArrayList<DetailData.Data.Collection.ContentFeed?>) {
        dataList = detailList
        notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {
        p0?.let { listener.onClick(dataList[it.tag as Int]) }
    }

    interface HolderClickListener {
        fun onClick(data: DetailData.Data.Collection.ContentFeed? = null)
    }
}