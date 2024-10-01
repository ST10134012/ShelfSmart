package za.co.varsitycollege.st10134012.shelfsmart

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class BookAdapter(private val mList: List<Books>, private val clickListener: (Books) -> Unit) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = mList[position]

        holder.textView.text = book.title
        book.image?.let { imageUrl ->
            Picasso.get().load(imageUrl).into(holder.imageView)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, BookDetailActivity::class.java)
            intent.putExtra("BOOK_DATA", book) // Pass the book data to the detail activity
            holder.itemView.context.startActivity(intent)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}
