package jp.android.app.common_ui.util

enum class Screen(val route: String, val previousScreen: (() -> Screen)? = null) {

    Login("login"),
    Home("home"),
    MyPage("my_page"),
    User("user"),
    Setting("setting"),
    About("about", { MyPage });

    object Args {
        const val KEY_MOVIE_ID = "movie_id"
        const val KEY_MOVIE_TITLE = "movie_title"
    }
}