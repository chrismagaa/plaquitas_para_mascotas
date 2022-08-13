package com.camg_apps.placas_perros.admob

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object AdMobManager {


    private var mInterstitialAd: InterstitialAd? = null
    const val idIntersticial = "ca-app-pub-4702215318330979/7257469801"

    fun loadAd(activity: Activity) {

        if (mInterstitialAd == null) {
            var adRequest = AdRequest.Builder().build()
            InterstitialAd.load(
                activity, idIntersticial, adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d(this@AdMobManager.javaClass.simpleName, adError.message)
                        mInterstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.d(this@AdMobManager.javaClass.simpleName, "Ad was loaded.")
                        mInterstitialAd = interstitialAd
                    }
                }
            )
        }else{
            Log.d(this@AdMobManager.javaClass.simpleName, "Ya estaba cargado")
        }
    }


    fun showAd(activity: Activity, onShowAd: () -> Unit) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null
                    onShowAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    mInterstitialAd = null
                    onShowAd()
                }

                override fun onAdShowedFullScreenContent() {
                    mInterstitialAd = null
                }
            }
            mInterstitialAd?.show(activity)
        }else{
            Log.d(this@AdMobManager.javaClass.simpleName, "No hay anuncio cargado pai")
            onShowAd()
        }
    }


}