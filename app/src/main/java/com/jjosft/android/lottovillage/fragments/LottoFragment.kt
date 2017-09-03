package com.jjosft.android.lottovillage.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jjosft.android.lottovillage.R

/**
 * Created by JJSOFT-DESKTOP on 2017-09-03.
 */
class LottoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_lotto, container, false)
    }
}