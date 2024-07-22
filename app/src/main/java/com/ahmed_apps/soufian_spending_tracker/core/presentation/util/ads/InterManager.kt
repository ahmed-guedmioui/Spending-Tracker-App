package com.ahmed_apps.soufian_spending_tracker.core.presentation.util.ads

import android.app.Activity
import android.app.ProgressDialog
import android.os.Handler
import android.os.Looper
import com.ahmed_apps.soufian_spending_tracker.R
import com.ahmed_apps.soufian_spending_tracker.SpendingTrackerApp
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.adunit.adapter.utility.AdInfo
import com.ironsource.mediationsdk.logger.IronSourceError
import com.ironsource.mediationsdk.sdk.LevelPlayInterstitialListener

object InterManager {

    private var isIronSourceInterLoaded = false

    private lateinit var onAdClosedListener: OnAdClosedListener
    private var counter = 1

    fun loadInterstitial(activity: Activity) {
        loadIronSourceInter(activity)
    }

    fun showInterstitial(
        activity: Activity, onInterClosed: () -> Unit
    ) {
        showInterstitial(
            activity,
            object : OnAdClosedListener {
                override fun onAdClosed() {
                    onInterClosed()
                }
            }
        )
    }

    private fun showInterstitial(
        activity: Activity, adClosedListener: OnAdClosedListener
    ) {
        onAdClosedListener = adClosedListener

        if (3 == counter) {
            counter = 1
            val progressDialog = ProgressDialog(activity)
            progressDialog.setCancelable(false)
            progressDialog.setMessage(activity.getString(R.string.loading_ad))
            progressDialog.show()

            Handler(Looper.getMainLooper()).postDelayed({
                showIronSourceInter(activity)
                progressDialog.dismiss()
            }, 2000)
        } else {
            counter++
            onAdClosedListener.onAdClosed()
        }
    }

    // IronSource ---------------------------------------------------------------------------------------------------------------------

    private fun loadIronSourceInter(activity: Activity) {
        isIronSourceInterLoaded = false

        IronSource.setLevelPlayInterstitialListener(object : LevelPlayInterstitialListener {
            override fun onAdReady(adInfo: AdInfo) {
                isIronSourceInterLoaded = true
            }

            override fun onAdLoadFailed(ironSourceError: IronSourceError) {
                isIronSourceInterLoaded = false
            }

            override fun onAdOpened(adInfo: AdInfo) {}
            override fun onAdShowSucceeded(adInfo: AdInfo) {}
            override fun onAdShowFailed(ironSourceError: IronSourceError, adInfo: AdInfo) {
                isIronSourceInterLoaded = false
                loadInterstitial(activity)
                onAdClosedListener.onAdClosed()
            }

            override fun onAdClicked(adInfo: AdInfo) {}
            override fun onAdClosed(adInfo: AdInfo) {
                isIronSourceInterLoaded = false
                loadInterstitial(activity)
                onAdClosedListener.onAdClosed()
            }
        })
        IronSource.loadInterstitial()
    }

    private fun showIronSourceInter(activity: Activity) {
        if (isIronSourceInterLoaded) {
            IronSource.showInterstitial(SpendingTrackerApp.IRON_SOURCE_INTER_ID)
        } else {
            loadInterstitial(activity)
            onAdClosedListener.onAdClosed()
        }
    }
}

interface OnAdClosedListener {
    fun onAdClosed()
}

