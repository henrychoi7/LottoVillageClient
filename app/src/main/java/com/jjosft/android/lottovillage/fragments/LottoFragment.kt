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
import com.jjosft.android.lottovillage.adapters.LottoAdapter
import com.jjosft.android.lottovillage.base.BaseApplication
import com.jjosft.android.lottovillage.model.Model
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_lotto.view.*

/**
 * Created by JJSOFT-DESKTOP on 2017-09-03.
 */
class LottoFragment : Fragment() {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_lotto, container, false)
        //view.lotto_button_retrieve.setOnClickListener({retrieveRealLotto(view, view.lotto_spinner_rounds.selectedItem.toString())})
        prepareLottoRounds(view)
        return view
    }

    override fun onStop() {
        mCompositeDisposable.clear()
        super.onStop()
    }

    private fun prepareLottoRounds(view: View) {
        BaseApplication.getInstance().getRetrofitMethod().getLottoRounds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Model.SingleStringArrayListResponse> {
                    override fun onSubscribe(d: Disposable) {
                        mCompositeDisposable.add(d)
                        BaseApplication.getInstance().progressOn(activity, getString(R.string.request_lotto_rounds))
                    }

                    override fun onNext(t: Model.SingleStringArrayListResponse) {
                        if (t.isSuccess) {
                            val lottoRoundsAdapter: SpinnerAdapter = ArrayAdapter(activity, R.layout.spinner_lotto, t.results.singleStringArrayList)
                            //view.lotto_spinner_rounds.adapter = lottoRoundsAdapter
                            //view.lotto_spinner_rounds.setSelection(0)
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(activity, "실패 ${e.message}", Toast.LENGTH_SHORT).show()
                        BaseApplication.getInstance().progressOff()
                        mCompositeDisposable.clear()
                    }

                    override fun onComplete() {
                        //retrieveRealLotto(view, view.lotto_spinner_rounds.selectedItem.toString())
                        retrieveRealLotto(view, "770")
                    }
                })
    }

    private fun retrieveRealLotto(view: View, lottoRounds: String) {
        BaseApplication.getInstance().getRetrofitMethod().getDetailsOfLotto(lottoRounds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Model.LottoResponse> {
                    override fun onSubscribe(d: Disposable) {
                        mCompositeDisposable.add(d)
                        BaseApplication.getInstance().progressOn(activity, getString(R.string.request_detail_of_lotto))
                    }

                    override fun onNext(t: Model.LottoResponse) {
                        if (t.isSuccess) {
                            val lottoData: Model.DetailsOfLotto = t.detailsOfLotto[0]
                            /*view.lotto_text_winning_date.text = lottoData.winningDate
                            view.lotto_text_real_lotto_rounds.text = lottoRounds
                            BaseApplication.getInstance().setLottoNumberBackground(view.lotto_text_real_lotto_1, lottoData.winningNumber1)
                            BaseApplication.getInstance().setLottoNumberBackground(view.lotto_text_real_lotto_2, lottoData.winningNumber2)
                            BaseApplication.getInstance().setLottoNumberBackground(view.lotto_text_real_lotto_3, lottoData.winningNumber3)
                            BaseApplication.getInstance().setLottoNumberBackground(view.lotto_text_real_lotto_4, lottoData.winningNumber4)
                            BaseApplication.getInstance().setLottoNumberBackground(view.lotto_text_real_lotto_5, lottoData.winningNumber5)
                            BaseApplication.getInstance().setLottoNumberBackground(view.lotto_text_real_lotto_6, lottoData.winningNumber6)
                            BaseApplication.getInstance().setLottoNumberBackground(view.lotto_text_real_lotto_bonus, lottoData.bonusNumber)*/

                            /*view.lotto_recycler_view_first_prize.layoutManager = LinearLayoutManager(activity)
                            view.lotto_recycler_view_first_prize.adapter =
                                    LottoAdapter(getString(R.string.first_prize), lottoData.totalPrize1,
                                            lottoData.totalNumber1, lottoData.perPrize1)

                            view.lotto_recycler_view_second_prize.layoutManager = LinearLayoutManager(activity)
                            view.lotto_recycler_view_second_prize.adapter =
                                    LottoAdapter(getString(R.string.second_prize), lottoData.totalPrize2,
                                            lottoData.totalNumber2, lottoData.perPrize2)

                            view.lotto_recycler_view_third_prize.layoutManager = LinearLayoutManager(activity)
                            view.lotto_recycler_view_third_prize.adapter =
                                    LottoAdapter(getString(R.string.third_prize), lottoData.totalPrize3,
                                            lottoData.totalNumber3, lottoData.perPrize3)

                            view.lotto_recycler_view_fourth_prize.layoutManager = LinearLayoutManager(activity)
                            view.lotto_recycler_view_fourth_prize.adapter =
                                    LottoAdapter(getString(R.string.fourth_prize), lottoData.totalPrize4,
                                            lottoData.totalNumber4, lottoData.perPrize4)

                            view.lotto_recycler_view_fifth_prize.layoutManager = LinearLayoutManager(activity)
                            view.lotto_recycler_view_fifth_prize.adapter =
                                    LottoAdapter(getString(R.string.fifth_prize), lottoData.totalPrize5,
                                            lottoData.totalNumber5, lottoData.perPrize5)*/
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