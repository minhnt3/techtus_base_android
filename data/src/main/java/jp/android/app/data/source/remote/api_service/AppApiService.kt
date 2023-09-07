package jp.android.app.data.source.remote.api_service

import jp.android.app.data.model.MovieData
import jp.android.app.data.model.base.BaseListResponse
import retrofit2.http.GET

interface AppApiService {

    @GET("api/v1/movie")
    suspend fun getMovies(): BaseListResponse<MovieData>
}
