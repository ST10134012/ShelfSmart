package za.co.varsitycollege.st10134012.shelfsmart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.ValueEventListener
import za.co.varsitycollege.st10134012.shelfsmart.databinding.ActivityMyShelfBinding

class MyShelf : AppCompatActivity() {

    private lateinit var binding: ActivityMyShelfBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var adapter: BookAdapter
    private var userBooks = ArrayList<Books>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyShelfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth and Database
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")

        // Set up RecyclerView
        binding.recyclerViewMyShelf.layoutManager = LinearLayoutManager(this)

        // Set up the back button listener
        val menuIcon: ImageButton = findViewById(R.id.menu_icon)
        menuIcon.setOnClickListener {
            // Navigate back to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Load user's books
        loadUserBooks()
    }

    private fun loadUserBooks() {
        val currentUser = firebaseAuth.currentUser
        currentUser?.let { user ->
            databaseReference.child(user.uid).child("user_books").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userBooks.clear() // Clear existing data
                    for (bookSnapshot in snapshot.children) {
                        val book = bookSnapshot.getValue<Books>() // Assuming Books class has been set up properly
                        if (book != null) {
                            userBooks.add(book)
                        }
                    }
                    setupAdapter(userBooks)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MyShelf", "Failed to load user books: ${error.message}")
                }
            })
        } ?: run {
            Log.e("MyShelf", "User not logged in")
        }
    }

    private fun setupAdapter(data: List<Books>) {
        adapter = BookAdapter(data) { book ->
            // Handle click events if needed
        }
        binding.recyclerViewMyShelf.adapter = adapter
    }
}
