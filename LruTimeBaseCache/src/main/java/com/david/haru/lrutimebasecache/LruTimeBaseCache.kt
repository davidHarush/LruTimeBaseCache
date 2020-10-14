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
import kotlin.collections.HashMap

class LruTimeBaseCache<K, V>(
    private var expiredTimeInMinutes: Int = EXPIRED_TIME_IN_MIN
) {
    private var lruCache: LruCache<K, CacheItem<V>>

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        // Use 1/8th of the available memory for this memory cache.
        val cacheSize = maxMemory / 8
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
    public fun setCacheSize(@IntRange(from = 1) cacheSize: Int): LruTimeBaseCache<K, V> {
        lruCache.resize(cacheSize)
        return this
    }

    public fun setExpiredTime(expiredTimeInMinutes: Int): LruTimeBaseCache<K, V> {
        this.expiredTimeInMinutes = expiredTimeInMinutes
        return this
    }


    public fun put(@NonNull key: K, @NonNull data: V): LruTimeBaseCache<K, V> {
        lruCache.put(key, CacheItem(data = data))
        return this
    }

    public fun remove(@NonNull key: K): LruTimeBaseCache<K, V> {
        lruCache.remove(key)
        return this
    }

    public fun removeAll(): LruTimeBaseCache<K, V> {
        lruCache.evictAll()
        return this
    }

    public fun getNumberOfEntries(): Int{
        clearAllExpired()
        return lruCache.size()
    }


    @Nullable
    public fun get(key: K): V? {
        val cacheItem: CacheItem<V> = lruCache[key] ?: return null

        return if (removeIfExpired(key, cacheItem)) {
            null
        } else {
            cacheItem.data
        }
    }

    public fun clearAllExpired(): LruTimeBaseCache<K, V> {

        val snapshot = lruCache.snapshot()
        for ((key, value) in snapshot) {
            removeIfExpired(key, lruCache.get(key))
        }
        return this
    }

   public fun getAllAsMap(): MutableMap<K, V> {

        val map = mutableMapOf<K,V>()
        val snapshot = lruCache.snapshot()
        for ((key, value) in snapshot) {
            map[key] = value.data
        }
        return map
    }


    override fun toString(): String{
        return getAllAsMap().toString()
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