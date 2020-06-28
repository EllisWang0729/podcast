package com.example.casts

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import java.nio.CharBuffer

class PlayPresenter(private val context: Context, private val mView: PlayContract.View) :
    PlayContract.Presenter, MediaPlayer.OnBufferingUpdateListener,
    MediaPlayer.OnCompletionListener ,MediaPlayer.OnPreparedListener{
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var handler: Handler = Handler()
    private val runnable = object : Runnable {
        override fun run() {
            mView.getPlayCurrent(mediaPlayer.currentPosition)
            handler.postDelayed(this, 1000)

        }

    }


    override fun setPlaySource(url: String) {
        mediaPlayer.reset();
        mediaPlayer.setDataSource(url)
//        mediaPlayer.setOnErrorListener()
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnBufferingUpdateListener(this)
//        mediaPlayer.prepare()

    }

    override fun play() {
        if (!mediaPlayer.isPlaying) {
            if (!handler.hasCallbacks(runnable)) {
                handler.post(runnable)
            }
            mediaPlayer.start()
        }
    }

    override fun stop() {
        mediaPlayer.stop();

    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun reset() {
        mediaPlayer.reset()

    }

    override fun isPlay(): Boolean {
        return mediaPlayer.isPlaying
    }

    override fun removeHandler() {
        handler.removeCallbacks(runnable)
    }

    override fun seekTo(msec: Int) {
        mediaPlayer.seekTo(msec)
    }

    override fun onBufferingUpdate(p0: MediaPlayer?, p1: Int) {
        p0?.currentPosition?.let { mView.getPlayCurrent(it) }
    }

    override fun onCompletion(p0: MediaPlayer?) {
        removeHandler()
        mView.setPlay(true)
    }

    override fun onPrepared(p0: MediaPlayer?) {
        p0?.start()
        mView.setDuration(mediaPlayer.duration)
        handler.post(runnable)
        mView.setPlay(false)
    }


}