package com.sanitcode.footballclubapp.model.response

import com.google.gson.annotations.SerializedName
import com.sanitcode.footballclubapp.model.ItemModel

data class ScheduleResponse(@SerializedName("events") var events: MutableList<ItemModel>? = null)