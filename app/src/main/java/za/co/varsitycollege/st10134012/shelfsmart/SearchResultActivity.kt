package za.co.varsitycollege.st10134012.shelfsmart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class SearchResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        // Get the search query from the intent
        val searchQuery = intent.getStringExtra("SEARCH_QUERY")

        // Find the TextView and display the search query
        val searchResultsTextView: TextView = findViewById(R.id.search_results_text)
        searchResultsTextView.text = "You searched for: $searchQuery"
    }
}

