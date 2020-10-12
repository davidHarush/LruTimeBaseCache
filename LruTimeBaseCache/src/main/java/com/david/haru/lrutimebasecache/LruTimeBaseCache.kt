package com.david.haru.lrutimebasecache

/**
 * Created by David Harush
 * On 12/10/2020.
 */
import android.util.LruCache
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by David Harush
 * On 16/09/2020.
 */
class LruTimeBaseCache<K, V>(
    cacheSize: Int = MAX_SIZE,
    private val expiredTime: Int = EXPIRED_TIME_IN_MIN
) {

    companion object {
        private const val EXPIRED_TIME_IN_MIN = 3
        private const val MAX_SIZE = 5
    }

    private val lruCache: LruCache<K, CacheItem<V>> = LruCache(cacheSize)

    fun put(key: K, data: V) {
        lruCache.put(key, CacheItem(data = data))
    }

    fun remove(key: K) {
        lruCache.remove(key)
    }

    fun removeAll() {
        lruCache.evictAll()
    }

    fun getNumberOfEntries() = lruCache.size()


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

        if (minutes >= expiredTime) {
            lruCache.remove(key)
            return true
        }
        return false
    }

    private data class CacheItem<V>(var data: V) {
        var creationTime: Date = Date()
    }

}