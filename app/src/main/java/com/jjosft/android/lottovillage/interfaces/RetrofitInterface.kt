package com.jjosft.android.lottovillage.interfaces

import com.jjosft.android.lottovillage.model.Model
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

    @POST("login")
    fun postLogin(@Body paras: RequestBody): Observable<Model.DefaultResponse>

    @POST("register")
    fun postRegister(@Body params: RequestBody): Observable<Model.DefaultResponse>

    @GET("lotto_rounds")
    fun getLottoRounds(): Observable<Model.SingleStringArrayListResponse>

    @GET("details_of_lotto")
    fun getDetailsOfLotto(@Query("rounds") rounds : String): Observable<Model.LottoResponse>

    @GET("details_of_participation")
    fun getDetailsOfParticipation(@Query("event_type") eventType : String,
                                  @Query("event_date") eventDate : String,
                                  @Query("event_number") eventNumber : String,
                                  @Query("confirm_status") isConfirmStatus : Boolean): Observable<Model.ParticipationResponse>

    @POST("participation")
    fun postParticipation(@Body params: RequestBody): Observable<Model.DefaultResponse>
}