package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity("asteroid")
@Parcelize
data class Asteroid(
    @PrimaryKey val id: Long,
    @ColumnInfo("code_name") val codename: String,
    @ColumnInfo("close_approach_date") val closeApproachDate: String,
    @ColumnInfo("absolute_magnitude") val absoluteMagnitude: Double,
    @ColumnInfo("estimated_diameter") val estimatedDiameter: Double,
    @ColumnInfo("relative_velocity") val relativeVelocity: Double,
    @ColumnInfo("distance_from_earth") val distanceFromEarth: Double,
    @ColumnInfo("is_potentially_hazardous") val isPotentiallyHazardous: Boolean
) : Parcelable