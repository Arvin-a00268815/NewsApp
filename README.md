# News App

The project is entirely written in Kotlin. Pattern used is MVVM with repository layer containing ROOM database and API calls using Retrofit with asynchronous operation provided by RxJava. Unit test cases and integration test cases are written using MockK and Junit4 libraries. Dependency is handled by Dagger components and modules which helps to create testable code.

The app lists the top headlines using the api provided by https://newsapi.org/ 

Access token is used to fetch the top headlines. The app would list the recent headlines.
