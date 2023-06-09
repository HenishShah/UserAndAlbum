package com.example.userandalbum.models

import java.io.Serializable

data class UserDetails(
    val id: Int? = null,
    val name: String?,
    val username: String?,
    val email: String?,
    val address: Address?,
    val phone: String?,
    val website: String?,
    val company: Company?,
): Serializable