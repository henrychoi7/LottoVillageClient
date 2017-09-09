package com.jjosft.android.lottovillage.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ArrayAdapter
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.adapters.ParticipationHistoryAdapter
import com.jjosft.android.lottovillage.base.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_participation_history.*
import kotlinx.android.synthetic.main.content_participation_history.*

class ParticipationHistoryActivity : BaseActivity() {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participation_history)
        setSupportActionBar(participation_history_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val yearAdapter = ArrayAdapter<String>(applicationContext, R.layout.spinner_lotto, resources.getStringArray(R.array.years))
        participation_history_spinner_year.adapter = yearAdapter
        participation_history_spinner_year.setSelection(0)

        val monthAdapter = ArrayAdapter<String>(applicationContext, R.layout.spinner_lotto, resources.getStringArray(R.array.months))
        participation_history_spinner_month.adapter = monthAdapter
        participation_history_spinner_month.setSelection(0)

        val dayAdapter = ArrayAdapter<String>(applicationContext, R.layout.spinner_lotto, resources.getStringArray(R.array.days))
        participation_history_spinner_day.adapter = dayAdapter
        participation_history_spinner_day.setSelection(0)

        retrieveParticipationHistory()
    }

    override fun onStop() {
        super.onStop()
        mCompositeDisposable.clear()
    }

    fun customOnClick(view: View) {
        when (view.id) {
            R.id.participation_history_button_retrieve -> {retrieveParticipationHistory()}
        }
    }

    private fun retrieveParticipationHistory() {
        val participationHistoryAdapter: ParticipationHistoryAdapter = ParticipationHistoryAdapter()
        participation_history_recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        participation_history_recycler_view.adapter = participationHistoryAdapter
    }
}
