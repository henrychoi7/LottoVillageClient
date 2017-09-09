package com.jjosft.android.lottovillage.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ArrayAdapter
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.adapters.PurchaseHistoryAdapter
import com.jjosft.android.lottovillage.base.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_purchase_history.*
import kotlinx.android.synthetic.main.content_purchase_history.*

class PurchaseHistoryActivity : BaseActivity() {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_history)
        setSupportActionBar(purchase_history_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val yearAdapter = ArrayAdapter<String>(applicationContext, R.layout.spinner_lotto, resources.getStringArray(R.array.years))
        purchase_history_spinner_year.adapter = yearAdapter
        purchase_history_spinner_year.setSelection(0)

        val monthAdapter = ArrayAdapter<String>(applicationContext, R.layout.spinner_lotto, resources.getStringArray(R.array.months))
        purchase_history_spinner_month.adapter = monthAdapter
        purchase_history_spinner_month.setSelection(0)

        retrievePurchaseHistory()
    }

    override fun onStop() {
        super.onStop()
        mCompositeDisposable.clear()
    }

    fun customOnClick(view: View) {
        when (view.id) {
            R.id.purchase_history_button_retrieve -> {retrievePurchaseHistory()}
        }
    }

    private fun retrievePurchaseHistory() {
        val purchaseHistoryAdapter: PurchaseHistoryAdapter = PurchaseHistoryAdapter()
        purchase_history_recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        purchase_history_recycler_view.adapter = purchaseHistoryAdapter
    }
}
