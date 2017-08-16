package com.jjosft.android.lottovillage.model

import com.google.gson.annotations.SerializedName

/**
 * Created by JJSOFT-DESKTOP on 2017-08-17.
 */
object Model {
    data class DefaultResponse(@SerializedName("isSuccess") val isSuccess : Boolean)
}