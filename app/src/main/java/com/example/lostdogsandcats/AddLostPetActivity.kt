package com.example.lostdogsandcats

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class AddLostPetActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lost_pet)

        /*val saveButton = findViewById<Button>(R.id.button_save)
        val updateButton = findViewById<Button>(R.id.button_update)

        val petToAdd = Pet("Samba", true, false, "","", "", "", "")
        saveButton.setOnClickListener {
            addPet(petToAdd)
        }
        val readButton = findViewById<Button>(R.id.button_read)
        readButton.setOnClickListener {
            readPetData()
        }
        //val name = findViewById<EditText>(R.id.name).text.toString()
        updateButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.name).text.toString()
            updatePet(name)
        }
        val deleteButton = findViewById<Button>(R.id.button_delete)

        deleteButton.setOnClickListener {
            deletePet()
        }*/

        //LostDogsAndCats
        val saveButton = findViewById<Button>(R.id.button_save)
        saveButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.name).text.toString()
            val description = findViewById<EditText>(R.id.description).text.toString()
            val photo = "There will be a photo"
            val dateNow = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val date = dateNow.format(Date()).toString()
            val place = findViewById<EditText>(R.id.place).text.toString()
            val number = findViewById<EditText>(R.id.number).text.toString()
            val userId = "0"
            var isDog = false
            val id = findViewById<RadioGroup>(R.id.dog_or_cat).checkedRadioButtonId
            when (id) {
                R.id.cat -> isDog = false
                else -> isDog = true
            }
            val petToAdd = Pet(name, isDog, description, photo, date, place, number, userId)
            addPet(petToAdd)
            startActivity(Intent(this@AddLostPetActivity, MainActivity::class.java))
        }


    }
    /*private fun addPet(pet: Pet){
//        db.collection("pets")
//            .add(pet)
//            .addOnSuccessListener { documentReference ->
//                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(ContentValues.TAG, "Error adding document", e)
//            }

        db.collection("addedPets").document("AddedPet2").set(pet)
    }
    private fun updatePet(name: String){
        val petToUpdate = db.collection("addedPets").document("AddedPet")

        val vards = name
        petToUpdate
            .update("name", vards)
    }
    private fun deletePet() {
        db.collection("pets").document("DfLYgR570BJucWWLh6qd")
            .delete()
    }
    private fun readPetData(){
        val docRef = db.collection("addedPets").document("AddedPet")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    findViewById<Button>(R.id.button_delete).text = document.id.toString()

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }*/

    //LostDogsAndCats
    private fun addPet(pet: Pet){
        db.collection("lostpets")
            .add(pet)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

}