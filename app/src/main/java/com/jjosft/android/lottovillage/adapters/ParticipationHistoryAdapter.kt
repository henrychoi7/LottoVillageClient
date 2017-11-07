package com.jjosft.android.lottovillage.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.base.BaseApplication
import com.jjosft.android.lottovillage.model.Model

/**
 * Created by JJSOFT-DESKTOP on 2017-09-04.
 */
class ParticipationHistoryAdapter(private val mContext: Context, private val mParticipationHistoryArrayList: ArrayList<Model.DetailsOfParticipationHistory>) : RecyclerView.Adapter<ParticipationHistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val participationHistoryTextViewTitle: TextView = view.findViewById(R.id.participation_history_text_title)
        val participationHistoryTextViewEventDateHour: TextView = view.findViewById(R.id.participation_history_text_event_date_hour)
        val participationHistoryTextViewParticipation_1: TextView = view.findViewById(R.id.participation_history_text_participation_1)
        val participationHistoryTextViewParticipation_2: TextView = view.findViewById(R.id.participation_history_text_participation_2)
        val participationHistoryTextViewParticipation_3: TextView = view.findViewById(R.id.participation_history_text_participation_3)
        val participationHistoryTextViewParticipation_4: TextView = view.findViewById(R.id.participation_history_text_participation_4)
        val participationHistoryTextViewParticipation_5: TextView = view.findViewById(R.id.participation_history_text_participation_5)
        val participationHistoryTextViewParticipation_6: TextView = view.findViewById(R.id.participation_history_text_participation_6)
        val participationHistoryTextViewWinnerRate: TextView = view.findViewById(R.id.participation_history_text_winner_rate)
        val participationHistoryTextViewWinnerPrize: TextView = view.findViewById(R.id.participation_history_text_winner_prize)
        val participationHistoryTextViewParticipationTime: TextView = view.findViewById(R.id.participation_history_text_participating_time)
        val participationHistoryTextViewWinningTime: TextView = view.findViewById(R.id.participation_history_text_winning_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipationHistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_participation_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParticipationHistoryAdapter.ViewHolder, position: Int) {
        val participationHistoryData: Model.DetailsOfParticipationHistory = mParticipationHistoryArrayList[position]
        when (participationHistoryData.eventType) {
            "1" -> {holder.participationHistoryTextViewTitle.text = mContext.getString(R.string.one_hour_lotto_number)}
            "2" -> {holder.participationHistoryTextViewTitle.text = mContext.getString(R.string.six_hour_lotto_number)}
            "3" -> {holder.participationHistoryTextViewTitle.text = mContext.getString(R.string.twelve_hour_lotto_number)}
        }
        holder.participationHistoryTextViewEventDateHour.text = participationHistoryData.eventDateHour
        holder.participationHistoryTextViewWinnerRate.text = participationHistoryData.winningRate
        BaseApplication.getInstance().setLottoNumberBackground(holder.participationHistoryTextViewParticipation_1, participationHistoryData.winningNumber1)
        BaseApplication.getInstance().setLottoNumberBackground(holder.participationHistoryTextViewParticipation_2, participationHistoryData.winningNumber2)
        BaseApplication.getInstance().setLottoNumberBackground(holder.participationHistoryTextViewParticipation_3, participationHistoryData.winningNumber3)
        BaseApplication.getInstance().setLottoNumberBackground(holder.participationHistoryTextViewParticipation_4, participationHistoryData.winningNumber4)
        BaseApplication.getInstance().setLottoNumberBackground(holder.participationHistoryTextViewParticipation_5, participationHistoryData.winningNumber5)
        BaseApplication.getInstance().setLottoNumberBackground(holder.participationHistoryTextViewParticipation_6, participationHistoryData.winningNumber6)
        holder.participationHistoryTextViewWinnerPrize.text = participationHistoryData.prize.toString()
        holder.participationHistoryTextViewParticipationTime.text = participationHistoryData.participatingTime
        holder.participationHistoryTextViewWinningTime.text = participationHistoryData.winningTime
    }

    override fun getItemCount(): Int {
        return mParticipationHistoryArrayList.size
    }
}