package jp.android.app.shared

object Constants {
    object Network {
        const val CONNECT_TIME_OUT_IN_SECONDS = 30L
        const val WRITE_TIME_OUT_IN_SECONDS = 30L
        const val READ_TIME_OUT_IN_SECONDS = 30L
        const val RETRY_TIMES = 3
        const val RETRY_INITIAL_DELAY = 100L
        const val RETRY_MAX_DELAY = 1000L
        const val RETRY_FACTOR = 2.0
    }

    object Paging {
        const val INITIAL_PAGE = 0
        const val PAGE_SIZE = 10
    }

    const val LANGUAGE_DEFAULT = "en"
}
