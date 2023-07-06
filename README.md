# MusicApp

Music App is a promotion application for introducing songs based on modern Android text-stacks 
especially focusing on Jetpack Compose UI and fetching data from the network "Deezer DB API".
allowing users to store liked songs in local DB using RoomDB technology. 


https://www.linkedin.com/feed/update/urn:li:activity:7063872127317291008/

# Tech Stacks

Jetpack Compose: Jetpack Compose is Android's recommended modern toolkit for building native UI. 
It simplifies and accelerates UI development on Android.

Room DB: Room is a persistent library that is part of the Android jetpack. It is built on top of SQLite. 
The room persistent library has many advantages over raw SQLite

Dagger-Hilt: Hilt provides a standard way to incorporate Dagger dependency injection into an Android application. 
The goals of Hilt are: To simplify Dagger-related infrastructure for Android apps. 

MVVM: To stand for Model, View, ViewModel. Model: This holds the data of the application. It cannot directly talk to the View. 
Generally, it's recommended to expose the data to the ViewModel through Observables

Coroutines: To help to manage long-running tasks that might otherwise block the main thread and cause your app to become unresponsive.
Coroutines is our recommended solution for asynchronous programming on Android. Noteworthy features include the following:

•	Lightweight: You can run many coroutines on a single thread due to support for suspension, which doesn't block the thread where the coroutine is running. Suspending saves memory over blocking while supporting many concurrent operations.
•	Fewer memory leaks: Use structured concurrency to run operations within a scope.
•	Built-in cancellation support: Cancellation is propagated automatically through the running coroutine hierarchy.
•	Jetpack integration: Many Jetpack libraries include extensions that provide full coroutines support. Some libraries also provide their own coroutine scope that you can use for structured concurrency.

Retrofit: it is an easy and fast library to retrieve and upload data via a REST-based web service. Retrofit manages the process of receiving, sending, and creating HTTP requests and responses.

# DEMO
https://lnkd.in/dwa7Xiyf


