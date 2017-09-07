package com.jjosft.android.lottovillage.activities

import android.os.Bundle
import android.view.View
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.base.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_participation_history.*

class ParticipationHistoryActivity : BaseActivity() {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participation_history)
        setSupportActionBar(participation_history_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStop() {
        super.onStop()
        mCompositeDisposable.clear()
    }

    fun customOnClick(view: View) {

    }
}
