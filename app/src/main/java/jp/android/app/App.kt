package jp.android.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import jp.android.app.domain.use_case.GetAppLocalizeUseCase
import jp.android.app.domain.use_case.GetAppThemeUseCase
import jp.android.app.shared.LocaleService
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var getAppThemeUseCase: GetAppThemeUseCase

    @Inject
    lateinit var getAppLocalizeUseCase: GetAppLocalizeUseCase

//    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(base?.let {
//            LocaleService.updateBaseContextLocale(it, "vn")
//        })
//    }

    override fun onCreate() {
        super.onCreate()
//        configLocalizeApp()
    }

    private fun configLocalizeApp() {
        LocaleService.updateBaseContextLocale(applicationContext, getAppLocalizeUseCase())
    }
}
