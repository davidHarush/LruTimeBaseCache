package com.david.haru.lrutimebasecache

/**
 * Created by David Harush
 */
import android.util.LruCache
import androidx.annotation.IntRange
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import java.util.*
import java.util.concurrent.TimeUnit

class LruTimeBaseCache<K, V>(
    private val expiredTimeInMinutes: Int = EXPIRED_TIME_IN_MIN
) {
    private  lateinit var  lruCache: LruCache<K, CacheItem<V>>

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        // Use 1/8th of the available memory for this memory cache.
        val cacheSize =maxMemory / 8
        lruCache = LruCache(cacheSize)
    }

    companion object {
        private const val EXPIRED_TIME_IN_MIN = 3

    }

     /**
     * Set a new CacheSize
      *
      * Copy from Lru class :
      *   @param maxSize for caches that do not override {@link #sizeOf}, this is
      *     the maximum number of entries in the cache. For all other caches,
      *     this is the maximum sum of the sizes of the entries in this cache.
      *
     */
    fun setCacheSize(@IntRange(from=1) cacheSize : Int){
         lruCache.resize(cacheSize)
    }

    fun put(@NonNull key: K,@NonNull data: V) {
        lruCache.put(key, CacheItem(data = data))
    }

    fun remove(@NonNull key: K) {
        lruCache.remove(key)
    }

    fun removeAll() {
        lruCache.evictAll()
    }

    fun getNumberOfEntries() = lruCache.size()


    @Nullable
    fun get(key: K): V? {
        val cacheItem: CacheItem<V> = lruCache[key] ?: return null

        return if (removeIfExpired(key, cacheItem)) {
            null
        } else {
            cacheItem.data
        }
    }

    /**********************
     *****   PRIVATE  *****
     **********************/


    /**
     * return true if item is Expired (and removed)
     */
    private fun removeIfExpired(key: K, cacheItem: CacheItem<V>): Boolean {
        val timeInterval =
            System.currentTimeMillis() - cacheItem.creationTime.time

        val minutes =
            TimeUnit.MILLISECONDS.toMinutes(timeInterval)

        if (minutes >= expiredTimeInMinutes) {
            lruCache.remove(key)
            return true
        }
        return false
    }

    private data class CacheItem<V>(var data: V) {
        var creationTime: Date = Date()
    }

}