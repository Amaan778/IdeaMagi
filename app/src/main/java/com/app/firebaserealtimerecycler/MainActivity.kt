package com.app.firebaserealtimerecycler

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var btn: Button
    private lateinit var database: DatabaseReference
    private var progressDialog:Dialog?=null
    private lateinit var first: EditText
    private lateinit var lastname: EditText
    private lateinit var add: EditText
    private lateinit var pin: EditText
    private lateinit var start: Button
    private lateinit var btnsubmit:Button
    private lateinit var firestores:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        first=findViewById(R.id.names)
        lastname=findViewById(R.id.lastnames)
        add=findViewById(R.id.add)
        pin=findViewById(R.id.pins)
        start=findViewById(R.id.retrive)
        btnsubmit=findViewById(R.id.submit)
        firestores=findViewById(R.id.fire)

        start.setOnClickListener(View.OnClickListener {

            val firstn=first.text.toString()
            val lastn=lastname.text.toString()
            val adds=add.text.toString()
            val pins=pin.text.toString()

            showprogree()

            addValue(firstn,lastn,adds,pins)
        })

        btnsubmit.setOnClickListener {
            startActivity(Intent(this,FetchDataRecycler::class.java))
        }

        firestores.setOnClickListener {
            startActivity(Intent(this, Firestore::class.java))
        }


    }
    private fun showprogree(){
        progressDialog= Dialog(this)
        progressDialog?.setContentView(R.layout.progressbar)
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    private fun addValue(firstn: String, lastn: String, adds: String, pins: String) {

        database= FirebaseDatabase.getInstance().getReference("Users")
        val users=User(firstn,lastn,adds,pins)
        database.child(firstn).setValue(users).addOnSuccessListener {

            first.text.clear()
            lastname.text.clear()
            add.text.clear()
            pin.text.clear()

            progressDialog?.dismiss()
            Toast.makeText(this,"Success", Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
            progressDialog?.dismiss()
        }

    }
}