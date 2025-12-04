package com.group1.zoomi.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.group1.zoomi.model.LocationData
import kotlinx.coroutines.tasks.await

class LocationRepository(
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {
    suspend fun getCurrentLocation(): LocationData? {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            val location: Location? = fusedLocationProviderClient
                .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .await()
            return location?.let {
                LocationData(it.latitude, it.longitude)
            }
        }
        return null
    }
}
