package com.example.spotsync.datalayer

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


public fun getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
    if (ContextCompat.checkSelfPermission(this.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
        locationPermissionGranted = true
    } else {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
    }
}
