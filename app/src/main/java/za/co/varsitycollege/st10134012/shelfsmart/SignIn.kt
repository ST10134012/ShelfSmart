package za.co.varsitycollege.st10134012.shelfsmart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import za.co.varsitycollege.st10134012.shelfsmart.databinding.ActivitySignInBinding
import com.google.firebase.database.*
import java.security.MessageDigest
import java.util.Base64

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        // Set up "Login" button listener
        binding.loginBtn.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this@SignIn, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }

        // Redirect to Sign Up page when clicking on "Sign Up"
        binding.signUpLink.setOnClickListener {
            startActivity(Intent(this@SignIn, SignUp::class.java))
            finish()
        }

        // Handle back arrow click (optional)
        binding.backArrow.setOnClickListener {
            finish() // Closes the current activity
        }
    }

    // Function to handle login logic with Firebase
    // Function to handle login logic with Firebase
    private fun loginUser(email: String, password: String) {
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val userData = userSnapshot.getValue(UserData::class.java)

                        if (userData != null) {
                            val storedSalt = userData.salt ?: ""
                            val storedHashedPassword = userData.passwordHash ?: ""
                            val hashedInputPassword = hashPassword(password, storedSalt)

                            // Compare the provided password hash with the stored hash
                            if (hashedInputPassword == storedHashedPassword) {
                                Toast.makeText(this@SignIn, "Login Successful", Toast.LENGTH_SHORT).show()
                                val userId = userData.id ?: "" // Handle possible null value for id
                                // Store the userId in shared preferences
                                val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("userId", userId)
                                editor.putString("userEmail", email) // Save the email as well
                                editor.apply()
                                // Start main activity and finish login activity
                                val intent = Intent(this@SignIn, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                                return
                            } else {
                                Toast.makeText(this@SignIn, "Login failed: Incorrect password", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this@SignIn, "Login failed: User not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignIn, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    // Hash the input password with the stored salt using SHA-256
    private fun hashPassword(password: String, salt: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val passwordBytes = password.toByteArray()
        val saltBytes = Base64.getDecoder().decode(salt)
        messageDigest.update(saltBytes) // Add salt to the hash calculation
        val hashedBytes = messageDigest.digest(passwordBytes) // Hash the password with salt
        return Base64.getEncoder().encodeToString(hashedBytes) // Convert to Base64 for comparison
    }
}


