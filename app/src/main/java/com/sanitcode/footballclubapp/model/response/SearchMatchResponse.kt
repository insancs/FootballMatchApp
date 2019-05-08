package com.sanitcode.footballclubapp.model.response

import com.google.gson.annotations.SerializedName
import com.sanitcode.footballclubapp.model.ItemModel

data class SearchMatchResponse(@SerializedName("event") var events: MutableList<ItemModel>? = null)