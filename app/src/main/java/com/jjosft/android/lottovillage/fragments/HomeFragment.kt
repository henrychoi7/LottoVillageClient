package com.jjosft.android.lottovillage.fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
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
import io.reactivex.Observable
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val observableParticipationData1: Observable<Model.ParticipationResponse> = BaseApplication.getInstance().getRetrofitMethod().getDetailsOfParticipation("1", "", "", false)
        val observableParticipationData2: Observable<Model.ParticipationResponse> = BaseApplication.getInstance().getRetrofitMethod().getDetailsOfParticipation("2", "", "", false)
        val observableParticipationData3: Observable<Model.ParticipationResponse> = BaseApplication.getInstance().getRetrofitMethod().getDetailsOfParticipation("3", "", "", false)
        val mergedData = Observable.merge(observableParticipationData1, observableParticipationData2, observableParticipationData3)

        mergedData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Model.ParticipationResponse> {
                    override fun onSubscribe(d: Disposable) {
                        mCompositeDisposable.add(d)
                        BaseApplication.getInstance().progressOn(activity, getString(R.string.request_detail_of_participation))
                    }

                    override fun onNext(t: Model.ParticipationResponse) {
                        if (t.isSuccess) {
                            val participationData: Model.DetailsOfParticipation = t.detailsOfParticipation[0]
                            val participationAdapter = ParticipationAdapter(activity, participationData.eventType, participationData)
                            when (participationData.eventType) {
                                "1" -> {
                                    view.main_recycler_view_one_hour.layoutManager = LinearLayoutManager(activity)
                                    view.main_recycler_view_one_hour.adapter = participationAdapter
                                }
                                "2" -> {
                                    view.main_recycler_view_six_hour.layoutManager = LinearLayoutManager(activity)
                                    view.main_recycler_view_six_hour.adapter = participationAdapter
                                }
                                "3" -> {
                                    view.main_recycler_view_twelve_hour.layoutManager = LinearLayoutManager(activity)
                                    view.main_recycler_view_twelve_hour.adapter = participationAdapter
                                }
                            }
                        } else {
                            if (t.errorMessage == getString(R.string.unmatched_token_value)) Toast.makeText(activity, getString(R.string.request_login), Toast.LENGTH_SHORT).show()
                            else {
                                val participationData: Model.DetailsOfParticipation = Model.DetailsOfParticipation(
                                        t.detailsOfParticipation[0].eventType,1, 2, 3,
                                        4, 5, 6, "")
                                val participationAdapter = ParticipationAdapter(activity, participationData.eventType, participationData)
                                when (participationData.eventType) {
                                    "1" -> {
                                        view.main_recycler_view_one_hour.layoutManager = LinearLayoutManager(activity)
                                        view.main_recycler_view_one_hour.adapter = participationAdapter
                                    }
                                    "2" -> {
                                        view.main_recycler_view_six_hour.layoutManager = LinearLayoutManager(activity)
                                        view.main_recycler_view_six_hour.adapter = participationAdapter
                                    }
                                    "3" -> {
                                        view.main_recycler_view_twelve_hour.layoutManager = LinearLayoutManager(activity)
                                        view.main_recycler_view_twelve_hour.adapter = participationAdapter
                                    }
                                }
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(activity, "실패 ${e.message}", Toast.LENGTH_SHORT).show()
                        BaseApplication.getInstance().progressOff()
                        mCompositeDisposable.clear()
                    }

                    override fun onComplete() {
                        BaseApplication.getInstance().progressOff()
                        mCompositeDisposable.clear()
                    }
                })

        return view
    }

    override fun onStop() {
        mCompositeDisposable.clear()
        super.onStop()
    }
}