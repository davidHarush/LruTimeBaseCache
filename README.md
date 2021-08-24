# LruTimeBaseCache
![GitHub release (latest by date)](https://img.shields.io/github/v/release/davidHarush/LruTimeBaseCache)
![GitHub top language](https://img.shields.io/github/languages/top/davidHarush/LruTimeBaseCache)
![GitHub repo size](https://img.shields.io/github/repo-size/davidHarush/LruTimeBaseCache)
![GitHub issues](https://img.shields.io/github/issues/davidHarush/LruTimeBaseCache)

### A Wrapper class for Android's LruCache which adds a time based expiration functionality.<br>
- The expire Time In Units of Minutes.
- The defult expire Time velue is 3 Minutes.
- When the time is up, the returned value will be null.
- Support kotlin+Java




## Installation.
Step 1. Add the JitPack repository to your build file. <br>
Add it in your root build.gradle at the end of repositories:
```sh
allprojects {
  repositories {
	  ...
	  maven { url 'https://jitpack.io' }
	}
}
```
Step 2. Add the dependency<br>
```sh
dependencies {
  implementation 'com.github.davidHarush:LruTimeBaseCache:1.5'
}

```
For more info :<br>
[![](https://jitpack.io/v/davidHarush/LruTimeBaseCache.svg)](https://jitpack.io/#davidHarush/LruTimeBaseCache)

<br>
<br>


## How to use it.

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
