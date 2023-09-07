package jp.android.app.data.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.android.app.data.BuildConfig
import jp.android.app.data.repository.RepositoryImpl
import jp.android.app.data.source.local.database.DatabaseApi
import jp.android.app.data.source.local.database.DatabaseApiImpl
import jp.android.app.data.source.local.database.config.DatabaseConfig
import jp.android.app.data.source.local.database.config.DatabaseManager
import jp.android.app.data.source.local.database.config.MigrationManager
import jp.android.app.data.source.local.preference.AppPreferences
import jp.android.app.data.source.local.preference.SharedPrefApi
import jp.android.app.data.source.local.preference.SharedPrefApiImpl
import jp.android.app.data.source.remote.api_service.AppApiService
import jp.android.app.data.source.remote.api_service.AuthApiService
import jp.android.app.data.source.remote.api_service.RefreshTokenApiService
import jp.android.app.data.source.remote.middleware.*
import jp.android.app.domain.repository.Repository
import jp.android.app.shared.AppBuildConfig
import jp.android.app.shared.AppCoroutineDispatchers
import jp.android.app.shared.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAppPreferences(
        sharedPrefApi: SharedPrefApi,
        appCoroutineDispatchers: AppCoroutineDispatchers
    ) = AppPreferences(sharedPrefApi, appCoroutineDispatchers)

    @Provides
    @Singleton
    fun provideAppApiService(
        appBuildConfig: AppBuildConfig,
        appPreferences: AppPreferences,
        loggingInterceptor: LoggingInterceptor,
        refreshTokenApiService: RefreshTokenApiService,
    ): AppApiService {
        return Retrofit.Builder()
            .baseUrl(appBuildConfig.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HeaderInterceptor(appPreferences = appPreferences))
                    .addInterceptor(loggingInterceptor)
                    .authenticator(
                        TokenAuthenticator(
                            appPreferences = appPreferences,
                            refreshTokenApiService = refreshTokenApiService,
                        )
                    )
                    .buildOkHttpClient()
            )
            .build()
            .create(AppApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(
        appBuildConfig: AppBuildConfig,
        loggingInterceptor: LoggingInterceptor,
    ): AuthApiService {
        return Retrofit.Builder()
            .baseUrl(appBuildConfig.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .buildOkHttpClient()
            )
            .build()
            .create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRefreshTokenApiService(
        appBuildConfig: AppBuildConfig,
        loggingInterceptor: LoggingInterceptor,
    ): RefreshTokenApiService {
        return Retrofit.Builder()
            .baseUrl(appBuildConfig.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .buildOkHttpClient()
            )
            .build()
            .create(RefreshTokenApiService::class.java)
    }

    private fun OkHttpClient.Builder.buildOkHttpClient(
        connectTimeout: Long = Constants.Network.CONNECT_TIME_OUT_IN_SECONDS,
        writeTimeout: Long = Constants.Network.WRITE_TIME_OUT_IN_SECONDS,
        readTimeout: Long = Constants.Network.READ_TIME_OUT_IN_SECONDS,
    ): OkHttpClient {
        return connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideLoggingInterceptor() = LoggingInterceptor(BuildConfig.DEBUG)

    @Provides
    @Singleton
    fun provideRepository(
        appBuildConfig: AppBuildConfig,
        appApiService: AppApiService,
        authApiService: AuthApiService,
        databaseApi: DatabaseApi,
        appPreferences: AppPreferences,
        appCoroutineDispatchers: AppCoroutineDispatchers,
    ): Repository = RepositoryImpl(
        appApiService = appApiService,
        databaseApi = databaseApi,
        appPreferences = appPreferences,
        authApiService = authApiService,
        appBuildConfig = appBuildConfig,
        appCoroutineDispatchers = appCoroutineDispatchers,
    )

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideSharedPrefApi(@ApplicationContext context: Context, moshi: Moshi): SharedPrefApi =
        SharedPrefApiImpl(context = context, moshi = moshi)

    @Provides
    @Singleton
    fun provideDatabaseApi(databaseManager: DatabaseManager): DatabaseApi =
        DatabaseApiImpl(databaseManager = databaseManager)

    @Provides
    @Singleton
    fun provideDatabaseManager(
        @ApplicationContext context: Context,
    ): DatabaseManager {
        return Room
            .databaseBuilder(
                context,
                DatabaseManager::class.java,
                DatabaseConfig.DATABASE_NAME
            )
            .addMigrations(MigrationManager.MIGRATION_1_2)
            .build()
    }
}
