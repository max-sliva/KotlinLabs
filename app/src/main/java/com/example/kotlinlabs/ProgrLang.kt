package com.example.kotlinlabs

import java.io.Serializable

//data class ProgrLang(val name: String, val year: Int, var picture: Object = R.drawable.no_picture as Object ) :Serializable
data class ProgrLang(val name: String, val year: Int,var picture: String = R.drawable.no_picture.toString()) :Serializable
