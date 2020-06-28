package com.example.casts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.casts.CastDetailActivity.Companion.DATA_KEY
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContract.View, CastsAdapter.HolderClickListener,
    SwipeRefreshLayout.OnRefreshListener {
    private var mainPresenter: MainContract.Presenter = MainPresenter(this, this)
    private lateinit var castsAdapter: CastsAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
    }

    private fun initView() {
        srl_freshLayout.setDistanceToTriggerSync(100);
        srl_freshLayout.setOnRefreshListener(this)
        gridLayoutManager = GridLayoutManager(this, 2)
        castsAdapter = CastsAdapter(context = this, listener = this)
        rv_casts.layoutManager = gridLayoutManager
        rv_casts.adapter = castsAdapter
    }

    private fun initData() {
        mainPresenter.getCastList()
    }

    override fun showCastList(podCastList: ArrayList<CastData.Data.PodCast>) {
        castsAdapter.updateList((podCastList as ArrayList<Any>))
    }

    override fun onClick(data: Any?) {
        data?.let {
            (it as CastData.Data.PodCast)
            Log.d("Click ", it.artistName)
            val intent = Intent(this, CastDetailActivity::class.java)
            intent.putExtra(DATA_KEY, it)
            startActivity(intent)
        }
    }

    override fun onRefresh() {
        srl_freshLayout.isRefreshing = false
        initData()
    }
}