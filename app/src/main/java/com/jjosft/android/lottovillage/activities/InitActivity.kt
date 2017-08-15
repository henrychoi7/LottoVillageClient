package com.jjosft.android.lottovillage.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jjosft.android.lottovillage.R


class InitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)
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
