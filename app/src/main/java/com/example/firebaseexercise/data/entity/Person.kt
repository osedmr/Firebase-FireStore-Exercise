package com.example.firebaseexercise.data.entity

import java.io.Serializable

data class Person(
    var id: String? = null, // Firebase tarafından atanacak
    var name:String? = "",
    var surname:String? = "",
    var age:Int? = -1  ) : Serializable {
}
