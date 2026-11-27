package com.brokenkernel.didacticdisco

import android.app.Application
import android.os.Build
import android.os.StrictMode

public class DidadticDiscoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val strictModeVMPolicy =
            StrictMode.VmPolicy
                .Builder()
                .detectActivityLeaks()
                .detectCleartextNetwork()
                .detectFileUriExposure()
                .detectLeakedClosableObjects()
                .detectLeakedRegistrationObjects()
                .detectLeakedSqlLiteObjects()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 26
            strictModeVMPolicy
                .detectContentUriWithoutPermission()
                .detectUntaggedSockets()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { // 28
            strictModeVMPolicy.detectNonSdkApiUsage()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // 29
            strictModeVMPolicy
                .detectCredentialProtectedWhileLocked()
                .detectImplicitDirectBoot()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // 31
            strictModeVMPolicy
                .detectIncorrectContextUse()
                .detectUnsafeIntentLaunch()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) { // 36
            strictModeVMPolicy.detectBlockedBackgroundActivityLaunch()
        }


        val strictModeThreadPolicy =
            StrictMode.ThreadPolicy
                .Builder()
                .detectNetwork()
                .detectDiskReads()
                .detectDiskWrites()
                .detectCustomSlowCalls()
                .detectResourceMismatches()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 26
            strictModeThreadPolicy.detectUnbufferedIo()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // 34
            strictModeThreadPolicy.detectExplicitGc()
        }

        if (BuildConfig.ENABLE_STRICT_MODE_DEATH) {
            strictModeVMPolicy.penaltyDeath()
            strictModeThreadPolicy.penaltyDeath()
        } else {
            strictModeVMPolicy.penaltyDeathOnCleartextNetwork()
            strictModeThreadPolicy.penaltyLog()
        }
        StrictMode.setVmPolicy(strictModeVMPolicy.build())
        StrictMode.setThreadPolicy(strictModeThreadPolicy.build())
    }
}
