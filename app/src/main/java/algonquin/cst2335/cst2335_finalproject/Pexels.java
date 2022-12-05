package algonquin.cst2335.cst2335_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import algonquin.cst2335.cst2335_finalproject.databinding.ActivityMainBinding;
import algonquin.cst2335.cst2335_finalproject.databinding.ActivityPexelsBinding;
import algonquin.cst2335.cst2335_finalproject.databinding.SearchedItemBinding;

public class Pexels extends AppCompatActivity {

    ActivityPexelsBinding binding;
    PexelsViewModel pexelModel;
    ArrayList<SearchedItem> items = new ArrayList<>();
    RecyclerView.Adapter myAdapter;
    SearchedItemDAO iDAO;
    Bitmap image = null;
    RequestQueue queue = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPexelsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pexelModel = new ViewModelProvider(this).get(PexelsViewModel.class);
        items = pexelModel.items.getValue();

        ItemDatabase db = Room.databaseBuilder(getApplicationContext(), ItemDatabase.class, "database-name").build();
        iDAO = db.siDAO();

        binding.searchBtn.setOnClickListener(clk -> {
            String searchQuery = binding.queryEdit.getText().toString();
            String stringURL = "";

            try {
                stringURL = "https://api.pexels.com/v1/search?query=" + URLEncoder.encode(searchQuery, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            SearchedItem obj = new SearchedItem(1,"heloo", 2,3, "fea");
            items.add(obj);
            runOnUiThread(()->{
                binding.queryView.setAdapter(myAdapter);

            });
            myAdapter.notifyItemInserted(items.size()-1);

            /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, stringURL, null, new Response.Listener<JSONObject>() {


                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray photos = response.getJSONArray("photos");
                                JSONObject position0 = photos.getJSONObject(0);
                                int id = position0.getInt("id");
                                String photographer = position0.getString("photographer");
                                int height = position0.getInt("height");
                                int width = position0.getInt("width");
                                JSONObject src = position0.getJSONObject("src");
                                String imageURL = src.getString("original");

                                ImageRequest imgReq = new ImageRequest(imageURL, new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap bitmap) {
                                        try {
                                            image = bitmap;
                                            image.compress(Bitmap.CompressFormat.PNG, 100, Pexels.this.openFileOutput(id + ".png", Activity.MODE_PRIVATE));

                                        } catch (IOException e) {
                                            e.printStackTrace();

                                        }

                                    }
                                }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error ) -> {

                                });

                                SearchedItem obj = new SearchedItem(id,photographer,height,width,imageURL);
                                items.add(obj);
                                runOnUiThread(()->{
                                    binding.queryView.setAdapter(myAdapter);

                                });
                                myAdapter.notifyItemInserted(items.size()-1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  header = new HashMap<String, String>();
                    header.put("Authorization", "563492ad6f917000010000016dd37f89df024e15a714d0235c8c6db9");

                    return header;
                }
            };*/

        });

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

                SearchedItem obj = items.get(0);
                holder.photographer.setText(obj.getPhotographer());
            }


            @Override
            public int getItemCount() {
                return (items == null) ? 0 : items.size();
            }
        });

        binding.queryView.setLayoutManager(new LinearLayoutManager(this));



    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView photographer;

        public MyRowHolder(@NonNull View itemView){
            super(itemView);

            photographer = itemView.findViewById(R.id.photographerText);
        }


    }
}