package com.jjosft.android.lottovillage.activities

import android.os.Bundle
import android.os.Handler
import com.jjosft.android.lottovillage.base.BaseActivity
import com.jjosft.android.lottovillage.R

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressOn(getString(R.string.loading))

        Handler().postDelayed({
            progressSet(getString(R.string.app_name))

            progressOff()
        }, 2000)

    }
}
