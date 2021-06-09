package com.example.lostdogsandcats

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class AddLostPetActivity : AppCompatActivity() {

    val storage = Firebase.storage
    val db = FirebaseFirestore.getInstance()

    lateinit var imageView: ImageView
    lateinit var button: Button
    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onBackPressed() {
        val userid = intent.getStringExtra("user_id").toString()
        val intent = Intent(this@AddLostPetActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(
            "user_id",
            userid
        )
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lost_pet)
        val userid = intent.getStringExtra("user_id").toString()

        imageView = findViewById<ImageView>(R.id.imageView)
        button = findViewById<Button>(R.id.buttonLoadPicture)
        button.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        val saveButton = findViewById<Button>(R.id.button_save)
        saveButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.name).text.toString()
            val description = findViewById<EditText>(R.id.description).text.toString()
            val dateNow = SimpleDateFormat("dd.M.yyyy HH:mm:ss")
            val date = dateNow.format(Date()).toString()
            val place = findViewById<EditText>(R.id.place).text.toString()
            val number = findViewById<EditText>(R.id.number).text.toString()
            val userId = userid
            var isDog = "false"
            val id = findViewById<RadioGroup>(R.id.dog_or_cat).checkedRadioButtonId
            when (id) {
                R.id.cat -> isDog = "false"
                else -> isDog = "true"
            }
            val photo = userId + date + ".jpg"
            val petId = userId + date
            val petToAdd =
                LostPet(petId, name, isDog, description, photo, date, place, number, userId)
            addPet(petToAdd, petId)
            //For uploading image
            val storageRef = storage.reference

            val mountainsRef = storageRef.child(photo)

            val mountainImagesRef = storageRef.child("images/" + photo)

            mountainsRef.name == mountainImagesRef.name // true
            mountainsRef.path == mountainImagesRef.path // false

            val imageView = findViewById<ImageView>(R.id.imageView)
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)


            //imageView.setImageResource(R.drawable.image2)
            imageView.isDrawingCacheEnabled = true
            imageView.buildDrawingCache()


            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            var uploadTask = mountainsRef.putBytes(data)
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
//                val intent = Intent(this@AddLostPetActivity, MainActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                intent.putExtra(
//                    "user_id",
//                    userid
//                )
//                startActivity(intent)
//                finish()
            }
            val intent = Intent(this@AddLostPetActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(
                "user_id",
                userid
            )
            startActivity(intent)
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

    private fun addPet(pet: LostPet, petID: String) {
        db.collection("allpets").document(petID).set(pet)
    }

    private fun deletePet(pet: String) {
        db.collection("allpets").document("${pet}")
            .delete()
    }

}


