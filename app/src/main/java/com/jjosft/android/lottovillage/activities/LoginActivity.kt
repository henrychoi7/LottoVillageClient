package com.jjosft.android.lottovillage.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.base.BaseActivity
import com.jjosft.android.lottovillage.base.BaseApplication
import com.jjosft.android.lottovillage.model.Model
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar_login)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun customOnClick(view: View) {
        when (view.id) {
            R.id.button_login -> BaseApplication.getRetrofitMethod().getLogin().enqueue(object : Callback<Model.DefaultResponse> {
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
                })
        }
    }
}
