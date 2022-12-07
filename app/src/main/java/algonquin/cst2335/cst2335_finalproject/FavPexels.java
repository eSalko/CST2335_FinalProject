package algonquin.cst2335.cst2335_finalproject;

import static algonquin.cst2335.cst2335_finalproject.Pexels.iDAO;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.cst2335_finalproject.databinding.ActivityFavPexelsBinding;
import algonquin.cst2335.cst2335_finalproject.databinding.SearchedItemBinding;

public class FavPexels extends AppCompatActivity {

    /**
     * Variable binding to access layout items in activity_fav_pexels.xml
     * */
    ActivityFavPexelsBinding binding;
    /**
     * RecyclerView Adapter to render items inside it
     * */
    RecyclerView.Adapter myAdapter;
    /**
     * Array of favourite items
     * */
    ArrayList<SearchedItem> favItems = new ArrayList<>();
    /**
     * ViewModel for favPexels activity
     * */
    PexelsViewModel favModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFavPexelsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        favModel = new ViewModelProvider(this).get(PexelsViewModel.class);
        favItems = favModel.items.getValue();

        if(favItems == null){
            favModel.items.setValue(favItems = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(()->{
                favItems.addAll(iDAO.getAllItems());
                runOnUiThread(() -> binding.favView.setAdapter(myAdapter));
            });
        }



        binding.favView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SearchedItemBinding binding = SearchedItemBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.photographer.setText("");
                holder.photo.setImageBitmap(null);

                SearchedItem obj = favItems.get(position);
                holder.photographer.setText(obj.getPhotographer());
                holder.photo.setImageBitmap(obj.getImagePic());
            }


            @Override
            public int getItemCount() {
                return (favItems == null) ? 0 : favItems.size();
            }
        });

        binding.favView.setLayoutManager(new LinearLayoutManager(this));
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        /**
         * ImageView and TextView variables to initialize each row in the recycler view
         * */
        ImageView photo;
        TextView photographer;

        /**
         * If the user clicks on a row, ask the user if they want to delete the row. Then, display a snack bar to prompt undo.
         * @param itemView View object of each row
         * */
        public MyRowHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(clk->{
                int position = getAbsoluteAdapterPosition();
                SearchedItem selected = favItems.get(position);

                favModel.selectedItem.postValue(selected);

                AlertDialog.Builder builder = new AlertDialog.Builder(FavPexels.this);

                builder.setMessage("Do you want to delete the message: " + photographer.getText())
                        .setTitle("Question:")
                        .setNegativeButton("No", (dialog, cl) -> {})
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            SearchedItem removedMessage = favItems.get(position);
                            favItems.remove(position);
                            myAdapter.notifyItemRemoved(position);

                            Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute(()->{
                                iDAO.deleteItem(removedMessage);

                                runOnUiThread(() -> binding.favView.setAdapter(myAdapter));
                            });

                            Snackbar.make(photographer, "You deleted photo #" + position, Snackbar.LENGTH_SHORT)
                                    .setAction("Undo", click ->{
                                        favItems.add(position, removedMessage);
                                        Executor thread1 = Executors.newSingleThreadExecutor();
                                        thread1.execute(()->{
                                            iDAO.insertItem(removedMessage);

                                            runOnUiThread(() -> binding.favView.setAdapter(myAdapter));
                                        });
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();
                        })
                        .create().show();
            });

            photo = itemView.findViewById(R.id.imageView);
            photographer = itemView.findViewById(R.id.photographerText);
        }


    }

}