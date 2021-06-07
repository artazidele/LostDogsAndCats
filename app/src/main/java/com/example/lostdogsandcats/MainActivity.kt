package com.example.lostdogsandcats

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class MainActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
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

//        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val userId = intent.getStringExtra("user_id").toString()


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

//        val query = db.collection("comments")
        val query = db.collection("allpets").orderBy("date")
        val options = FirestoreRecyclerOptions.Builder<LostPet>().setQuery(query, LostPet::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object: FirestoreRecyclerAdapter<LostPet, PetViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
                val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.lostpet_item, parent, false)
                return PetViewHolder(view)
            }
//        val options = FirestoreRecyclerOptions.Builder<Comment>().setQuery(query, Comment::class.java)
//            .setLifecycleOwner(this).build()
//        val adapter = object: FirestoreRecyclerAdapter<Comment, CommentViewHolder>(options) {
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
//                val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.lostpet_item, parent, false)
//                return CommentViewHolder(view)
//            }

            override fun onBindViewHolder(holder: PetViewHolder, position: Int, model: LostPet) {
//                override fun onBindViewHolder(holder: CommentViewHolder, position: Int, model: Comment) {

//                val commentText: TextView = holder.itemView.findViewById(R.id.kind)
//                commentText.text = model.commentText
                val kind: TextView = holder.itemView.findViewById(R.id.kind)
                if (model.dog == "true"){
                    kind.text = "Dog"
                } else {
                    kind.text = "Cat"
                }
                val name: TextView = holder.itemView.findViewById(R.id.displayname)
                name.text = model.name
                val place: TextView = holder.itemView.findViewById(R.id.displayplace)
                place.text = model.place
                val photo: TextView = holder.itemView.findViewById(R.id.displayphoto)
                photo.text = model.photo
                val number: TextView = holder.itemView.findViewById(R.id.displaynumber)
                number.text = "If see, please call " + model.number
                val desc: TextView = holder.itemView.findViewById(R.id.displaydescription)
                desc.text = model.description

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



//        val myDataset = Datasource().loadAllPets()
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
//        recyclerView.adapter = ItemAdapter(this, myDataset)
//        //recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = LinearLayoutManager(this)

        //val userId = intent.getStringExtra("user_id")


//        val addPetButton = findViewById<Button>(R.id.add)
//        addPetButton.setOnClickListener {
//            //startActivity(Intent(this@MainActivity, AddLostPetActivity::class.java))
//
//
//            val intent = Intent(this@MainActivity, AddLostPetActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            intent.putExtra(
//                "user_id",
//                userId
//            )
//            //intent.putExtra("email_id", email)
//            startActivity(intent)
//            finish()
//
//
//        }

//        val logOutButton = findViewById<Button>(R.id.log_out)
//        logOutButton.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            startActivity(Intent(this@MainActivity, LogInActivity::class.java))
//            finish()
//        }
//        val addCommentButton = findViewById<Button>(R.id.addComment)
//        addCommentButton.setOnClickListener {
//            val dialogView = LayoutInflater.from(this).inflate(R.layout.add_comment, null)
//            val builder = AlertDialog.Builder(this)
//                .setView(dialogView)
//                .setTitle("Add comment")
//            val alertDialog = builder.show()
//            dialogView.findViewById<Button>(R.id.savecomment).setOnClickListener {
//                alertDialog.dismiss()
//                val commentText = dialogView.findViewById<EditText>(R.id.commenttext).text.toString()
//                //SAVE TO DB
//                val commentToAdd = Comment("Q6zpReNfdTCPExlOKFZO", userId, commentText)
//                addComment(commentToAdd)
//            }
//            dialogView.findViewById<Button>(R.id.cancelcomment).setOnClickListener {
//                alertDialog.dismiss()
//            }
//        }

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
    }
//    private fun addComment(comment: Comment){
//        db.collection("comments")
//            .add(comment)
//            .addOnSuccessListener { documentReference ->
//                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(ContentValues.TAG, "Error adding document", e)
//            }
//    }
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