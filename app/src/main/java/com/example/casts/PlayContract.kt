package com.example.casts

interface PlayContract {
    interface View {
        fun setPlay(isPlay: Boolean)

        fun setDuration(duration: Int)
        fun getPlayCurrent(current: Int)
    }

    interface Presenter {
        fun setPlaySource(url: String)
        fun play()
        fun stop()
        fun release()
        fun pause()
        fun reset()
        fun isPlay(): Boolean
        fun removeHandler()
        fun seekTo(msec: Int)
    }
}