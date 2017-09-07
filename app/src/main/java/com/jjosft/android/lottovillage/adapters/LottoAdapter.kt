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
class LottoAdapter(private val mNumberPrize: String, private val mTotalPrize: String,
                   private val mTotalNumber: String, private val mPerPrize: String) : RecyclerView.Adapter<LottoAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lottoTextViewNumberPrize: TextView = view.findViewById(R.id.lotto_text_number_prize)
        val lottoTextViewTotalPrize: TextView = view.findViewById(R.id.lotto_text_total_prize)
        val lottoTextViewTotalNumber: TextView = view.findViewById(R.id.lotto_text_total_number)
        val lottoTextViewPerPrize: TextView = view.findViewById(R.id.lotto_text_per_prize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LottoAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_lotto_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LottoAdapter.ViewHolder, position: Int) {
        holder.lottoTextViewNumberPrize.text = mNumberPrize
        holder.lottoTextViewTotalPrize.text = mTotalPrize
        holder.lottoTextViewTotalNumber.text = mTotalNumber
        holder.lottoTextViewPerPrize.text = mPerPrize
    }

    override fun getItemCount(): Int {
        return 1
    }
}