package za.co.varsitycollege.st10134012.shelfsmart.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import za.co.varsitycollege.st10134012.shelfsmart.Book;
import za.co.varsitycollege.st10134012.shelfsmart.MyAdapter;
import za.co.varsitycollege.st10134012.shelfsmart.R;
import za.co.varsitycollege.st10134012.shelfsmart.databinding.FragmentMyShelfBinding;

public class MyShelfFragment extends Fragment {

    private FragmentMyShelfBinding binding;
    private ArrayList<Book> bookArrayList;
    private String[] bookName, bookAuthor, bookCategory, bookDescription;
    private int[] bookImageID;
    private RecyclerView recyclerview;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private MyAdapter myAdapter;
    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //MyShelfViewModel myShelfViewModel =
          //      new ViewModelProvider(this).get(MyShelfViewModel.class);

        binding = FragmentMyShelfBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //progressDialog = new ProgressDialog(getContext());
        //progressDialog.setCancelable(false);
        //progressDialog.setMessage("Fetching Data...");
        //progressDialog.show();

        dataInitialize();

        recyclerview = root.findViewById(R.id.recycleview_book);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);

        //db = FirebaseFirestore.getInstance();
        //databaseReference = FirebaseDatabase.getInstance().getReference("Book");

        bookArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(getContext(), bookArrayList);
        recyclerview.setAdapter(myAdapter);

        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Book book = dataSnapshot.getValue(Book.class);
                    bookArrayList.add(book);
                }
                myAdapter.notifyDataSetChanged();
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (error != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.e("Firestore error", error.getMessage());
                    return;
                }
            }
        });*/

        myAdapter.notifyDataSetChanged();

        return root;
    }

    private void dataInitialize() {
        bookArrayList = new ArrayList<>();
        bookName = new String[] {
                "Book 1",
                "Book 2",
                "Book 3",
                "Book 4",
                "Book 5",
                "Book 6",
                "Book 7",
                "Book 8",
                "Book 9",
                "Book 10"
        };
        bookAuthor = new String[] {
                "Joe Smith",
                "Joe Smith",
                "Joe Smith",
                "Joe Smith",
                "Joe Smith",
                "Joe Smith",
                "Joe Smith",
                "Joe Smith",
                "Joe Smith",
                "Joe Smith"
        };
        bookCategory = new String[] {
                getString(R.string.category_3),
                getString(R.string.category_1),
                getString(R.string.category_8),
                getString(R.string.category_5),
                getString(R.string.category_5),
                getString(R.string.category_3),
                getString(R.string.category_6),
                getString(R.string.category_4),
                getString(R.string.category_2),
                getString(R.string.category_4)
        };

        bookDescription = new String[] {
                getString(R.string.description_1),
                getString(R.string.description_2),
                getString(R.string.description_3),
                getString(R.string.description_4),
                getString(R.string.description_5),
                getString(R.string.description_6),
                getString(R.string.description_7),
                getString(R.string.description_8),
                getString(R.string.description_9),
                getString(R.string.description_10)
        };

        /*bookImageID = new int[]{
                R.drawable.ic_event_24dp,
                R.drawable.ic_event_24dp,
                R.drawable.ic_event_24dp,
                R.drawable.ic_event_24dp,
                R.drawable.ic_event_24dp,
                R.drawable.ic_event_24dp,
                R.drawable.ic_event_24dp,
                R.drawable.ic_event_24dp,
                R.drawable.ic_event_24dp,
                R.drawable.ic_event_24dp,
        };*/
        for (int i=0; i<bookName.length; i++){
            Book book = new Book(bookName[i], bookAuthor[i], bookCategory[i], bookDescription[i] /*, sellerImageID[i]*/);
            bookArrayList.add(book);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}