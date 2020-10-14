package com.david.haru.lrutimebasecachesample

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.lruCache
import com.david.haru.lrutimebasecache.LruTimeBaseCache
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dataCache = LruTimeBaseCache<Int, String>()
            .setExpiredTime(1)
            .put(1, "q")
            .put(2, "s")
            .put(3, "s")
            .put(4, "v")


        Handler().postDelayed({
            dataCache.put(5, "n").put(6, "z")
        }, 30_000)


        object : CountDownTimer(100_000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                time.text = "Seconds remaining : " + millisUntilFinished / 1000
                entries.text =
                    "Entries in cache : ${dataCache.getNumberOfEntries()}"
                list.text = dataCache.toString()
                dataCache.getNumberOfEntries()
            }

            override fun onFinish() {
                time.text = "Done!"
                entries.text =
                    "Entries in cache : ${dataCache.getNumberOfEntries()}"
            }
        }.start()

    }
}