package za.co.varsitycollege.st10134012.shelfsmart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import za.co.varsitycollege.st10134012.shelfsmart.databinding.ActivitySignUpBinding
import com.google.firebase.database.*

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        // Handle back arrow click (optional if you want to navigate back)
        binding.backArrow.setOnClickListener {
            finish() // Closes the current activity
        }

        // Set up the "Create Account" button listener
        binding.createAccountBtn.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            val confirmPassword = binding.confirmPasswordInput.text.toString().trim()

            // Check for mandatory fields and password confirmation
            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    signUpUser(email, password)
                } else {
                    Toast.makeText(this@SignUp, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@SignUp, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }

        // Redirect to login screen when clicking "Login"
        binding.loginLink.setOnClickListener {
            startActivity(Intent(this@SignUp, SignIn::class.java))
            finish()
        }
    }

    // Function to handle sign-up logic with Firebase
    private fun signUpUser(email: String, password: String) {
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    val id = databaseReference.push().key
                    val userData = UserData(id, email, password)
                    id?.let {
                        databaseReference.child(it).setValue(userData)
                            .addOnSuccessListener {
                                Toast.makeText(this@SignUp, "Sign Up successful", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@SignUp, SignIn::class.java))
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this@SignUp, "Sign Up failed: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    Toast.makeText(this@SignUp, "User already exists", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUp, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
