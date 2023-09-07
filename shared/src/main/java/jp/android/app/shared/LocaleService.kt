package jp.android.app.shared

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.Locale

object LocaleService {

    fun updateBaseContextLocale(context: Context, language: String): ContextWrapper {
        val newLocale = Locale(language)
        val localeList = LocaleList(newLocale)
        LocaleList.setDefault(localeList)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResourcesLocalize(context, localeList)
        }
        return updateResourcesLocaleLegacy(context, newLocale)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResourcesLocalize(
        context: Context,
        localeList: LocaleList
    ): ContextWrapper {
        val configuration: Configuration = context.resources.configuration
        configuration.setLocales(localeList)
        configuration.setLayoutDirection(localeList[0])
        return ContextWrapper(context.createConfigurationContext(configuration))
    }

    @Suppress("DEPRECATION")
    private fun updateResourcesLocaleLegacy(
        context: Context,
        locale: Locale
    ): ContextWrapper {
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return ContextWrapper(context)
    }
}

