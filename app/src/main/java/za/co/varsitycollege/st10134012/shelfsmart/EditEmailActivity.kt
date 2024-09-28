package za.co.varsitycollege.st10134012.shelfsmart

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import za.co.varsitycollege.st10134012.shelfsmart.databinding.ActivityEmaileditBinding

class EditEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmaileditBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmaileditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        // Back button functionality
        binding.backButton.setOnClickListener {
            finish() // Go back to the previous activity
        }

        // Handle email update button click
        binding.editEmailButton.setOnClickListener {
            val newEmail = binding.emailEditText.text.toString().trim()
            val confirmEmail = binding.confirmEmailEditText.text.toString().trim()

            // Validate input
            if (newEmail.isNotEmpty() && confirmEmail.isNotEmpty()) {
                if (newEmail == confirmEmail) {
                    updateEmail(newEmail)
                } else {
                    Toast.makeText(this, "Emails do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter and confirm the email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateEmail(newEmail: String) {
        val user = firebaseAuth.currentUser

        // Update email in Firebase Authentication
        user?.updateEmail(newEmail)
            ?.addOnSuccessListener {
                Toast.makeText(this, "Email updated in FirebaseAuth", Toast.LENGTH_SHORT).show()

                // Update email in Firebase Database
                val userId = user.uid
                val databaseReference = firebaseDatabase.reference.child("users").child(userId)
                databaseReference.child("email").setValue(newEmail)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Email updated in Database", Toast.LENGTH_SHORT).show()
                        finish() // Return to the previous screen
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to update email in Database: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            ?.addOnFailureListener { e: Exception ->
                Toast.makeText(this, "Failed to update email: ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }
    }

