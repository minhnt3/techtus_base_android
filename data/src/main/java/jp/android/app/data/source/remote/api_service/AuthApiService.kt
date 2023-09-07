package jp.android.app.data.source.remote.api_service

import jp.android.app.data.model.LoginResponseData
import jp.android.app.data.model.base.BaseResponse
import jp.android.app.data.model.request.LoginRequest
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApiService {

    @GET("api/v1/login")
    suspend fun login(@Query("request") request: LoginRequest): BaseResponse<LoginResponseData>
}