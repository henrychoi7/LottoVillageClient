package com.jjosft.android.lottovillage.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.base.BaseApplication
import com.jjosft.android.lottovillage.fragments.HomeFragment
import com.jjosft.android.lottovillage.model.Model
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import org.json.JSONObject


/**
 * Created by JJSOFT-DESKTOP on 2017-09-04.
 */
class ParticipationAdapter(private val mActivity: Activity, private val mEventType: String, private var mParticipationData: Model.DetailsOfParticipation) : RecyclerView.Adapter<ParticipationAdapter.ViewHolder>() {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mainTextViewTitle: TextView = view.findViewById(R.id.main_text_participation_title)
        val mainTextViewHelp: TextView = view.findViewById(R.id.main_text_participation_help)
        val mainLinearLayoutParticipation: LinearLayout = view.findViewById(R.id.main_linear_layout_participation)
        val mainTextViewParticipation1: TextView = view.findViewById(R.id.main_text_participation_1)
        val mainTextViewParticipation2: TextView = view.findViewById(R.id.main_text_participation_2)
        val mainTextViewParticipation3: TextView = view.findViewById(R.id.main_text_participation_3)
        val mainTextViewParticipation4: TextView = view.findViewById(R.id.main_text_participation_4)
        val mainTextViewParticipation5: TextView = view.findViewById(R.id.main_text_participation_5)
        val mainTextViewParticipation6: TextView = view.findViewById(R.id.main_text_participation_6)
        val mainTextViewParticipatingTime: TextView = view.findViewById(R.id.main_text_participating_time)
        val mainButtonParticipation: Button = view.findViewById(R.id.main_button_participation_lotto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipationAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_participation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParticipationAdapter.ViewHolder, position: Int) {
        when (mEventType) {
            "1" -> {
                holder.mainTextViewTitle.text = mActivity.getString(R.string.one_hour_lotto_number)
                holder.mainButtonParticipation.setOnClickListener({ participation("1") })
            }
            "2" -> {
                holder.mainTextViewTitle.text = mActivity.getString(R.string.six_hour_lotto_number)
                holder.mainButtonParticipation.setOnClickListener({ participation("2") })
            }
            "3" -> {
                holder.mainTextViewTitle.text = mActivity.getString(R.string.twelve_hour_lotto_number)
                holder.mainButtonParticipation.setOnClickListener({ participation("3") })
            }
        }
        if (mParticipationData.participatingTime == "") {
            holder.mainTextViewHelp.visibility = View.VISIBLE
            holder.mainLinearLayoutParticipation.visibility = View.INVISIBLE
        } else {
            holder.mainTextViewHelp.visibility = View.INVISIBLE
            holder.mainLinearLayoutParticipation.visibility = View.VISIBLE
            holder.mainButtonParticipation.isEnabled = false
            setLottoNumberBackground(holder.mainTextViewParticipation1, mParticipationData.winningNumber1)
            setLottoNumberBackground(holder.mainTextViewParticipation2, mParticipationData.winningNumber2)
            setLottoNumberBackground(holder.mainTextViewParticipation3, mParticipationData.winningNumber3)
            setLottoNumberBackground(holder.mainTextViewParticipation4, mParticipationData.winningNumber4)
            setLottoNumberBackground(holder.mainTextViewParticipation5, mParticipationData.winningNumber5)
            setLottoNumberBackground(holder.mainTextViewParticipation6, mParticipationData.winningNumber6)
        }
        holder.mainTextViewParticipatingTime.text = mParticipationData.participatingTime
    }

    override fun getItemCount(): Int {
        return 1
    }

    private fun setLottoNumberBackground(targetTextView: TextView, lottoNumber: Int) {
        targetTextView.text = lottoNumber.toString()
        when (lottoNumber) {
            in 1..10 -> targetTextView.setBackgroundResource(R.drawable.attr_lotto_number_background_yellow)
            in 11..20 -> targetTextView.setBackgroundResource(R.drawable.attr_lotto_number_background_blue)
            in 21..30 -> targetTextView.setBackgroundResource(R.drawable.attr_lotto_number_background_red)
            in 31..40 -> targetTextView.setBackgroundResource(R.drawable.attr_lotto_number_background_grey)
            in 41..45 -> targetTextView.setBackgroundResource(R.drawable.attr_lotto_number_background_green)
        }
    }

    private fun detailsOfParticipation(eventType: String, eventDate: String = "", eventNumber: String = "", isConfirmedStatus: Boolean = false, isContinued: Boolean = false) {
        BaseApplication.getRetrofitMethod().getDetailsOfParticipation(eventType, eventDate, eventNumber, isConfirmedStatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Model.ResultResponse> {
                    override fun onSubscribe(d: Disposable) {
                        mCompositeDisposable.add(d)
                        BaseApplication.getInstance().progressOn(mActivity, "$eventType 회차 ${mActivity.getString(R.string.request_detail_of_participation)}")
                    }

                    override fun onNext(t: Model.ResultResponse) {
                        if (t.isSuccess) {
                            mParticipationData = t.detailsOfParticipation[0]
                        } else {
                            if (t.errorMessage == mActivity.getString(R.string.unmatched_token_value)) Toast.makeText(mActivity, mActivity.getString(R.string.request_login), Toast.LENGTH_SHORT).show()
                            else {
                                mParticipationData = Model.DetailsOfParticipation(1, 2, 3,
                                        4, 5, 6, "")
                            }
                        }
                        notifyItemChanged(0)
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(mActivity, "실패 ${e.message}", Toast.LENGTH_SHORT).show()
                        BaseApplication.getInstance().progressOff()
                        mCompositeDisposable.clear()
                    }

                    override fun onComplete() {
                        if (!isContinued) BaseApplication.getInstance().progressOff()
                        mCompositeDisposable.clear()
                    }
                })
    }

    private fun participation(eventType: String) {
        val jsonObject = JSONObject()
        jsonObject.put("event_type", eventType)

        BaseApplication.getRetrofitMethod().postParticipation(RequestBody.create(BaseApplication.MEDIA_TYPE_JSON, jsonObject.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Model.DefaultResponse> {
                    override fun onSubscribe(d: Disposable) {
                        mCompositeDisposable.add(d)
                        BaseApplication.getInstance().progressOn(mActivity, "$eventType 회차 ${mActivity.getString(R.string.participating_lotto)}")
                    }

                    override fun onNext(t: Model.DefaultResponse) {
                        if (t.isSuccess) {
                            Toast.makeText(mActivity, "로또참여 성공", Toast.LENGTH_SHORT).show()
                        } else {
                            if (t.errorMessage == mActivity.getString(R.string.unmatched_token_value)) {
                                Toast.makeText(mActivity, mActivity.getString(R.string.request_login), Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(mActivity, t.errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(mActivity, "실패 ${e.message}", Toast.LENGTH_SHORT).show()
                        BaseApplication.getInstance().progressOff()
                        mCompositeDisposable.clear()
                    }

                    override fun onComplete() {
                        //BaseApplication.getInstance().progressOff()
                        detailsOfParticipation(eventType, isContinued = false)
                    }
                })
    }
}