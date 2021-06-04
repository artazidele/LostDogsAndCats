package com.example.lostdogsandcats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myDataset = Datasource().loadAllPets()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = ItemAdapter(this, myDataset)
        recyclerView.setHasFixedSize(true)
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//
//
//        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
////        val adapter = LostPetListAdapter()
////        recyclerView.adapter = adapter
////        recyclerView.layoutManager = LinearLayoutManager(this)
////        recyclerView.setOnClickListener {
////
////        }
//    //val docRef = db.collection("lostpets")
//        findViewById<Button>(R.id.read).setOnClickListener {
//            db.collection("lostpets")
//                //.whereEqualTo("capital", true)
//                .get()
//                .addOnSuccessListener { documents ->
//                    for (document in documents) {
//                        Log.d(TAG, "${document.id} => ${document.data}")
//                        val text = findViewById<TextView>(R.id.textview).text.toString()
//                        findViewById<TextView>(R.id.textview).text =  text + "\n" + document.get("name").toString()
//                        //findViewById<TextView>(R.id.textview).text =  text + "\n" + document.id.toString()
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents: ", exception)
//                }
//        }
//
//
//
//
//        val addPetButton = findViewById<Button>(R.id.add)
//        addPetButton.setOnClickListener {
//            startActivity(Intent(this@MainActivity, AddLostPetActivity::class.java))
//        }
//
//    }
}