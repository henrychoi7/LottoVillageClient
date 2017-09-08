package com.jjosft.android.lottovillage.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.adapters.PointHistoryAdapter
import com.jjosft.android.lottovillage.base.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_point_history.*
import kotlinx.android.synthetic.main.content_point_history.*

class PointHistoryActivity : BaseActivity() {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point_history)
        setSupportActionBar(point_history_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val yearAdapter: SpinnerAdapter = ArrayAdapter<String>(applicationContext, R.layout.spinner_lotto, resources.getStringArray(R.array.years))
        point_history_spinner_year.adapter = yearAdapter
        point_history_spinner_year.setSelection(0)

        val monthAdapter: SpinnerAdapter = ArrayAdapter<String>(applicationContext, R.layout.spinner_lotto, resources.getStringArray(R.array.months))
        point_history_spinner_month.adapter = monthAdapter
        point_history_spinner_month.setSelection(0)

        retrievePointHistory()
    }

    override fun onStop() {
        super.onStop()
        mCompositeDisposable.clear()
    }

    fun customOnClick(view: View) {
        when (view.id) {
            R.id.point_history_button_retrieve -> {retrievePointHistory()}
        }
    }

    private fun retrievePointHistory() {
        val pointHistoryAdapter: PointHistoryAdapter = PointHistoryAdapter()
        point_history_recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        point_history_recycler_view.adapter = pointHistoryAdapter
    }
}