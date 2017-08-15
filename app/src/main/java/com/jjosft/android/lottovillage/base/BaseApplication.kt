package com.jjosft.android.lottovillage.base

import android.app.Activity
import android.app.Application
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatDialog
import com.jjosft.android.lottovillage.R
import kotlinx.android.synthetic.main.progress_loading.*


/**
 * Created by JJSOFT-DESKTOP on 2017-08-13.
 */
open class BaseApplication : Application() {
    companion object {
        private lateinit var baseApplication: BaseApplication
        private lateinit var progressDialog: AppCompatDialog
        val PREF_ID = "LOTTO_VILLAGE"
        val CERTIFIED_NUMBER = "LOTTO_VILLAGE_CERTIFICATED_NUMBER"

        fun getInstance(): BaseApplication {
            return baseApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
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