[![](https://jitpack.io/v/davidHarush/LruTimeBaseCache.svg)](https://jitpack.io/#davidHarush/LruTimeBaseCache)


# LruTimeBaseCache

### A Wrapper class for Android's LruCache which adds a time based expiration functionality.<br>
- The expire Time In Units of Minutes.
- The defult expire Time velue is 3 Minutes.
- When the time is up, the returned value will be null.
- Support kotlin+Java




## Installation.
```sh
repositories {
  maven { url 'https://jitpack.io' }
}
```

```sh
dependencies {
  implementation 'com.github.davidHarush:LruTimeBaseCache:1.2'
}
```

## How I use it.

```sh
   val dataCache = LruTimeBaseCache<Int, String>()
            .setExpiredTime(5)
            .put(1, "first")
            .put(2, "second")

        Log.i("MainActivity", dataCache.get(1)!!)
```

You also can set the expire Time in the constructor, the defult velue is 3 Minutes.

```sh
  val dataCache= LruTimeBaseCache<Int,String>(expiredTimeInMinutes =  10)
```
### See also
<a href="https://developer.android.com/reference/android/util/LruCache">Android Lru</a>
