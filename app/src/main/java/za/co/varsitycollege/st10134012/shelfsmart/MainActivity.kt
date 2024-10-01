package za.co.varsitycollege.st10134012.shelfsmart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import za.co.varsitycollege.st10134012.shelfsmart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var data = ArrayList<Books>()
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        // Fetch the books
        getAllBooks()
    }

    private fun getAllBooks() {
        val retrofit = ServiceBuilder.buildService(ServiceInterface::class.java)

        retrofit.getAllBooks().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        data = responseBody.books
                        setupAdapter(data)
                    } ?: run {
                        Log.e("Error", "Response body is null")
                    }
                } else {
                    Log.e("Error", "Response failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("Failed", "API Call Failed: ${t.message}")
            }
        })
    }

    private fun setupAdapter(data: List<Books>) {
        adapter = BookAdapter(data) { book ->
            // Navigate to the book details activity
            val intent = Intent(this, BookDetailActivity::class.java)
            intent.putExtra("BOOK_DATA", book) // Pass the book object
            startActivity(intent)
        }
        binding.recyclerview.adapter = adapter
    }

}

