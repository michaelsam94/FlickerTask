package com.example.data.model

import com.google.gson.annotations.SerializedName


data class FlickrResponse(
    @SerializedName("photos") val photos: PhotoData
)
