package com.jjosft.android.lottovillage.fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.adapters.ParticipationAdapter
import com.jjosft.android.lottovillage.base.BaseApplication
import com.jjosft.android.lottovillage.model.Model
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.view.*
import okhttp3.RequestBody
import org.json.JSONObject

/**
 * Created by JJSOFT-DESKTOP on 2017-09-03.
 */
class HomeFragment : Fragment() {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mView = inflater.inflate(R.layout.fragment_home, container, false)
        detailsOfParticipation("1", isContinued = true)
        detailsOfParticipation("2", isContinued = true)
        detailsOfParticipation("3", isContinued = false)
        return mView
    }

    override fun onStop() {
        mCompositeDisposable.clear()
        super.onStop()
    }

    private fun detailsOfParticipation(eventType: String, eventDate: String = "", eventNumber: String = "", isConfirmedStatus: Boolean = false, isContinued: Boolean = false) {
        BaseApplication.getRetrofitMethod().getDetailsOfParticipation(eventType, eventDate, eventNumber, isConfirmedStatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Model.ResultResponse> {
                    override fun onSubscribe(d: Disposable) {
                        mCompositeDisposable.add(d)
                        BaseApplication.getInstance().progressOn(activity, "$eventType 회차 ${getString(R.string.request_detail_of_participation)}")
                    }

                    override fun onNext(t: Model.ResultResponse) {
                        if (t.isSuccess) {
                            val participationData: Model.DetailsOfParticipation = t.detailsOfParticipation[0]
                            val participationAdapter = ParticipationAdapter(activity, eventType, participationData)
                            when (eventType) {
                                "1" -> {
                                    mView.main_recycler_view_one_hour.layoutManager = LinearLayoutManager(activity)
                                    mView.main_recycler_view_one_hour.adapter = participationAdapter
                                }
                                "2" -> {
                                    mView.main_recycler_view_six_hour.layoutManager = LinearLayoutManager(activity)
                                    mView.main_recycler_view_six_hour.adapter = participationAdapter
                                }
                                "3" -> {
                                    mView.main_recycler_view_twelve_hour.layoutManager = LinearLayoutManager(activity)
                                    mView.main_recycler_view_twelve_hour.adapter = participationAdapter
                                }
                            }
                        } else {
                            if (t.errorMessage == getString(R.string.unmatched_token_value)) Toast.makeText(activity, getString(R.string.request_login), Toast.LENGTH_SHORT).show()
                            else {
                                val participationData: Model.DetailsOfParticipation = Model.DetailsOfParticipation(1, 2, 3,
                                        4, 5, 6, "")
                                val participationAdapter = ParticipationAdapter(activity, eventType, participationData)
                                when (eventType) {
                                    "1" -> {
                                        mView.main_recycler_view_one_hour.layoutManager = LinearLayoutManager(activity)
                                        mView.main_recycler_view_one_hour.adapter = participationAdapter
                                    }
                                    "2" -> {
                                        mView.main_recycler_view_six_hour.layoutManager = LinearLayoutManager(activity)
                                        mView.main_recycler_view_six_hour.adapter = participationAdapter
                                    }
                                    "3" -> {
                                        mView.main_recycler_view_twelve_hour.layoutManager = LinearLayoutManager(activity)
                                        mView.main_recycler_view_twelve_hour.adapter = participationAdapter
                                    }
                                }
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(activity, "실패 ${e.message}", Toast.LENGTH_SHORT).show()
                        BaseApplication.getInstance().progressOff()
                    }

                    override fun onComplete() {
                        if (!isContinued) {
                            BaseApplication.getInstance().progressOff()
                        }
                    }
                })
    }
}