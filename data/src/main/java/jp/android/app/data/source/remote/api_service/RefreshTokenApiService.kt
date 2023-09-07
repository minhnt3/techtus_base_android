package jp.android.app.data.source.remote.api_service

import jp.android.app.data.model.LoginResponseData
import jp.android.app.data.model.base.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RefreshTokenApiService {
    @POST("api/v1/refresh_tokens")
    @FormUrlEncoded
    suspend fun refreshToken(
        @Field("refresh_token") refreshToken: String,
    ): BaseResponse<LoginResponseData>
}
