package com.example.lostdogsandcats

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

//class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class MainActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val storage = Firebase.storage
    override fun onBackPressed() {
        val intent = Intent(this@MainActivity, LogInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val userId = intent.getStringExtra("user_id").toString()
        when (item.itemId) {
            R.id.addnewpetmenu -> {
                val intent = Intent(this@MainActivity, AddLostPetActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra(
                    "user_id",
                    userId
                )
                startActivity(intent)
                finish()
            }
            R.id.logoutmenu -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@MainActivity, LogInActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userId = intent.getStringExtra("user_id").toString()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        val query = db.collection("allpets").orderBy("date")
        val options =
            FirestoreRecyclerOptions.Builder<LostPet>().setQuery(query, LostPet::class.java)
                .setLifecycleOwner(this).build()
        val adapter = object : FirestoreRecyclerAdapter<LostPet, PetViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
                val view = LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.lostpet_item, parent, false)
                return PetViewHolder(view)
            }

            override fun onBindViewHolder(holder: PetViewHolder, position: Int, model: LostPet) {
                val kind: TextView = holder.itemView.findViewById(R.id.kind)
                if (model.dog == "true") {
                    kind.text = "Dog"
                } else {
                    kind.text = "Cat"
                }
                val name: TextView = holder.itemView.findViewById(R.id.displayname)
                name.text = model.name
                val place: TextView = holder.itemView.findViewById(R.id.displayplace)
                place.text = model.place
                val number: TextView = holder.itemView.findViewById(R.id.displaynumber)
                number.text = "If see, please call " + model.number
                val desc: TextView = holder.itemView.findViewById(R.id.displaydescription)
                desc.text = model.description
                val imagetest: ImageView = holder.itemView.findViewById(R.id.enterImage)
                val imageref = Firebase.storage.reference.child(model.photo)
                imageref.downloadUrl.addOnSuccessListener { Uri ->

                    val imageURL = Uri.toString()
                    Glide.with(this@MainActivity)
                        .load(imageURL)
                        .into(imagetest)

                }

                val buttonSee: Button = holder.itemView.findViewById(R.id.see)
                buttonSee.setOnClickListener {
                    val intent2 = Intent(this@MainActivity, PetActivity::class.java)
                    intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent2.putExtra("petId", model.petId)
                    intent2.putExtra("petName", model.name)
                    intent2.putExtra("isDog", model.dog)
                    intent2.putExtra("description", model.description)
                    intent2.putExtra("photo", model.photo)
                    intent2.putExtra("date", model.date)
                    intent2.putExtra("place", model.place)
                    intent2.putExtra("number", model.number)
                    intent2.putExtra("userId", model.userId)
                    intent2.putExtra("user", userId)
                    startActivity(intent2)
                    finish()
                }
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}