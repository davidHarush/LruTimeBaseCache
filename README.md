[![](https://jitpack.io/v/davidHarush/LruTimeBaseCache.svg)](https://jitpack.io/#davidHarush/LruTimeBaseCache)


# LruTimeBaseCache

### Wrapper class for android LruCache this util add expiredTime Feature to the android LruCache class.<br>
- The expire Time In Units of Minutes.
- The defult expire Time velue is 3 Minutes.
- In case of time is expired then the retun velue will be a null.
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
    val myCache : LruTimeBaseCache<Int,String> = LruTimeBaseCache()
    dataCache.put(1,"first")
    dataCache.put(2,"second")
    Log.i(TAG,dataCache.get(1)!!)
```

You can set the expire Time in the constructor, the defult velue is 3 Minutes.

```sh
    val dataCache= LruTimeBaseCache<Int,String>(expiredTimeInMinutes =  10)
```
### See also
<a href="https://developer.android.com/reference/android/util/LruCache">Android Lru</a>
