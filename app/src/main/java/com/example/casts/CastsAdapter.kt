package com.example.casts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CastsAdapter(
    val context: Context,
    var dataList: ArrayList<Any> = ArrayList(),
    val listener: HolderClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    companion object {
        private const val CAST_TYPE = 1
        private const val ERROR_TYPE = 2
    }

    private fun createErrorHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val cell = LayoutInflater.from(context).inflate(R.layout.item_error, parent, false)
        return ErrorViewHolder(cell)
    }

    class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    private fun createCastsHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val cell = LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false)
        cell.setOnClickListener(this)
        return CastsViewHolder(cell)
    }

    private class CastsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView = itemView.findViewById(R.id.tv_title)
        var contentView: TextView = itemView.findViewById(R.id.tv_content)
        var imgView: ImageView = itemView.findViewById(R.id.iv_img)


        fun setData(context: Context, data: CastData.Data.PodCast?) {
            data?.let { podCast ->
                titleView.text = podCast.artistName
                contentView.text = podCast.name
                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.drawable.ic_launcher_background)
                requestOptions.error(R.drawable.ic_launcher_background)
                Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(podCast.artworkUrl100)
                    .into(imgView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CAST_TYPE -> {
                createCastsHolder(parent)
            }
            else -> {
                createErrorHolder(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        when (holder) {
            is CastsViewHolder -> {
                holder.itemView.tag = position
                holder.setData(context, item as CastData.Data.PodCast)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is CastData.Data.PodCast -> {
                CAST_TYPE
            }
            else -> {
                ERROR_TYPE
            }
        }
    }

    override fun getItemCount(): Int {
        return if (dataList.isNullOrEmpty()) 0 else dataList.size
    }

    fun updateList(podCastList: ArrayList<Any>) {
        dataList = podCastList
        notifyDataSetChanged()
    }

    interface HolderClickListener {
        fun onClick(data: Any? = null)
    }

    override fun onClick(p0: View?) {
        p0?.let { listener.onClick(dataList[it.tag as Int]) }
    }
}