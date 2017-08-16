package com.jjosft.android.lottovillage.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.jjosft.android.lottovillage.base.BaseActivity
import com.jjosft.android.lottovillage.R


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /*Handler().postDelayed({
            startActivity(Intent(applicationContext, InitActivity::class.java))
            finish()
        }, 3000)*/
    }

    fun customOnClick(view: View) {
        when (view.id) {
            R.id.button_register -> {
                startActivity(Intent(applicationContext, RegisterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
            }
            R.id.button_login -> {
                startActivity(Intent(applicationContext, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
            }
        }
    }
}
