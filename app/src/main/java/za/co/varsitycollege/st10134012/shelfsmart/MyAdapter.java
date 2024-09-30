package za.co.varsitycollege.st10134012.shelfsmart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Book> bookArrayList;

    public MyAdapter(Context context, ArrayList<Book> bookArrayList){
        this.context = context;
        this.bookArrayList = bookArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Book book = bookArrayList.get(position);
        holder.tvBookName.setText(book.bookname);
        holder.tvBookAuthor.setText(book.author);
        holder.tvBookCategory.setText(book.category);
        holder.tvSellerDescription.setText(book.description);
        //holder.sellerImage.setImageResource(seller.image);
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //ShapeableImageView bookImage;
        TextView tvBookName, tvBookAuthor, tvBookCategory, tvSellerDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //bookImage = itemView.findViewById(R.id.book_image);
            tvBookName = itemView.findViewById(R.id.tv_book_name);
            tvBookAuthor = itemView.findViewById(R.id.tv_author);
            tvBookCategory = itemView.findViewById(R.id.tv_category);
            tvSellerDescription =  itemView.findViewById(R.id.tv_description);

        }
    }
}
