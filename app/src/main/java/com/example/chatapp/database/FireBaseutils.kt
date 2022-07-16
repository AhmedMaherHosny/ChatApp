package com.example.chatapp.database

import com.example.chatapp.model.AppUser
import com.example.chatapp.model.AppUser.Companion.COLLECTION_NAME
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun getCollection(collectionName : String):CollectionReference{
    val db = Firebase.firestore
    return db.collection(collectionName)
}

fun addUserToFirestore(
    user: AppUser,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val userCollection = getCollection(COLLECTION_NAME)
    val userDocument = userCollection.document(user.id!!)
    userDocument.set(user)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)

}

fun signIn(uid:String, onSuccessListener: OnSuccessListener<DocumentSnapshot>, onFailureListener: OnFailureListener){
    val collectionRef = getCollection(COLLECTION_NAME)
    collectionRef.document(uid)
        .get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}