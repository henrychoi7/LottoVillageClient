package com.jjosft.android.lottovillage.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.jjosft.android.lottovillage.base.BaseActivity
import com.jjosft.android.lottovillage.R


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(applicationContext, InitActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}
