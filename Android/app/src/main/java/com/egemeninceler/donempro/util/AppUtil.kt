package com.egemeninceler.donempro.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel

fun rotate90FImage(bytes: ByteArray): Bitmap? {
    val matrix = Matrix()

    matrix.postRotate(90.toFloat())
    var bitmap  = Bitmap.createBitmap(
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size),
        0,
        0,
        600,
        480,
        matrix,
        true
    )
    return bitmap
}
