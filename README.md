# News
[![Build Status](https://travis-ci.com/ZattWine/news.svg?branch=master)](https://travis-ci.com/ZattWine/news)
[![GitHub issues](https://img.shields.io/github/issues/ZattWine/news)](https://github.com/ZattWine/news/issues)
[![GitHub forks](https://img.shields.io/github/forks/ZattWine/news)](https://github.com/ZattWine/news/network)
[![GitHub stars](https://img.shields.io/github/stars/ZattWine/news)](https://github.com/ZattWine/news/stargazers)
[![GitHub license](https://img.shields.io/github/license/ZattWine/news)](https://github.com/ZattWine/news/blob/master/LICENSE)
[![Twitter](https://img.shields.io/twitter/url?style=social&url=https%3A%2F%2Fgithub.com%2FZattWine%2Fnews)](https://twitter.com/intent/tweet?text=Wow:&url=https%3A%2F%2Fgithub.com%2FZattWine%2Fnews)

 API powered by [NewsAPI.org](https://newsapi.org/)
 
 Most of codes are referenced from [Android Kotlin Fundamentals Code Labs](https://codelabs.developers.google.com/android-kotlin-fundamentals/)

# Features
- News source lists
- News lists for each source
- News details

# Libraries
- [Material UI](https://github.com/material-components/material-components-android) for material user interface
- [Navigation](https://github.com/ZattWine/news/blob/972de43c235b93674cfbe7ae4425762720882b73/app/build.gradle#L54) for navigation
- [Lifecycle](https://github.com/ZattWine/news/blob/972de43c235b93674cfbe7ae4425762720882b73/app/build.gradle#L58) for `ViewModel`, `LiveData` and other lifecycle awareness
- [Room](https://github.com/ZattWine/news/blob/972de43c235b93674cfbe7ae4425762720882b73/app/build.gradle#L61) for local cache (database)
- [Coroutines](https://github.com/ZattWine/news/blob/972de43c235b93674cfbe7ae4425762720882b73/app/build.gradle#L65) for job
- [Retrofit](https://github.com/square/retrofit) for networking
- [OkHttp](https://github.com/square/okhttp)for client - user agent
- [Moshi](https://github.com/square/moshi) for json
- [Glide](https://github.com/bumptech/glide) for image loading and caching
- [Timber](https://github.com/JakeWharton/timber) for logging
- [WorkManager](https://github.com/ZattWine/news/blob/972de43c235b93674cfbe7ae4425762720882b73/app/build.gradle#L70) for schedule background work
- [LeakCanary](https://github.com/square/leakcanary/) for detecting memory leaks
- [ThreeTen Android Backport](https://github.com/JakeWharton/ThreeTenABP)

# Todo
- [x] News source lists
- [x] News lists for each source
- [x] News Detail
- [x] About app page
- [x] License page
- [ ] Search everything
- [ ] Notification
- [ ] Open external link
- [ ] Dagger implementation

# License
```
MIT License

Copyright (c) 2020 Zayar Tun

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
