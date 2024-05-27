package com.cropwise.gmapspolygoncoloringissue

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.material.button.MaterialButton
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonPolygon

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private val changePolygonColorButton: MaterialButton by lazy {
        findViewById(R.id.changeColorButton)
    }

    private val changePolygonColorLoopButton: MaterialButton by lazy {
        findViewById(R.id.changeColorLoopButton)
    }

    private var mMap: GoogleMap? = null
    private var polygonLayer: GeoJsonLayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()
        setUpMap()
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        addPolygonFromResource()
        moveToPolygonBounds()
    }

    private fun setUpMap() {
        (supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment)?.getMapAsync(this)
    }

    private fun setListeners() {

        changePolygonColorButton.setOnClickListener {
            changePolygonColor()
        }

        changePolygonColorLoopButton.setOnClickListener {
            repeat(10) {
                changePolygonColor()
            }
        }
    }

    private fun changePolygonColor() {
        polygonLayer?.run {
            val polygon = features.first()
            polygon.polygonStyle.fillColor =
                ContextCompat.getColor(applicationContext, R.color.polygon_color)
        }
    }

    private fun addPolygonFromResource() {
        polygonLayer = GeoJsonLayer(mMap, R.raw.polygon, this)
        polygonLayer?.addLayerToMap()
    }

    private fun moveToPolygonBounds() {
        polygonLayer?.run {

            val polygonCoordinates =
                (features.first().geometry as GeoJsonPolygon).coordinates.flatten()
            val boundsBuilder = LatLngBounds.Builder()
            polygonCoordinates.forEach { coordinate ->
                boundsBuilder.include(coordinate)
            }

            val bounds = boundsBuilder.build()
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
        }
    }
}
