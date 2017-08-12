package com.jjosft.android.lottovillage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val lottieAnimationView = customToastLayout.findViewById(R.id.custom_av) as LottieAnimationView
        lottieAnimationView.setAnimation(jsonFileName)
        lottieAnimationView.loop(false)
        lottieAnimationView.playAnimation()*/

        lottie_animation_splash.setAnimation("material_wave_loading.json")
        lottie_animation_splash.loop(true)
        lottie_animation_splash.playAnimation()
    }
}
