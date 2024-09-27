package za.co.varsitycollege.st10134012.shelfsmart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import za.co.varsitycollege.st10134012.shelfsmart.databinding.ActivityEmailoptionBinding

class OptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailoptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityEmailoptionBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
