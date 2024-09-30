package za.co.varsitycollege.st10134012.shelfsmart

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search) // Replace with your actual layout

        // Find the search bar and icon
        val searchBar: EditText = findViewById(R.id.search_bar)
        val searchIcon: ImageView = findViewById(R.id.search_icon)

        // Set an OnClickListener for the search icon
        searchIcon.setOnClickListener {
            // Get the input from the search bar
            val searchQuery = searchBar.text.toString()

            // Create an intent to navigate to the SearchResultsActivity
            val intent = Intent(this, SearchResultsActivity::class.java)

            // Pass the search query to the SearchResultsActivity
            intent.putExtra("SEARCH_QUERY", searchQuery)

            // Start the SearchResultsActivity
            startActivity(intent)
        }
    }
}


