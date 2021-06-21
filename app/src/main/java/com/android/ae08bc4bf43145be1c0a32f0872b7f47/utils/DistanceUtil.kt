package com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils

object DistanceUtil {

    fun calculateDistance(dirX1: Double, dirX2: Double, dirY1: Double, dirY2: Double): Double {

        return Math.sqrt(Math.pow((dirX1 - dirX2), 2.0) + Math.pow((dirY1 - dirY2), 2.0))
    }
}