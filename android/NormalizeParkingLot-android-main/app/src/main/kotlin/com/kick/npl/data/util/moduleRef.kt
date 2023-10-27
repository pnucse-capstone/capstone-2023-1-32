package com.kick.npl.data.util

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

fun FirebaseFirestore.module(): CollectionReference {
    return this.collection("module")
}