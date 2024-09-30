package za.co.varsitycollege.st10134012.shelfsmart


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import za.co.varsitycollege.st10134012.shelfsmart.databinding.ActivitySettingsBinding


class SettingsActivity : AppCompatActivity() {

    // Declare binding variable
    private lateinit var binding: ActivitySettingsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the current logged-in user from FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // If the user is not null, set the user's email to the TextView
        if (currentUser != null) {
            val userEmail = currentUser.email
            binding.userEmail.text = userEmail
        } else {
            // Handle case when the user is not logged in
            binding.userEmail.text = "No user logged in"
        }

        // Set up click listeners for menu icons
        binding.menuIcon.setOnClickListener {
            Toast.makeText(this, "Menu icon clicked", Toast.LENGTH_SHORT).show()
        }

        // Handle click for the forward icon in user info section
        binding.forwardIconProfile.setOnClickListener {
            Toast.makeText(this, "User Profile Forward icon clicked", Toast.LENGTH_SHORT).show()
            val intent =Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }

        // Language option click listener
        binding.forwardIconLanguage.setOnClickListener {
            Toast.makeText(this, "Language Forward icon clicked", Toast.LENGTH_SHORT).show()
            val intent =Intent(this,OptionsActivity::class.java)
            startActivity(intent)
        }

        // Wi-Fi option click listener
        binding.forwardIconWifi.setOnClickListener {
            Toast.makeText(this, "Wi-Fi Forward icon clicked", Toast.LENGTH_SHORT).show()
            val intent =Intent(this,OptionsActivity::class.java)
            startActivity(intent)
        }

        // Notifications option click listener
        binding.forwardIconNotification.setOnClickListener {
            Toast.makeText(this, "Notifications Forward icon clicked", Toast.LENGTH_SHORT).show()
            val intent =Intent(this,OptionsActivity::class.java)
            startActivity(intent)
        }

        binding.forwardIconLanguage.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }

        binding.forwardIconWifi.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }

        binding.forwardIconNotification.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }
        }
        }
