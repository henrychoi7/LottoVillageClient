package com.jjosft.android.lottovillage.activities

import android.os.Bundle
import android.view.View
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.base.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_point_history.*

class PointHistoryActivity : BaseActivity() {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point_history)
        setSupportActionBar(point_history_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStop() {
        super.onStop()
        mCompositeDisposable.clear()
    }

    fun customOnClick(view: View) {

    }
}
