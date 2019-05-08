package com.sanitcode.footballclubapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import java.text.SimpleDateFormat
import java.util.*

internal fun loadImage(context: Context,
                       url: String,
                       imageView: ImageView) {

    fun setMemoryCategory(context: Context) {
        Glide.get(context).setMemoryCategory(MemoryCategory.NORMAL)
    }

    setMemoryCategory(context)

    Glide.with(context)
            .load(url)
            .into(imageView)
}

internal fun formatDate(dateEvent: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")
    val date = format.parse(dateEvent)
    return SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
            .format(date).toString()
}

internal fun checkBeforeDate(dateEvent: String): Boolean {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return !format.parse(dateEvent).before(Date())
}

/**@SuppressLint("SimpleDateFormat")
internal fun toGMTFormat(date: String): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    formatter.timeZone = TimeZone.getTimeZone("GMT")
    return formatter.parse(date)
}*/

@SuppressLint("SimpleDateFormat")
fun toGMTTime(timeMatch: String): String? {
    val formatTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    formatTime.timeZone = TimeZone.getTimeZone("UTC")
    val time = formatTime.parse(timeMatch)
    return SimpleDateFormat("HH:mm", Locale.getDefault())
            .format(time).toString()
}

internal fun String?.changeLine(): String? {
    return this?.replace(";", "\n")?.replace(":", " ")
}
