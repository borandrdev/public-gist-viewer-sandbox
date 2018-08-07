Public gists viewer sandbox app.
Displays list of public gists with details (https://developer.github.com/v3/gists/).
The app consists of two screens: list of gists with pagination and single gist details screen.

Used: MVVM (ViewModel/LiveData), Dagger2, Retrofit, Gson, Glide

TODOs:
- add RxJava usage
- add error handling for single gist details screen
- shared elevent transition animation
- unit tests
- ui tests
- Master-detail fragment on single screen for tablets
- Navigation component (Cicerone or Google Navigation)
- ContentLoadingProgressbar