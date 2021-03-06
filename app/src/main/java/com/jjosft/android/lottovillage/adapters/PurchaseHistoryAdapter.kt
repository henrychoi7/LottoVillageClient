package com.jjosft.android.lottovillage.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jjosft.android.lottovillage.R

/**
 * Created by JJSOFT-DESKTOP on 2017-09-04.
 */
class PurchaseHistoryAdapter() : RecyclerView.Adapter<PurchaseHistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseHistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_purchase_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PurchaseHistoryAdapter.ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 3
    }
}