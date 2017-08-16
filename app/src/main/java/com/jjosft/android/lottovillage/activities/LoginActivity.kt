package com.jjosft.android.lottovillage.activities

import android.os.Bundle
import android.view.View
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar_login)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun customOnClick(view: View) {
        when (view.id) {
        }
    }
}
