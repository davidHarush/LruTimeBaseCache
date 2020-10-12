package com.david.haru.lrutimebasecachesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.david.haru.lrutimebasecache.LruTimeBaseCache

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


         val dataCache= LruTimeBaseCache<Int,String>()

        dataCache.put(1,"first")
        dataCache.put(2,"second")

        Log.i("MainActivity",dataCache.get(1)!!)

    }
}