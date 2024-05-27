package com.cropwise.gmapspolygoncoloringissue

import android.app.Application
import android.widget.Toast
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback

internal class App : Application(), OnMapsSdkInitializedCallback {

    override fun onCreate() {
        super.onCreate()
        MapsInitializer.initialize(applicationContext, MapsInitializer.Renderer.LATEST, this)
    }

    override fun onMapsSdkInitialized(renderer: MapsInitializer.Renderer) {
        when (renderer) {
            MapsInitializer.Renderer.LATEST -> Toast.makeText(
                applicationContext,
                "Using LATEST renderer!",
                Toast.LENGTH_SHORT
            ).show()

            MapsInitializer.Renderer.LEGACY -> Toast.makeText(
                applicationContext,
                "Using LEGACY renderer!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
