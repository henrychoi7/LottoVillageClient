package com.jjosft.android.lottovillage.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.base.BaseApplication

/**
 * Created by JJSOFT-DESKTOP on 2017-09-04.
 */
class WinningInfoAdapter(private val mActivity: Activity) : RecyclerView.Adapter<WinningInfoAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val homeTextViewWinningHour: TextView = view.findViewById(R.id.home_text_winning_hour)
        val homeSpinnerWinningHour: Spinner = view.findViewById(R.id.home_spinner_winning_hour)
        val homeTextViewWinningHelp: TextView = view.findViewById(R.id.home_text_winning_help)
        val homeLinearLayoutWinning: LinearLayout = view.findViewById(R.id.home_linear_layout_winning)
        val homeTextViewWinning1: TextView = view.findViewById(R.id.home_text_winning_1)
        val homeTextViewWinning2: TextView = view.findViewById(R.id.home_text_winning_2)
        val homeTextViewWinning3: TextView = view.findViewById(R.id.home_text_winning_3)
        val homeTextViewWinning4: TextView = view.findViewById(R.id.home_text_winning_4)
        val homeTextViewWinning5: TextView = view.findViewById(R.id.home_text_winning_5)
        val homeTextViewWinning6: TextView = view.findViewById(R.id.home_text_winning_6)
        val homeButtonWinning: Button = view.findViewById(R.id.home_button_winning_retrieve)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WinningInfoAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_winning_info, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: WinningInfoAdapter.ViewHolder, position: Int) {
        val hourAdapter: SpinnerAdapter = ArrayAdapter<String>(mActivity, R.layout.spinner_lotto, mActivity.resources.getStringArray(R.array.days))
        holder.homeSpinnerWinningHour.adapter = hourAdapter
        holder.homeSpinnerWinningHour.setSelection(0)
        if (true) {
            holder.homeTextViewWinningHelp.visibility = View.VISIBLE
            holder.homeLinearLayoutWinning.visibility = View.INVISIBLE
        } else {
            holder.homeTextViewWinningHelp.visibility = View.INVISIBLE
            holder.homeLinearLayoutWinning.visibility = View.VISIBLE
            BaseApplication.getInstance().setLottoNumberBackground(holder.homeTextViewWinning1, 1)
            BaseApplication.getInstance().setLottoNumberBackground(holder.homeTextViewWinning2, 11)
            BaseApplication.getInstance().setLottoNumberBackground(holder.homeTextViewWinning3, 21)
            BaseApplication.getInstance().setLottoNumberBackground(holder.homeTextViewWinning4, 31)
            BaseApplication.getInstance().setLottoNumberBackground(holder.homeTextViewWinning5, 41)
            BaseApplication.getInstance().setLottoNumberBackground(holder.homeTextViewWinning6, 2)
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}