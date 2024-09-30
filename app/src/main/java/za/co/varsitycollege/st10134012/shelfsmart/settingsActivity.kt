package za.co.varsitycollege.st10134012.shelfsmart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import za.co.varsitycollege.st10134012.shelfsmart.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    // Declare binding variable and GoogleSignInClient
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize GoogleSignInClient
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Retrieve the user email from shared preferences
        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("userEmail", "No user logged in")
        binding.userEmail.text = userEmail


        // Set up the Google Sign-Out Button
        binding.signOutButton.setOnClickListener {
            signOut()
        }

        // Set up other option click listeners (Language, Wi-Fi, Notifications)
        binding.forwardIconProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.forwardIconLanguage.setOnClickListener {
            startActivity(Intent(this, OptionsActivity::class.java))
        }

        binding.forwardIconWifi.setOnClickListener {
            startActivity(Intent(this, OptionsActivity::class.java))
        }

        binding.forwardIconNotification.setOnClickListener {
            startActivity(Intent(this, OptionsActivity::class.java))
        }
    }

    // Function to sign out from FirebaseAuth and GoogleSignIn
    private fun signOut() {
        // Firebase sign out
        firebaseAuth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show()

            // After sign out, navigate back to the login screen (or wherever appropriate)
            val intent = Intent(this,Registration::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Close the current activity
        }.addOnFailureListener {
            Toast.makeText(this, "Sign out failed: ${it.message}", Toast.LENGTH_LONG).show()
        }
    }
}
