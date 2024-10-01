package za.co.varsitycollege.st10134012.shelfsmart

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class BookDetailActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var authorTextView: TextView
    private lateinit var genreTextView: TextView
    private lateinit var publicationYearTextView: TextView
    private lateinit var isbnTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        // Initialize Firebase Auth and Database
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")

        titleTextView = findViewById(R.id.titleTextView)
        authorTextView = findViewById(R.id.authorTextView)
        genreTextView = findViewById(R.id.genreTextView)
        publicationYearTextView = findViewById(R.id.publicationYearTextView)
        isbnTextView = findViewById(R.id.isbnTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        imageView = findViewById(R.id.imageView)
        saveButton = findViewById(R.id.save_btn)

        // Retrieve the book data
        val book: Books? = intent.getParcelableExtra("BOOK_DATA")

        book?.let {
            titleTextView.text = it.title
            authorTextView.text = it.author
            genreTextView.text = it.genre
            publicationYearTextView.text = it.publicationYear.toString()
            isbnTextView.text = it.isbn
            descriptionTextView.text = it.description

            it.image?.let { imageUrl ->
                Picasso.get().load(imageUrl).into(imageView)
            }

            // Set up back button listener
            findViewById<ImageButton>(R.id.menu_icon).setOnClickListener {
                finish() // Finish the current activity and return to MainActivity
            }

            // Handle the "Add to My Shelf" button click
            saveButton.setOnClickListener {
                // Add the book to the current user's shelf
                currentUser?.uid?.let { userId ->
                    book?.let { currentBook ->  // Use 'book' instead of 'it'
                        addToUserShelf(userId, currentBook) // Pass the book object
                    }
                } ?: run {
                    Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    // Function to add the book to the current user's shelf in Firebase
    private fun addToUserShelf(userId: String, book: Books) {
        // Create a unique ID for the book
        val bookId = databaseReference.child(userId).child("user_books").push().key

        bookId?.let {
            // Add the book to the user's shelf in Firebase under "users/{userId}/user_books/{bookId}"
            databaseReference.child(userId).child("user_books").child(it).setValue(book)
                .addOnSuccessListener {
                    Toast.makeText(this@BookDetailActivity, "Book added to shelf", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this@BookDetailActivity, "Failed to add book: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
