package com.example.lostdogsandcats

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class EditPetActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    override fun onBackPressed() {
        val intent2 = Intent(this@EditPetActivity, PetActivity::class.java)
        intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent2.putExtra("petId", intent.getStringExtra("petId").toString())
        intent2.putExtra("petName", intent.getStringExtra("petName").toString())
        intent2.putExtra("isDog", intent.getStringExtra("isDog").toString())
        intent2.putExtra("description", intent.getStringExtra("description").toString())
        intent2.putExtra("photo", intent.getStringExtra("photo").toString())
        intent2.putExtra("date", intent.getStringExtra("date").toString())
        intent2.putExtra("place", intent.getStringExtra("place").toString())
        intent2.putExtra("number", intent.getStringExtra("number").toString())
        intent2.putExtra("userId", intent.getStringExtra("userId").toString())
        intent2.putExtra("user", intent.getStringExtra("user").toString())
        startActivity(intent2)
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pet)
        //getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        //findViewById<TextView>(R.id.nameEditView).text = intent.getStringExtra("petName").toString()

        findViewById<EditText>(R.id.nameEditView).setText(intent.getStringExtra("petName"))
        findViewById<EditText>(R.id.phoneEditView).setText(intent.getStringExtra("number"))
        findViewById<EditText>(R.id.placeEditView).setText(intent.getStringExtra("place"))
        findViewById<EditText>(R.id.photoEditView).setText(intent.getStringExtra("photo"))
        findViewById<EditText>(R.id.descriptionEditView).setText(intent.getStringExtra("description"))

//        findViewById<Button>(R.id.deleteEditButton).setOnClickListener {
//            db.collection("allpets").document("${intent.getStringExtra("petId").toString()}")
//                .delete()
//
//
//            val intent2 = Intent(this@EditPetActivity, MainActivity::class.java)
//            intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//            intent2.putExtra("user", intent.getStringExtra("user").toString())
//            startActivity(intent2)
//            finish()
//        }

        findViewById<Button>(R.id.deleteEditButton).setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.delete_pet, null)
            val builder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Delete pet")
            val alertDialog = builder.show()
            dialogView.findViewById<Button>(R.id.deletepet).setOnClickListener {
                alertDialog.dismiss()
////                val commentText =
////                    dialogView.findViewById<EditText>(R.id.commenttext).text.toString()
////                //SAVE TO DB
//                val dateNow = SimpleDateFormat("dd.M.yyyy hh:mm:ss")
//                val date = dateNow.format(Date()).toString()
////                val commentToAdd = Comment(
////                    intent.getStringExtra("petId").toString(),
////                    intent.getStringExtra("user").toString(),
//////                    commentText,
////                    date
////                )
                db.collection("allpets").document("${intent.getStringExtra("petId").toString()}")
                    .delete()

                db.collection("comments")
                    .whereEqualTo("petId", "${intent.getStringExtra("petId").toString()}")
                    .get()
                    .addOnSuccessListener { documents ->
                        for (one in documents) {
                            db.collection("comments").document("${one.id}").delete()
                            //Log.d(TAG, "${document.id} => ${document.data}")
                        }
                    }
                    .addOnFailureListener { exception ->
                    }
                val intent2 = Intent(this@EditPetActivity, MainActivity::class.java)
                intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                intent2.putExtra("user", intent.getStringExtra("user").toString())
                startActivity(intent2)
                finish()
            }
            dialogView.findViewById<Button>(R.id.cancelpet).setOnClickListener {
                alertDialog.dismiss()
//                val intent2 = Intent(this@EditPetActivity, MainActivity::class.java)
//                intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//                intent2.putExtra("user", intent.getStringExtra("user").toString())
//                startActivity(intent2)
//                finish()
            }

        }





        findViewById<Button>(R.id.saveEditButton).setOnClickListener {
            db.collection("allpets").document("${intent.getStringExtra("petId").toString()}")
                .update(
                    mapOf(
                        "name" to findViewById<EditText>(R.id.nameEditView).text.toString(),
                        "number" to findViewById<EditText>(R.id.phoneEditView).text.toString(),
                        "photo" to findViewById<EditText>(R.id.photoEditView).text.toString(),
                        "description" to findViewById<EditText>(R.id.descriptionEditView).text.toString(),
                        "place" to findViewById<EditText>(R.id.placeEditView).text.toString()
                    )
                )


            val intent2 = Intent(this@EditPetActivity, MainActivity::class.java)
            intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            intent2.putExtra("user", intent.getStringExtra("user").toString())
            startActivity(intent2)
            finish()
        }
    }
}