package za.co.varsitycollege.st10134012.shelfsmart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import za.co.varsitycollege.st10134012.shelfsmart.databinding.ActivityPasswordeditBinding
import com.google.firebase.database.*
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

class EditPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordeditBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordeditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        // Handle back button click
        binding.backButton.setOnClickListener {
            finish() // Closes the current activity
        }

        // Set up the "Edit Password" button listener
        binding.editPasswordButton.setOnClickListener {
            val newPassword = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

            // Check if both fields are filled and passwords match
            if (newPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (newPassword == confirmPassword) {
                    updatePassword(newPassword)
                } else {
                    Toast.makeText(this@EditPasswordActivity, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@EditPasswordActivity, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to update password in Firebase
    private fun updatePassword(newPassword: String) {
        val userId = getCurrentUserId() // You can implement this function to get the logged-in user ID
        if (userId != null) {
            val salt = generateSalt()
            val hashedPassword = hashPassword(newPassword, salt)

            val userUpdates = mapOf(
                "password" to hashedPassword,
                "salt" to salt
            )

            databaseReference.child(userId).updateChildren(userUpdates)
                .addOnSuccessListener {
                    Toast.makeText(this@EditPasswordActivity, "Password updated successfully", Toast.LENGTH_SHORT).show()
                    // Redirect to profile or another screen if necessary
                    startActivity(Intent(this@EditPasswordActivity, ProfileActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this@EditPasswordActivity, "Password update failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Generate a random salt
    private fun generateSalt(): String {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return Base64.getEncoder().encodeToString(salt)
    }

    // Hash the password with salt using SHA-256
    private fun hashPassword(password: String, salt: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val passwordBytes = password.toByteArray()
        val saltBytes = Base64.getDecoder().decode(salt)
        messageDigest.update(saltBytes)
        val hashedBytes = messageDigest.digest(passwordBytes)
        return Base64.getEncoder().encodeToString(hashedBytes)
    }

    // You can implement this function to retrieve the currently logged-in user's ID
    private fun getCurrentUserId(): String? {
        // Retrieve the user's ID from session or authentication provider
        return "user_id_example" // Replace with actual user ID
    }
}
