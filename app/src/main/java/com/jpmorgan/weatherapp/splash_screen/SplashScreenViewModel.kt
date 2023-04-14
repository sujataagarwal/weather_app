package com.jpmorgan.weatherapp.splash_screen

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jpmorgan.weatherapp.utils.WeatherAppConstants.SPLASH_SCREEN_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel@Inject constructor() : ViewModel() {

    val isFinished = MutableLiveData<Boolean>()

    init {
        object : CountDownTimer(SPLASH_SCREEN_DELAY, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                print("onTick")
            }

            override fun onFinish() {
                isFinished.postValue(true)
            }

        }.start()
    }
}