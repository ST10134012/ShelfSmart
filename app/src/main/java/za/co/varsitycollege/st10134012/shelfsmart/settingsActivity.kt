package za.co.varsitycollege.st10134012.shelfsmart


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import za.co.varsitycollege.st10134012.shelfsmart.databinding.ActivitySettingsBinding


class SettingsActivity : AppCompatActivity() {

    // Declare binding variable
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up click listeners for menu icons
        binding.menuIcon.setOnClickListener {
            Toast.makeText(this, "Menu icon clicked", Toast.LENGTH_SHORT).show()
        }

        // Handle click for the forward icon in user info section
        binding.forwardIconProfile.setOnClickListener {
            Toast.makeText(this, "User Profile Forward icon clicked", Toast.LENGTH_SHORT).show()
        }

        // Language option click listener
        binding.forwardIconLanguage.setOnClickListener {
            Toast.makeText(this, "Language Forward icon clicked", Toast.LENGTH_SHORT).show()
        }

        // Wi-Fi option click listener
        binding.forwardIconWifi.setOnClickListener {
            Toast.makeText(this, "Wi-Fi Forward icon clicked", Toast.LENGTH_SHORT).show()
        }

        // Notifications option click listener
        binding.forwardIconNotification.setOnClickListener {
            Toast.makeText(this, "Notifications Forward icon clicked", Toast.LENGTH_SHORT).show()
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