package com.example.kotlinlabs

import java.io.Serializable

data class ProgrLang(val name: String, val year: Int, val picture: Int = R.drawable.no_picture ) :Serializable
