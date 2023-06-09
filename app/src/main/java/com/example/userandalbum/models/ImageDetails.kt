package com.example.userandalbum.models

import java.io.Serializable

data class ImageDetails(
    val id: Int? = null,
    val albumId: Int? = null,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?,
): Serializable