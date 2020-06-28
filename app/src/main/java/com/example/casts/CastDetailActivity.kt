package com.example.casts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.casts.PlayerActivity.Companion.PLAY_KEY
import com.example.casts.PlayerActivity.Companion.URL_KEY
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cast_detail.*
import kotlinx.android.synthetic.main.activity_cast_detail.srl_freshLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class CastDetailActivity : BaseActivity(), DetailContract.View, DetailAdapter.HolderClickListener,
    SwipeRefreshLayout.OnRefreshListener {
    companion object {
        const val DATA_KEY = "data_key"
    }

    private var podCast: CastData.Data.PodCast? = null
    private val detailPresenter: DetailContract.Presenter = DetailPresenter(this, this)
    private lateinit var lineLayoutManager: LinearLayoutManager
    private lateinit var detailAdapter: DetailAdapter
    private var url600: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast_detail)
        initView()
        initData()

    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        srl_freshLayout.setDistanceToTriggerSync(100);
        srl_freshLayout.setOnRefreshListener(this)

        lineLayoutManager = LinearLayoutManager(this)
        lineLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_detail.layoutManager = lineLayoutManager
        val mDividerItemDecoration =
            DividerItemDecoration(rv_detail.context, lineLayoutManager.orientation)
        rv_detail.addItemDecoration(mDividerItemDecoration)
        detailAdapter = DetailAdapter(context = this, listener = this)
        rv_detail.adapter = detailAdapter

        if (intent.hasExtra(DATA_KEY)) {
            podCast = intent.getParcelableExtra(DATA_KEY)
            Log.d(this.javaClass.simpleName, Gson().toJson(podCast))
            podCast?.artworkUrl100?.let {
                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.drawable.ic_launcher_background)
                requestOptions.error(R.drawable.ic_launcher_background)
                Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(it)
                    .into(iv_img)
            }
            podCast?.name?.let { tv_title.text = it }
            podCast?.artistName?.let { tv_content.text = it }

        }
    }

    private fun initData() {
        detailPresenter.getDetail()
    }

    override fun showDetailList(dataList: ArrayList<DetailData.Data.Collection.ContentFeed?>) {
        detailAdapter.updateList(dataList)
    }

    override fun getImgUrl(artworkUrl600: String?) {
        artworkUrl600?.let {
            url600 = it
        }
    }

    override fun onClick(data: DetailData.Data.Collection.ContentFeed?) {
        data?.let {
            Log.d("Click ", it.title)
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra(PLAY_KEY, it)
            intent.putExtra(URL_KEY, url600)
            startActivity(intent)
        }
    }

    override fun onRefresh() {
        srl_freshLayout.isRefreshing = false
        initData()
    }
}