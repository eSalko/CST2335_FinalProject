package algonquin.cst2335.cst2335_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.cst2335_finalproject.databinding.ActivityMainBinding;
import algonquin.cst2335.cst2335_finalproject.databinding.ActivityPexelsBinding;
import algonquin.cst2335.cst2335_finalproject.databinding.SearchedItemBinding;

public class Pexels extends AppCompatActivity {

    /**
     * Variable binding to access layout items for activity_pexels.xml
     * */
    ActivityPexelsBinding binding;
    /**
     * ViewModel for main pexels activity
     * */
    PexelsViewModel pexelModel;
    /**
     * Array of items loaded from Pexels API
     * */
    ArrayList<SearchedItem> items = new ArrayList<>();
    /**
     * RecyclerView Adapter to render items inside it
     * */
    RecyclerView.Adapter myAdapter;
    /**
     * Static DAO variable to access database across activities
     * */
    static SearchedItemDAO iDAO;
    /**
     * Bitmap of image requested from Pexels API
     * */
    Bitmap image = null;
    /**
     * RequestQueue variable for Volley requests
     * */
    RequestQueue queue = null;
    /**
     * Byte array to store converted Bitmap so that it can be put into BLOB in the database
     * */
    byte[] imageData = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.favImgs:
                Intent i = new Intent(this, FavPexels.class);
                startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPexelsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pexelModel = new ViewModelProvider(this).get(PexelsViewModel.class);
        items = pexelModel.items.getValue();

        ItemDatabase db = Room.databaseBuilder(getApplicationContext(), ItemDatabase.class, "database-name").build();
        iDAO = db.siDAO();

        queue = Volley.newRequestQueue(this);
        if(items == null){
            pexelModel.items.setValue(items = new ArrayList<>());
        }

        setSupportActionBar(binding.toolBar);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        binding.searchBtn.setOnClickListener(clk -> {
            String searchQuery = binding.queryEdit.getText().toString();
            String stringURL = "";

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("QueryText", searchQuery);
            editor.apply();

            try {
                stringURL = "https://api.pexels.com/v1/search?query=" + URLEncoder.encode(searchQuery, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            StringRequest request = new StringRequest
                    (Request.Method.GET, stringURL, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray photos = jsonObject.getJSONArray("photos");
                                for(int i=0;i< photos.length();i++){
                                    JSONObject objectAtI = photos.getJSONObject(i);
                                    int id = objectAtI.getInt("id");
                                    String photographer = objectAtI.getString("photographer");
                                    int height = objectAtI.getInt("height");
                                    int width = objectAtI.getInt("width");
                                    JSONObject src = objectAtI.getJSONObject("src");
                                    String imageURL = src.getString("tiny");
                                    ImageRequest imgReq = new ImageRequest(imageURL, new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            try {
                                                image = bitmap;
                                                ByteArrayOutputStream b = new ByteArrayOutputStream();
                                                image.compress(Bitmap.CompressFormat.PNG, 100, Pexels.this.openFileOutput(id + ".png", Activity.MODE_PRIVATE));
                                                image.compress(Bitmap.CompressFormat.PNG, 100, b);
                                                imageData = b.toByteArray();

                                                SearchedItem obj = new SearchedItem(id,imageData,photographer,height,width,imageURL);
                                                items.add(obj);
                                                runOnUiThread(()->{
                                                    binding.queryView.setAdapter(myAdapter);

                                                });
                                                myAdapter.notifyItemInserted(items.size()-1);
                                            } catch (IOException e) {
                                                e.printStackTrace();

                                            }

                                        }
                                    }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error ) -> {

                                    });
                                    queue.add(imgReq);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  header = new HashMap<String, String>();
                    header.put("Authorization", "563492ad6f917000010000016dd37f89df024e15a714d0235c8c6db9");

                    return header;
                }
            };
            queue.add(request);

        });

        String initialQuery = prefs.getString("QueryText", "");
        binding.queryEdit.setText(initialQuery);

        binding.queryView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
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

                SearchedItem obj = items.get(position);
                holder.photographer.setText(obj.getPhotographer());
                holder.photo.setImageBitmap(obj.getImagePic());
            }


            @Override
            public int getItemCount() {
                return (items == null) ? 0 : items.size();
            }
        });

        binding.queryView.setLayoutManager(new LinearLayoutManager(this));

        pexelModel.selectedItem.observe(this, (newItemValue) -> {
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            ItemDetailsFragment chatFragment = new ItemDetailsFragment(newItemValue, iDAO);

            tx.replace(R.id.fragmentLayout, chatFragment);
            tx.commit();
        });



    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView photographer;

        public MyRowHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(clk->{
                int position = getAbsoluteAdapterPosition();
                SearchedItem selected = items.get(position);

                pexelModel.selectedItem.postValue(selected);
            });

            photo = itemView.findViewById(R.id.imageView);
            photographer = itemView.findViewById(R.id.photographerText);
        }


    }
}