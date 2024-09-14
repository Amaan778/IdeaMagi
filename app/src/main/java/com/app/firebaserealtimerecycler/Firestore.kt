package com.app.firebaserealtimerecycler

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class Firestore : AppCompatActivity() {

    private lateinit var name:EditText
    private lateinit var last:EditText
    private lateinit var add:EditText
    private lateinit var pincode:EditText
    private lateinit var addData:Button
    private var progressDialog:Dialog?=null
    private lateinit var db: FirebaseFirestore
    private lateinit var btnrecycler:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firestore)

        name=findViewById(R.id.names)
        last=findViewById(R.id.lastnames)
        add=findViewById(R.id.add)
        pincode=findViewById(R.id.pins)
        addData=findViewById(R.id.retrive)
        btnrecycler=findViewById(R.id.firestore)


        addData.setOnClickListener {

            val namess=name.text.toString()
            val lastss=last.text.toString()
            val addres=add.text.toString()
            val pins=pincode.text.toString()

//            updating data
            showprogree()

            db = FirebaseFirestore.getInstance()

//            // Example: Update user data
            val userId = "Android"
            val updatedData = mapOf(
                "name" to namess,
                "lastname" to lastss,
                "address" to addres,
                "pincode" to pins
            )

            updateUser(userId, updatedData)

//            adding data
//            showprogree()
//
//            db = FirebaseFirestore.getInstance()
//
//            val user=User(namess,lastss,addres,pins)
//
//            db.collection("users").document()
//                .set(user)
//                .addOnSuccessListener {
//                    Toast.makeText(this,"Data Added Successfully",Toast.LENGTH_LONG).show()
//                    progressDialog?.dismiss()
//                    Log.d("MainActivity", "DocumentSnapshot successfully written!")
//                }
//                .addOnFailureListener { e ->
//                    progressDialog?.dismiss()
//                    Toast.makeText(this,"Data not added",Toast.LENGTH_LONG).show()
//                    Log.w("MainActivity", "Error writing document", e)
//                }


        }

        btnrecycler.setOnClickListener {
            startActivity(Intent(this,FirestoreRecycler::class.java))
        }

    }
    private fun showprogree(){
        progressDialog= Dialog(this)
        progressDialog?.setContentView(R.layout.progressbar)
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    private fun updateUser(userId: String, updatedData: Map<String, Any>) {
        // Reference the Firestore instance
        val db = FirebaseFirestore.getInstance()

        // Update the document with the specified ID in the "users" collection
        db.collection("users").document(userId)
            .update(updatedData)
            .addOnSuccessListener {
                progressDialog?.dismiss()
                Log.d("MainActivity", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                progressDialog?.dismiss()
                Log.w("MainActivity", "Error updating document", e)
            }
    }

    private fun fetchUser(documentId: String) {
        // Reference to the document
        val docRef = db.collection("users").document(documentId)

        // Get the document
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Document found, parse the data into the User data class
                    val user = document.toObject(User::class.java)
                    user?.let {
                        Log.d("MainActivity", "User data: ${it.name}, ${it.address}, ${it.lastname}")
                        // You can update the UI or do something with the data here

                    }
                } else {
                    Log.d("MainActivity", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("MainActivity", "Get failed with ", exception)
            }
    }

    private fun deleteUser(documentId: String) {
        // Reference to the document
        val docRef = db.collection("users").document(documentId)

        // Delete the document
        docRef.delete()
            .addOnSuccessListener {
                Log.d("MainActivity", "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w("MainActivity", "Error deleting document", e)
            }
    }

}