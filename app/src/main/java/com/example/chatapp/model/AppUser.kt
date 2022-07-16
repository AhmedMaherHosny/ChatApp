package com.example.chatapp.model

data class AppUser(
    var id: String? = null,
    var userName:String?=null,
    var email:String?=null,
    var password:String?=null,
    var phoneNumber:String?=null,
){
    companion object{
        val COLLECTION_NAME = "users"
    }
}