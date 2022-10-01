package com.uwdp.whatyouwant.util

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class MyApplication: MultiDexApplication(){
    companion object{
        lateinit var auth: FirebaseAuth
        var email: String? = null

        lateinit var db: FirebaseFirestore
        lateinit var storage: FirebaseStorage
    }

    override fun onCreate() {
        super.onCreate()
        db =  FirebaseFirestore.getInstance()
        storage = Firebase.storage
        auth = Firebase.auth

    }
}