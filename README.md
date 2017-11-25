# Engineer.ai Assignment

[![APK](https://img.shields.io/badge/Download%20APK-v1.0-brightgreen.svg)](https://github.com/hrishikesh-kadam/engineer.ai-assignment/raw/master/Engineer.ai%20Assignment.apk)

Engineer.ai aka SqauredSD assignment for Udacity's Propel 17th November 2017 Bangalore event.

### Problem Statement

<img src="https://github.com/hrishikesh-kadam/engineer.ai-assignment/raw/master/screenshots/Engineer.ai%20Assignment%20-%20Problem%20Statement.jpg">

### Components used
- FlexboxLayout
- Retrofit
- Gson
- AsyncTaskLoader
- RecyclerView
- Butterknife
- Picasso

### Usage

To optimise more pagination configuration edit following constants in UserAdapter.java -

```java
    public static final int LOAD_MORE_OFFSET = 5;
    public static final int LIMIT = 10;
```

LOAD_MORE_OFFSET is the number of items left before already loaded userArrayList to hit loadMore callback.

### Screenshots

<img src="https://github.com/hrishikesh-kadam/engineer.ai-assignment/raw/master/screenshots/Screenshot_20171126-000519.jpg" width="240" height="493">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://github.com/hrishikesh-kadam/engineer.ai-assignment/raw/master/screenshots/Screenshot_20171126-000605.jpg" width="240" height="493">