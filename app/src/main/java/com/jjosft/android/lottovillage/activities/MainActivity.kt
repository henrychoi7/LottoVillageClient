package com.jjosft.android.lottovillage.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.RequestBody
import org.json.JSONObject


class MainActivity : BaseActivity() {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mSharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(BaseApplication.LOTTO_VILLAGE_PREFERENCES, Context.MODE_PRIVATE)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                main_text_message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                main_text_message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                main_text_message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        main_bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        detailsOfParticipation("1", isContinued = true)
        detailsOfParticipation("2", isContinued = true)
        detailsOfParticipation("3", isContinued = false)
    }

    override fun onStop() {
        super.onStop()
        mCompositeDisposable.clear()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun customOnClick(view: View) {
        when (view.id) {
            R.id.main_button_one_hour_apart_lotto -> {
                participation("1")
            }
            R.id.main_button_six_hour_apart_lotto -> {
                participation("2")
            }
            R.id.main_button_twelve_hour_apart_lotto -> {
                participation("3")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.main_menu_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        progressOn(getString(R.string.send_to_request_logout))

        val sharedPreferencesEditor = mSharedPreferences.edit()
        sharedPreferencesEditor.putBoolean(BaseApplication.AUTO_LOGIN, false)
        sharedPreferencesEditor.putStringSet(BaseApplication.X_ACCESS_TOKEN, null)
        sharedPreferencesEditor.apply()

        progressOff()

        Toast.makeText(applicationContext, getString(R.string.complete_to_logout), Toast.LENGTH_SHORT).show()
        startActivity(Intent(applicationContext, SplashActivity::class.java))
        finish()
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
                        progressOn("$eventType 회차 ${getString(R.string.request_detail_of_participation)}")
                    }

                    override fun onNext(t: Model.ResultResponse) {
                        if (t.isSuccess) {
                            val participationData: Model.DetailsOfParticipation = t.detailsOfParticipation[0]
                            when (eventType) {
                                "1" -> {
                                    main_text_one_hour_help.visibility = View.INVISIBLE
                                    main_linear_layout_one_hour.visibility = View.VISIBLE
                                    setLottoNumberBackground(main_text_one_hour_1, participationData.winningNumber1)
                                    setLottoNumberBackground(main_text_one_hour_2, participationData.winningNumber2)
                                    setLottoNumberBackground(main_text_one_hour_3, participationData.winningNumber3)
                                    setLottoNumberBackground(main_text_one_hour_4, participationData.winningNumber4)
                                    setLottoNumberBackground(main_text_one_hour_5, participationData.winningNumber5)
                                    setLottoNumberBackground(main_text_one_hour_6, participationData.winningNumber6)
                                    main_text_one_hour_participating_time.text = participationData.participatingTime
                                }
                                "2" -> {
                                    main_text_six_hour_help.visibility = View.INVISIBLE
                                    main_linear_layout_six_hour.visibility = View.VISIBLE
                                    setLottoNumberBackground(main_text_six_hour_1, participationData.winningNumber1)
                                    setLottoNumberBackground(main_text_six_hour_2, participationData.winningNumber2)
                                    setLottoNumberBackground(main_text_six_hour_3, participationData.winningNumber3)
                                    setLottoNumberBackground(main_text_six_hour_4, participationData.winningNumber4)
                                    setLottoNumberBackground(main_text_six_hour_5, participationData.winningNumber5)
                                    setLottoNumberBackground(main_text_six_hour_6, participationData.winningNumber6)
                                    main_text_six_hour_participating_time.text = participationData.participatingTime
                                }
                                "3" -> {
                                    main_text_twelve_hour_help.visibility = View.INVISIBLE
                                    main_linear_layout_twelve_hour.visibility = View.VISIBLE
                                    setLottoNumberBackground(main_text_twelve_hour_1, participationData.winningNumber1)
                                    setLottoNumberBackground(main_text_twelve_hour_2, participationData.winningNumber2)
                                    setLottoNumberBackground(main_text_twelve_hour_3, participationData.winningNumber3)
                                    setLottoNumberBackground(main_text_twelve_hour_4, participationData.winningNumber4)
                                    setLottoNumberBackground(main_text_twelve_hour_5, participationData.winningNumber5)
                                    setLottoNumberBackground(main_text_twelve_hour_6, participationData.winningNumber6)
                                    main_text_twelve_hour_participating_time.text = participationData.participatingTime
                                }
                            }
                        } else {
                            if (t.errorMessage == getString(R.string.unmatched_token_value)) Toast.makeText(applicationContext, getString(R.string.request_login), Toast.LENGTH_SHORT).show()
                            else {
                                when (eventType) {
                                    "1" -> {
                                        main_text_one_hour_help.visibility = View.VISIBLE
                                        main_text_one_hour_help.text = t.errorMessage
                                        main_linear_layout_one_hour.visibility = View.INVISIBLE
                                    }
                                    "2" -> {
                                        main_text_six_hour_help.visibility = View.VISIBLE
                                        main_text_six_hour_help.text = t.errorMessage
                                        main_linear_layout_six_hour.visibility = View.INVISIBLE
                                    }
                                    "3" -> {
                                        main_text_twelve_hour_help.visibility = View.VISIBLE
                                        main_text_twelve_hour_help.text = t.errorMessage
                                        main_linear_layout_twelve_hour.visibility = View.INVISIBLE
                                    }
                                }
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(applicationContext, "실패 ${e.message}", Toast.LENGTH_SHORT).show()
                        progressOff()
                    }

                    override fun onComplete() {
                        if (!isContinued) progressOff()
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
                        progressOn("$eventType 회차 ${getString(R.string.participating_lotto)}")
                    }

                    override fun onNext(t: Model.DefaultResponse) {
                        if (t.isSuccess) {
                            Toast.makeText(applicationContext, "로또참여 성공", Toast.LENGTH_SHORT).show()
                        } else {
                            if (t.errorMessage == getString(R.string.unmatched_token_value)) {
                                Toast.makeText(applicationContext, getString(R.string.request_login), Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(applicationContext, t.errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(applicationContext, "실패 ${e.message}", Toast.LENGTH_SHORT).show()
                        progressOff()
                    }

                    override fun onComplete() {
                        progressOff()
                        detailsOfParticipation(eventType)
                    }
                })
    }
}