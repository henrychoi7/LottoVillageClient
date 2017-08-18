package com.jjosft.android.lottovillage.base

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by JJSOFT-DESKTOP on 2017-08-17.
 */
class AddCookiesInterceptor(context: Context) : Interceptor {
    private val mSharedPreferences: SharedPreferences = context.getSharedPreferences(BaseApplication.LOTTO_VILLAGE_PREFERENCES, Context.MODE_PRIVATE)
    override fun intercept(chain: Interceptor.Chain?): Response {
        val builder : Request.Builder = chain!!.request().newBuilder()

        val cookiesStringSet : HashSet<String>? = mSharedPreferences.getStringSet(BaseApplication.KEY_COOKIE, HashSet()) as HashSet<String>?
        if (cookiesStringSet != null) {
            for (cookie in cookiesStringSet) {
                builder.addHeader("cookie", cookie)
            }
        }
        return chain.proceed(builder.build())
    }
}