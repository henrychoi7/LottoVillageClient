package com.jjosft.android.lottovillage.base

import android.app.Activity
import android.app.Application
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatDialog
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.interfaces.RetrofitInterface
import kotlinx.android.synthetic.main.progress_loading.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by JJSOFT-DESKTOP on 2017-08-13.
 */
open class BaseApplication : Application() {
    companion object {
        private lateinit var baseApplication: BaseApplication
        private lateinit var progressDialog: AppCompatDialog
        private var retrofit: Retrofit? = null
        val MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8")
        const val LOTTO_VILLAGE_PREFERENCES = "LOTTO_VILLAGE_PREFERENCES"
        const val CERTIFIED_NUMBER = "LOTTO_VILLAGE_CERTIFICATED_NUMBER"
        const val KEY_COOKIE = "JJSOFT_LOTTO_VILLAGE_COOKIE"
        const val AUTO_LOGIN = "JJSOFT_LOTTO_VILLAGE_AUTO_LOGIN"
        const val PHONE_NUMBER = "JJSOFT_LOTTO_VILLAGE_PHONE_NUMBER"
        const val PASSWORD = "JJSOFT_LOTTO_VILLAGE_PASSWORD"


        fun getInstance(): BaseApplication {
            return baseApplication
        }

        fun getRetrofitMethod() : RetrofitInterface {
            return BaseApplication.getInstance().getClient().create(RetrofitInterface::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
        Stetho.initializeWithDefaults(this)
    }

    private fun getClient(): Retrofit {
        if (retrofit == null) {
            val client : OkHttpClient = OkHttpClient.Builder()
                    .readTimeout(10000, TimeUnit.MILLISECONDS)
                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
                    .addInterceptor(AddCookiesInterceptor(applicationContext))
                    .addInterceptor(ReceivedCookiesInterceptor(applicationContext))
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()

            retrofit = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }

    fun progressOn(activity: Activity, message: String) {
        if (activity.isFinishing) {
            return
        }

        progressDialog = AppCompatDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.progress_loading)
        progressDialog.text_progress.text = message
        progressDialog.show()
    }

    fun progressSet(message: String) {
        if (!progressDialog.isShowing) {
            return
        }

        progressDialog.text_progress.text = message
    }

    fun progressOff() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}