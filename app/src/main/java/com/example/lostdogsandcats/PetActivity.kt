package com.example.lostdogsandcats

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class PetActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    override fun onBackPressed() {
        val intent2 = Intent(this@PetActivity, MainActivity::class.java)
        intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent2.putExtra("user_id", intent.getStringExtra("user").toString())
        startActivity(intent2)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet)
        val recyclerView = findViewById<RecyclerView>(R.id.commentrecyclerview)
        val query = db.collection("comments")
            .whereEqualTo("petId", intent.getStringExtra("petId").toString())
            .orderBy("date")
        val options =
            FirestoreRecyclerOptions.Builder<Comment>().setQuery(query, Comment::class.java)
                .setLifecycleOwner(this).build()
        val adapter = object : FirestoreRecyclerAdapter<Comment, CommentViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
                val view = LayoutInflater.from(this@PetActivity)
                    .inflate(R.layout.comment_item, parent, false)
                return CommentViewHolder(view)
            }

            override fun onBindViewHolder(
                holder: CommentViewHolder,
                position: Int,
                model: Comment
            ) {
                val commentText: TextView = holder.itemView.findViewById(R.id.displayComment)
                commentText.text = model.commentText
                val displayCommentDateText: TextView =
                    holder.itemView.findViewById(R.id.displayCommentDate)
                displayCommentDateText.text = model.date
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val imageref = Firebase.storage.reference.child(intent.getStringExtra("photo").toString())
        imageref.downloadUrl.addOnSuccessListener { Uri ->
            val imageURL = Uri.toString()
            val image = findViewById<ImageView>(R.id.imagePetView)
            Glide.with(this@PetActivity)
                .load(imageURL)
                .into(image)
        }
        findViewById<TextView>(R.id.nameView).text = intent.getStringExtra("petName").toString()
        findViewById<TextView>(R.id.phoneView).text = intent.getStringExtra("number").toString()
        findViewById<TextView>(R.id.placeView).text = intent.getStringExtra("place").toString()
        findViewById<TextView>(R.id.descriptionView).text =
            intent.getStringExtra("description").toString()
        val petId = intent.getStringExtra("petId").toString()

        if (intent.getStringExtra("userId").toString() == intent.getStringExtra("user")
                .toString()
        ) {
            findViewById<TextView>(R.id.editButton).visibility = View.VISIBLE
        } else {
            findViewById<TextView>(R.id.editButton).visibility = View.GONE
        }
        findViewById<Button>(R.id.editButton).setOnClickListener {
            val intent2 = Intent(this@PetActivity, EditPetActivity::class.java)
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
        val addCommentButton = findViewById<Button>(R.id.addComment)
        addCommentButton.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.add_comment, null)
            val builder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Add comment")
            val alertDialog = builder.show()
            dialogView.findViewById<Button>(R.id.savecomment).setOnClickListener {
                alertDialog.dismiss()
                val commentText =
                    dialogView.findViewById<EditText>(R.id.commenttext).text.toString()
                val dateNow = SimpleDateFormat("dd.M.yyyy HH:mm:ss")
                val date = dateNow.format(Date()).toString()
                val commentToAdd = Comment(
                    intent.getStringExtra("petId").toString(),
                    intent.getStringExtra("user").toString(),
                    commentText,
                    date
                )
                addComment(commentToAdd)
                val intent3 = Intent(this@PetActivity, PetActivity::class.java)
                intent3.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent3.putExtra("petId", intent.getStringExtra("petId").toString())
                intent3.putExtra("petName", intent.getStringExtra("petName").toString())
                intent3.putExtra("isDog", intent.getStringExtra("isDog").toString())
                intent3.putExtra("description", intent.getStringExtra("description").toString())
                intent3.putExtra("photo", intent.getStringExtra("photo").toString())
                intent3.putExtra("date", intent.getStringExtra("date").toString())
                intent3.putExtra("place", intent.getStringExtra("place").toString())
                intent3.putExtra("number", intent.getStringExtra("number").toString())
                intent3.putExtra("userId", intent.getStringExtra("userId").toString())
                intent3.putExtra("user", intent.getStringExtra("user").toString())
                startActivity(intent3)
                finish()
            }
            dialogView.findViewById<Button>(R.id.cancelcomment).setOnClickListener {
                alertDialog.dismiss()
//                val intent2 = Intent(this@PetActivity, MainActivity::class.java)
//                intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                intent2.putExtra("user_id", intent.getStringExtra("user").toString())
//                startActivity(intent2)
//                finish()
            }
        }
    }

    private fun addComment(comment: Comment) {
        db.collection("comments")
            .add(comment)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }
}