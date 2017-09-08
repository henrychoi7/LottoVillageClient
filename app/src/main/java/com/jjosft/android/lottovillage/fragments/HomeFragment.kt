package com.jjosft.android.lottovillage.fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.adapters.ParticipationAdapter
import com.jjosft.android.lottovillage.adapters.WinningInfoAdapter
import com.jjosft.android.lottovillage.base.BaseApplication
import com.jjosft.android.lottovillage.model.Model
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * Created by JJSOFT-DESKTOP on 2017-09-03.
 */
class HomeFragment : Fragment() {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private fun makeEmptyParticipationData(eventType: String): Model.DetailsOfParticipation = Model.DetailsOfParticipation(
            eventType, 1, 2, 3,
            4, 5, 6, "")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val yearAdapter: SpinnerAdapter = ArrayAdapter<String>(activity, R.layout.spinner_lotto, resources.getStringArray(R.array.years))
        view.home_spinner_year.adapter = yearAdapter
        view.home_spinner_year.setSelection(0)

        val monthAdapter: SpinnerAdapter = ArrayAdapter<String>(activity, R.layout.spinner_lotto, resources.getStringArray(R.array.months))
        view.home_spinner_month.adapter = monthAdapter
        view.home_spinner_month.setSelection(0)

        val dayAdapter: SpinnerAdapter = ArrayAdapter<String>(activity, R.layout.spinner_lotto, resources.getStringArray(R.array.days))
        view.home_spinner_day.adapter = dayAdapter
        view.home_spinner_day.setSelection(0)

        retrieveMyPoint(view)

        return view
    }

    override fun onStop() {
        mCompositeDisposable.clear()
        super.onStop()
    }

    /*private fun retrieveWinningInfo(view: View) {
        BaseApplication.getInstance().getRetrofitMethod().getDetailsOfAllParticipation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Model.ParticipationResponse> {
                    override fun onSubscribe(d: Disposable) {
                        mCompositeDisposable.add(d)
                        BaseApplication.getInstance().progressOn(activity, getString(R.string.request_detail_of_participation))
                    }

                    override fun onNext(t: Model.ParticipationResponse) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(activity, "실패 ${e.message}", Toast.LENGTH_SHORT).show()
                        BaseApplication.getInstance().progressOff()
                        mCompositeDisposable.clear()
                    }

                    override fun onComplete() {
                        retrieveMyPoint(view)
                    }
                })
    }*/

    private fun retrieveMyPoint(view: View) {
        BaseApplication.getInstance().getRetrofitMethod().getMyPont()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Model.SingleIntResponse> {
                    override fun onSubscribe(d: Disposable) {
                        mCompositeDisposable.add(d)
                        BaseApplication.getInstance().progressOn(activity, getString(R.string.request_my_point))
                    }

                    override fun onNext(t: Model.SingleIntResponse) {
                        view.home_text_my_point.text = t.results.toString()

                        val winningInfoAdapter = WinningInfoAdapter(activity)
                        view.home_recycler_view_winning_info.layoutManager = LinearLayoutManager(activity)
                        view.home_recycler_view_winning_info.adapter = winningInfoAdapter
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(activity, "실패 ${e.message}", Toast.LENGTH_SHORT).show()
                        BaseApplication.getInstance().progressOff()
                        mCompositeDisposable.clear()
                    }

                    override fun onComplete() {
                        retrieveDetailsOfAllParticipation(view)
                    }
                })
    }

    private fun retrieveDetailsOfAllParticipation(view: View) {
        BaseApplication.getInstance().getRetrofitMethod().getDetailsOfAllParticipation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Model.ParticipationResponse> {
                    override fun onSubscribe(d: Disposable) {
                        mCompositeDisposable.add(d)
                        BaseApplication.getInstance().progressOn(activity, getString(R.string.request_detail_of_participation))
                    }

                    override fun onNext(t: Model.ParticipationResponse) {
                        if (t.isSuccess) {
                            val participationDataArrayList: ArrayList<Model.DetailsOfParticipation> = t.detailsOfParticipation
                            when (participationDataArrayList.size) {
                                0 -> {
                                    participationDataArrayList.add(makeEmptyParticipationData("1"))
                                    participationDataArrayList.add(makeEmptyParticipationData("2"))
                                    participationDataArrayList.add(makeEmptyParticipationData("3"))
                                }
                                1 -> {
                                    when (participationDataArrayList[0].eventType) {
                                        "1" -> {
                                            participationDataArrayList.add(1, makeEmptyParticipationData("2"))
                                            participationDataArrayList.add(2, makeEmptyParticipationData("3"))
                                        }
                                        "2" -> {
                                            participationDataArrayList.add(0, makeEmptyParticipationData("1"))
                                            participationDataArrayList.add(2, makeEmptyParticipationData("3"))
                                        }
                                        "3" -> {
                                            participationDataArrayList.add(0, makeEmptyParticipationData("1"))
                                            participationDataArrayList.add(1, makeEmptyParticipationData("2"))
                                        }
                                    }
                                }
                                2 -> {
                                    when (participationDataArrayList[0].eventType) {
                                        "1" -> {
                                            if (participationDataArrayList[1].eventType == "2") {
                                                participationDataArrayList.add(2, makeEmptyParticipationData("3"))
                                            } else {
                                                participationDataArrayList.add(1, makeEmptyParticipationData("2"))
                                            }
                                        }
                                        "2" -> {
                                            participationDataArrayList.add(0, makeEmptyParticipationData("1"))
                                        }
                                    }
                                }
                            }
                            val participationAdapter = ParticipationAdapter(activity, participationDataArrayList)
                            view.home_recycler_view_participation_hour.layoutManager = LinearLayoutManager(activity)
                            view.home_recycler_view_participation_hour.adapter = participationAdapter
                        } else {
                            if (t.errorMessage == getString(R.string.unmatched_token_value)) Toast.makeText(activity, getString(R.string.request_login), Toast.LENGTH_SHORT).show()
                            else {
                                val participationDataArrayList: ArrayList<Model.DetailsOfParticipation> = ArrayList()
                                participationDataArrayList.add(makeEmptyParticipationData("1"))
                                participationDataArrayList.add(makeEmptyParticipationData("2"))
                                participationDataArrayList.add(makeEmptyParticipationData("3"))
                                val participationAdapter = ParticipationAdapter(activity, participationDataArrayList)
                                view.home_recycler_view_participation_hour.layoutManager = LinearLayoutManager(activity)
                                view.home_recycler_view_participation_hour.adapter = participationAdapter
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
    }
}