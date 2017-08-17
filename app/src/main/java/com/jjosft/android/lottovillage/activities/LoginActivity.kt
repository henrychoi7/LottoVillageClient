package com.jjosft.android.lottovillage.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.base.BaseActivity
import com.jjosft.android.lottovillage.base.BaseApplication
import com.jjosft.android.lottovillage.model.Model
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {
    private val mCompositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar_login)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStop() {
        super.onStop()
        mCompositeDisposable.clear()
    }

    fun customOnClick(view: View) {
        when (view.id) {
            R.id.button_login -> validateLogin()
        }
    }

    private fun validateLogin() {
        // s%3Aj-xVL5j6MSSY_tUlGTtzjImFLIjhXARJ.HkAQaLpM6ttS%2FWJWubAznMNLjlhuo4AzKNf7bfGo%2B1Q
        BaseApplication.getRetrofitMethod().getLogin("01087596912", "test2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Model.DefaultResponse> {
                    override fun onNext(t: Model.DefaultResponse) {
                        if (t.isSuccess) {
                            Toast.makeText(applicationContext, "성공", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, t.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onComplete() {
                        Toast.makeText(applicationContext, "완료", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(applicationContext, "완전실패 ${e.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mCompositeDisposable.add(d)
                    }
                })

        /*RETROFIT_INTERFACE.OnOffMixRx("api.onoffmix.com/event/list", "json", 12,
                "if(recruitEndDateTime-NOW()>0# 1# 0)|DESC,FIND_IN_SET('advance'#wayOfRegistration)|DESC,popularity|DESC,idx|DESC", pageCount, "", "", "", "true", "true", "true", "개발", "", "", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<OnOffMixData>() {
                    fun onSubscribe(@NonNull d: Disposable) {
                        mMainActivityView.showProgressDialog(subscribeProgressDialog, mContext.getString(R.string.loading))
                    }

                    fun onNext(@NonNull onOffMixData: OnOffMixData) {
                        if (onOffMixData.getError().getCode() === 0) {
                            for (eventListData in onOffMixData.getEventList()) {
                                val recyclerViewData = RecyclerViewData()
                                recyclerViewData.setTitle(eventListData.getTitle())
                                recyclerViewData.setContent(null)
                                recyclerViewData.setWatchCount(eventListData.getTotalCanAttend() + mContext.getString(R.string.onOffMix_attend))
                                recyclerViewData.setFavoritesCount(eventListData.getCategoryIdx())
                                recyclerViewData.setSubInfo(if (eventListData.getUsePayment().equals("n")) "무료" else "유료" + eventListData.getRegTime())
                                recyclerViewData.setImageResources(getBitmapFromVectorDrawable(mContext, R.drawable.ic_onoffmix_24dp))
                                recyclerViewData.setImageUrl(eventListData.getBannerUrl())
                                recyclerViewData.setContentUrl(eventListData.getEventUrl().replace("http://onoffmix", "http://m.onoffmix"))
                                recyclerViewData.setType(ON_OFF_MIX)
                                recyclerViewDataArrayList.add(recyclerViewData)
                            }
                        } else {
                            mMainActivityView.dismissProgressDialog(subscribeProgressDialog)
                            mMainActivityView.showCustomToast(onOffMixData.getError().getMessage())
                        }
                    }

                    fun onError(@NonNull e: Throwable) {
                        mMainActivityView.dismissProgressDialog(subscribeProgressDialog)
                        mMainActivityView.showCustomToast(e.message)
                    }

                    fun onComplete() {
                        if (pageCount === 1) {
                            mMainActivityView.setDisplayRecyclerView(recyclerViewDataArrayList)
                        } else {
                            mMainActivityView.addAdditionalData(recyclerViewDataArrayList)
                        }

                        mMainActivityView.dismissProgressDialog(subscribeProgressDialog)
                    }
                })*/

        //기본 Retrofit2 사용
        /*BaseApplication.getRetrofitMethod().getLogin().enqueue(object : Callback<Model.DefaultResponse> {
            override fun onResponse(call: Call<Model.DefaultResponse>, response: Response<Model.DefaultResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Model.DefaultResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "완전실패", Toast.LENGTH_SHORT).show()
            }
        })*/
    }
}
