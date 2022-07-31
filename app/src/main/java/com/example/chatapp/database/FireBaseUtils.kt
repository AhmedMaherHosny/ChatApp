package com.example.chatapp.database

import com.example.chatapp.model.AppUser
import com.example.chatapp.model.AppUser.Companion.COLLECTION_NAME
import com.example.chatapp.model.Message
import com.example.chatapp.model.Room
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun getCollection(collectionName: String): CollectionReference {
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

fun signIn(
    uid: String,
    onSuccessListener: OnSuccessListener<DocumentSnapshot>,
    onFailureListener: OnFailureListener
) {
    val userCollection = getCollection(COLLECTION_NAME)
    userCollection.document(uid)
        .get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun addRoom(
    room: Room,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val roomCollection = getCollection(Room.COLLECTION_NAME)
    val doc = roomCollection.document()
    room.id = doc.id
    doc.set(room)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getAllRooms(
    onSuccessListener: OnSuccessListener<QuerySnapshot>,
    onFailureListener: OnFailureListener
) {
    val roomCollection = getCollection(Room.COLLECTION_NAME)
    roomCollection.get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getMsgRef(roomId: String): CollectionReference {
    val roomCollection = getCollection(Room.COLLECTION_NAME)
    return roomCollection.document(roomId).collection(Message.COLLECTION_NAME)
}

fun addMsg(
    msg: Message,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val getMsgCollRef = getMsgRef(msg.roomID!!)
    val msgRef = getMsgCollRef.document()
    msg.id = msgRef.id
    msgRef.set(msg)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}