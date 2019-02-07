
## Code Compilation instructions; IDE/Plugin versions expected, dependency management
IDE Version: 3.3 
Kotlin Plugin version : 1.3.20

## Short description explaining architecture and logical modules its comprised of (e.g View, ViewModel...)
The architecture of the application is MvvM using Android’s JetPack architectural library. The overall architecture is as follows:

![untitled diagram](https://user-images.githubusercontent.com/26409185/52424069-5f213a00-2b4d-11e9-9d06-5d0fa6fc8b04.jpg)

## Any 3rd party libraries used and rational
### Android Support Library ,
### Android Architecture Components ,
### Dagger 2 for dependency injection ,
### RxJava 2 for composing asynchronous and event-based programs using observable sequences .
### Retrofit for REST api communication -> The standard library for RESTFul Api calls ,
### Picasso for image loading in the recycler view -> Because it loads images asynchronously and we are able to implement caching for th images ,
### MockWebServer for testing HTTP clients ,
