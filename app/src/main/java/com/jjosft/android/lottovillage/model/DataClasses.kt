package com.jjosft.android.lottovillage.model

import com.google.gson.annotations.SerializedName

/**
 * Created by JJSOFT-DESKTOP on 2017-08-17.
 */

object Model {
    data class DefaultResponse(@SerializedName("isSuccess") val isSuccess: Boolean, @SerializedName("errorMessage") val errorMessage: String)
    data class ResultResponse(@SerializedName("isSuccess") val isSuccess: Boolean, @SerializedName("errorMessage") val errorMessage: String, @SerializedName("results") val detailOfParticipation: ArrayList<DetailOfParticipation>)
    data class DetailOfParticipation(@SerializedName("WINNING_NUMBER_1") val winningNumber1: Int, @SerializedName("WINNING_NUMBER_2") val winningNumber2: Int,
                                     @SerializedName("WINNING_NUMBER_3") val winningNumber3: Int, @SerializedName("WINNING_NUMBER_4") val winningNumber4: Int,
                                     @SerializedName("WINNING_NUMBER_5") val winningNumber5: Int, @SerializedName("WINNING_NUMBER_6") val winningNumber6: Int,
                                     @SerializedName("PARTICIPATING_TIME") val participatingTime: String)
}