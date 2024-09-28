package za.co.varsitycollege.st10134012.shelfsmart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import za.co.varsitycollege.st10134012.shelfsmart.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        val userId = firebaseAuth.currentUser?.uid

        // Load and display user info
        userId?.let {
            loadUserInfo(it)
        }

        // Handle Edit Email button click
        binding.buttonEditEmail.setOnClickListener {
            val intent = Intent(this, EditEmailActivity::class.java)
            startActivity(intent)
        }

        // Handle Edit Password button click
        binding.buttonEditPassword.setOnClickListener {
            val intent = Intent(this, EditPasswordActivity::class.java)
            startActivity(intent)
        }

        // Handle Delete Account button click
        binding.buttonDeleteAccount.setOnClickListener {
            deleteUserAccount(userId)
        }
    }

    private fun loadUserInfo(userId: String) {
        // Fetch user info from Firebase
        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val email = dataSnapshot.child("email").getValue(String::class.java)
                binding.userEmail.text = email
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ProfileActivity, "Failed to load user data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteUserAccount(userId: String?) {
        userId?.let {
            databaseReference.child(it).removeValue()
                .addOnSuccessListener {
                    firebaseAuth.currentUser?.delete()
                        ?.addOnSuccessListener {
                            Toast.makeText(this@ProfileActivity, "Account deleted", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, SignIn::class.java))
                            finish()
                        }
                        ?.addOnFailureListener { e ->
                            Toast.makeText(this@ProfileActivity, "Failed to delete account: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this@ProfileActivity, "Database Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
