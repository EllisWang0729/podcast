package com.example.casts

import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cast_detail.iv_img
import kotlinx.android.synthetic.main.activity_cast_detail.toolbar
import kotlinx.android.synthetic.main.activity_cast_detail.tv_title
import kotlinx.android.synthetic.main.activity_player.*
import java.util.*
import kotlin.math.round


class PlayerActivity : BaseActivity(), PlayContract.View, SeekBar.OnSeekBarChangeListener {
    companion object {
        const val PLAY_KEY = "play_key"
        const val URL_KEY = "url_key"
        const val THIRTY_SEC = 30000
    }

    private var data: DetailData.Data.Collection.ContentFeed? = null
    private var artworkUrl600: String? = null
    private val playPresenter: PlayContract.Presenter = PlayPresenter(this, this)
    private var mPlayCurrentMSec = 0
    private var mPlayCurrentMax = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        initView()
        initPlayer()
        setListener()

    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        if (intent.hasExtra(PLAY_KEY)) {
            data = intent.getParcelableExtra(PLAY_KEY)
            Log.e(this.javaClass.simpleName, Gson().toJson(data))
            data?.let { contentFeed ->
                contentFeed.title?.let {
                    tv_title.text = it
                }
            }

        }
        if (intent.hasExtra(URL_KEY)) {
            artworkUrl600 = intent.getStringExtra(URL_KEY)
            artworkUrl600?.let {
                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.drawable.ic_launcher_background)
                requestOptions.error(R.drawable.ic_launcher_background)
                Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(it)
                    .into(iv_img)
            }
        }
    }

    private fun initPlayer() {
        data?.contentUrl?.let {
            playPresenter.setPlaySource(it)
            playPresenter.play()
        }

    }

    private fun setListener() {
        iv_foward_30_sec.setOnClickListener { playPresenter.seekTo(mPlayCurrentMSec + THIRTY_SEC) }
        iv_replay_30_sec.setOnClickListener { playPresenter.seekTo(mPlayCurrentMSec - THIRTY_SEC) }
        iv_play.setOnClickListener {
            if (playPresenter.isPlay()) {
                setPlay(true)
                playPresenter.pause()
            } else {
                setPlay(false)
                playPresenter.play()
            }
        }
        sb_progress.setOnSeekBarChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        playPresenter.pause()
        playPresenter.stop()
        playPresenter.release()
        playPresenter.removeHandler()
    }

    override fun setPlay(isPlay: Boolean) {
        if (isPlay) {
            iv_play.setImageDrawable(getDrawable(R.drawable.ic_baseline_play_arrow_24))
        } else {
            iv_play.setImageDrawable(getDrawable(R.drawable.ic_baseline_pause_circle_filled_24))
        }
    }

    override fun setDuration(duration: Int) {
        mPlayCurrentMax = duration
        sb_progress.max = mPlayCurrentMax
        var remainingSecs = (duration / 1000)
        val remainingMins = remainingSecs / 60
        remainingSecs %= 60
        tv_music_max.text =
            String.format(Locale.getDefault(), "%02d:%02d", remainingMins, remainingSecs)
    }

    override fun getPlayCurrent(current: Int) {
        mPlayCurrentMSec = current
        var remainingSecs = (current / 1000)
        val remainingMins = remainingSecs / 60
        remainingSecs %= 60
        tv_music_current.text =
            String.format(Locale.getDefault(), "%02d:%02d", remainingMins, remainingSecs)
        sb_progress.progress = mPlayCurrentMSec
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        p0?.let { playPresenter.seekTo(it.progress) }
    }
}