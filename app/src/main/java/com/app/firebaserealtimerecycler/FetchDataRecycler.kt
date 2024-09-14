package com.app.firebaserealtimerecycler

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchDataRecycler : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemList: ArrayList<User>
    private lateinit var adapter: RecyclerAdpater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_data_recycler)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        itemList = ArrayList()
        adapter = RecyclerAdpater(itemList)
        recyclerView.adapter = adapter

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().getReference("Users")

        // Fetch data
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(User::class.java)
                    item?.let { itemList.add(it) }
                }
                Toast.makeText(this@FetchDataRecycler,"Success",Toast.LENGTH_LONG).show()
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
                Toast.makeText(this@FetchDataRecycler,"Error",Toast.LENGTH_LONG).show()
                Log.e("MainActivity", "Database error: ${error.message}")
            }
        })

    }
}