package com.jjosft.android.lottovillage.interfaces

import com.jjosft.android.lottovillage.model.Model
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * Created by JJSOFT-DESKTOP on 2017-08-17.
 */
interface RetrofitInterface {

    @POST("login")
    fun postLogin(@Body paras: RequestBody): Observable<Model.DefaultResponse>

    @POST("register")
    fun postRegister(@Body params: RequestBody): Observable<Model.DefaultResponse>

    @POST("details_of_participation")
    fun postDetailsOfParticipation(@Body params: RequestBody): Observable<Model.ResultResponse>

    @POST("participation")
    fun postParticipation(@Body params: RequestBody): Observable<Model.DefaultResponse>
}