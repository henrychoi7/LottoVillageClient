package com.jjosft.android.lottovillage.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.RequestBody
import org.json.JSONObject

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        main_bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val toggle = ActionBarDrawerToggle(
                this, main_drawer_layout, main_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        main_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        main_nav_view.setNavigationItemSelectedListener(this)

        detailsOfParticipation("1", "010-8759-6912", "123456")

        /*progressOn(getString(R.string.loading))

        Handler().postDelayed({
            progressOff()
        }, 1000)*/
    }

    override fun onStop() {
        super.onStop()
        mCompositeDisposable.clear()
    }

    override fun onBackPressed() {
        if (main_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            main_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun customOnClick(view: View) {
        when (view.id) {
            R.id.main_button_one_hour_apart_lotto -> {
                participation("1", "010-8759-6912", "123456")
            }
            R.id.main_button_six_hour_apart_lotto -> {
                participation("2", "010-8759-6912", "123456")
            }
            R.id.main_button_twelve_hour_apart_lotto -> {
                participation("3", "010-8759-6912", "123456")
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_camera -> {

            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        main_drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout() {
        BaseApplication.getRetrofitMethod().getLogout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Model.DefaultResponse> {
                    override fun onSubscribe(d: Disposable) {
                        mCompositeDisposable.add(d)
                        progressOn(getString(R.string.send_to_request_logout))
                    }

                    override fun onNext(t: Model.DefaultResponse) {
                        if (t.isSuccess) {
                            val sharedPreferencesEditors = mSharedPreferences.edit()
                            sharedPreferencesEditors.putBoolean(BaseApplication.AUTO_LOGIN, false)
                            sharedPreferencesEditors.putString(BaseApplication.PHONE_NUMBER, "")
                            sharedPreferencesEditors.putString(BaseApplication.PASSWORD, "")
                            sharedPreferencesEditors.apply()
                            Toast.makeText(applicationContext, getString(R.string.complete_to_logout), Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext, SplashActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(applicationContext, t.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(applicationContext, "실패 ${e.message}", Toast.LENGTH_SHORT).show()
                        progressOff()
                    }

                    override fun onComplete() {
                        progressOff()
                    }
                })
    }

    private fun detailsOfParticipation(eventType: String, phoneNumber: String, password: String) {
        val jsonObject = JSONObject()
        jsonObject.put("event_type", eventType)
        jsonObject.put("phone_number", phoneNumber)
        jsonObject.put("password", password)

        BaseApplication.getRetrofitMethod().postDetailsOfParticipation(RequestBody.create(BaseApplication.MEDIA_TYPE_JSON, jsonObject.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Model.DefaultResponse>{
                    override fun onSubscribe(d: Disposable) {
                        mCompositeDisposable.add(d)
                        progressOn(getString(R.string.send_to_request_register))
                    }

                    override fun onNext(t: Model.DefaultResponse) {
                        if (t.isSuccess) {
                            Toast.makeText(applicationContext, "로또참여 내역 불러오기 성공", Toast.LENGTH_SHORT).show()
                            //startActivity(Intent(applicationContext, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
                        } else {
                            Toast.makeText(applicationContext, t.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(applicationContext, "실패 ${e.message}", Toast.LENGTH_SHORT).show()
                        progressOff()
                    }

                    override fun onComplete() {
                        progressOff()
                    }
                })
    }

    private fun participation(eventType: String, phoneNumber: String, password: String) {
        val jsonObject = JSONObject()
        jsonObject.put("event_type", eventType)
        jsonObject.put("phone_number", phoneNumber)
        jsonObject.put("password", password)

        BaseApplication.getRetrofitMethod().postParticipation(RequestBody.create(BaseApplication.MEDIA_TYPE_JSON, jsonObject.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Model.DefaultResponse> {
                    override fun onSubscribe(d: Disposable) {
                        mCompositeDisposable.add(d)
                        progressOn(getString(R.string.send_to_request_register))
                    }

                    override fun onNext(t: Model.DefaultResponse) {
                        if (t.isSuccess) {
                            Toast.makeText(applicationContext, "로또참여 성공", Toast.LENGTH_SHORT).show()
                            //startActivity(Intent(applicationContext, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
                        } else {
                            Toast.makeText(applicationContext, t.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(applicationContext, "실패 ${e.message}", Toast.LENGTH_SHORT).show()
                        progressOff()
                    }

                    override fun onComplete() {
                        progressOff()
                        detailsOfParticipation(eventType, phoneNumber, password)
                    }
                })
    }
}