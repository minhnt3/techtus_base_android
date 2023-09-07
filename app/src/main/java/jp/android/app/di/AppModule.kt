package jp.android.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.android.app.BuildConfig
import jp.android.app.shared.AppBuildConfig
import jp.android.app.shared.AppCoroutineDispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppBuildConfig(): AppBuildConfig = AppBuildConfig(
        versionCode = BuildConfig.VERSION_CODE,
        versionName = BuildConfig.VERSION_NAME,
        flavor = BuildConfig.FLAVOR,
        applicationId = BuildConfig.APPLICATION_ID,
        baseUrl = BuildConfig.BASE_URL,
        isDebug = BuildConfig.DEBUG,
    )

    @Singleton
    @Provides
    fun provideAppCoroutineDispatchers() = AppCoroutineDispatchers()
}
