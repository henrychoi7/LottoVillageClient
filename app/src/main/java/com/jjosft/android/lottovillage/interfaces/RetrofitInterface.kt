package com.jjosft.android.lottovillage.interfaces

import com.jjosft.android.lottovillage.model.Model
import retrofit2.Call
import retrofit2.http.GET


/**
 * Created by JJSOFT-DESKTOP on 2017-08-17.
 */
interface RetrofitInterface {

    @GET("login") fun getLogin(): Call<Model.DefaultResponse>
}