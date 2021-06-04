package com.example.lostdogsandcats

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class Datasource {
    //private lateinit var allPets: MutableList<Pet>
   // val firstPet = Pet("VardsFirst", true, "", "", "", "", "", "")
    //var allPets = mutableListOf<Pet>(firstPet)
    fun loadAllPets(): MutableList<Pet> {
        return getAllPets()

//        Log.d(TAG, "AllPets ir aizpildita2")
//        for (pet in allPets) {
//            Log.d(TAG, "${pet.name}")
//        }
//        //return allPets
//        return mutableListOf<Pet>(
//            Pet("VardsFirst", true, "", "", "", "", "", "")
//        )
    }

    private fun getAllPets(): MutableList<Pet>{
        val db = FirebaseFirestore.getInstance()
        val firstPet = Pet("VardsFirst", true, "", "", "", "", "", "")
        var allPets = mutableListOf<Pet>(firstPet)
        db.collection("lostpets")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val newPet = Pet(
                        document.get("name").toString(),
                        false,//document.get("isDog").toBoolean(),
                        document.get("description").toString(),
                        document.get("photo").toString(),
                        document.get("date").toString(),
                        document.get("place").toString(),
                        document.get("number").toString(),
                        document.get("userId").toString()
                    )
                    allPets.add(newPet)
                    Log.d(TAG, "${newPet.name}")
                    Log.d(TAG, "${document.id} => ${document.data}")
                    //val text = findViewById<TextView>(R.id.textview).text.toString()
                    //findViewById<TextView>(R.id.textview).text =  text + "\n" + document.get("name").toString()
                    //findViewById<TextView>(R.id.textview).text =  text + "\n" + document.id.toString()
                }
                Log.d(TAG, "AllPets ir aizpildita 1")
                for (pet in allPets) {
                    Log.d(TAG, "${pet.name}")
                }
                //return allPets
                //getAllPets(allPets)
                //allPetsSaved = allPets
                //continue //return@addOnSuccessListener //allPets
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
        Log.d(TAG, "AllPets ir aizpildita2")
        for (pet in allPets) {
            Log.d(TAG, "${pet.name}")
        }
        //return allPets
        return mutableListOf<Pet>(
            Pet("VardsFirst", true, "", "", "", "", "", "")
        )
        return allPets
    }
}