package com.jjosft.android.lottovillage.interfaces

import com.jjosft.android.lottovillage.model.Model
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * Created by JJSOFT-DESKTOP on 2017-08-17.
 */
interface RetrofitInterface {

    @GET("login")
    fun getLogin(@Query("phone_no") phoneNo: String, @Query("password") password: String): Observable<Model.DefaultResponse>

    /*@GET("logout")
    fun getLogout()*/

    @POST("auth/register")
    fun postRegister(@Body params: RequestBody): Flowable<Model.DefaultResponse>
}