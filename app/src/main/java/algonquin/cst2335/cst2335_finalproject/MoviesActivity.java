package algonquin.cst2335.cst2335_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.cst2335_finalproject.databinding.ActivityMovieBinding;
import algonquin.cst2335.cst2335_finalproject.databinding.ActivityMovieInformationBinding;

public class MoviesActivity extends AppCompatActivity {
    ActivityMovieBinding binding;
    ActivityMovieInformationBinding binding2;
    protected RequestQueue queue = null;
    Bitmap image;
    ArrayList<MovieInfo> details;
    private RecyclerView.Adapter myAdapter;
    MovieViewModel movieModel;
    MovieDao mDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movieModel = new ViewModelProvider(this).get(MovieViewModel.class);
        details = movieModel.md.getValue();

        MovieDatabase md = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, "database-name").build();
        mDao = md.mDao();

        if(details == null){
            movieModel.md.postValue(details = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();

            thread.execute(() ->
            {
                details.addAll(mDao.getAllMovies());
                runOnUiThread( () ->  binding.RecyclerView.setAdapter( myAdapter ));
            });
        }

        queue = Volley.newRequestQueue(this);
        binding.saveBtn.setOnClickListener(click ->{
            Toast.makeText(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();
        });

        binding.submitBtn.setOnClickListener(click ->{
            String movie = binding.movieTitle.getText().toString();
            String stringURL = "";
            try{
                stringURL = "http://www.omdbapi.com/?apikey=6c9862c2&t="+ URLEncoder.encode(movie,"UTF-8");
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) -> {
                        try{
                            String title = response.getString("Title");
                            String year = response.getString("Year");
                            String rated = response.getString("Rated");
                            String runtime = response.getString("Runtime");
                            String director = response.getString("Director");
                            String actors = response.getString("Actors");
                            String plot = response.getString("Plot");
                            String poster = response.getString("Poster");

                            String pathname = getFilesDir() + "/" + poster + ".png";
                            File file = new File(pathname);
                            if(file.exists()){
                                image = BitmapFactory.decodeFile(pathname);
                            }else{
                                String posterURL = poster;
                                ImageRequest imgReq = new ImageRequest(posterURL, new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap bitmap) {
                                        try{
                                            image = bitmap;
                                            image.compress(Bitmap.CompressFormat.PNG, 100, MoviesActivity.this.openFileOutput(poster + ".png", Activity.MODE_PRIVATE));
                                        }catch(IOException e){
                                            e.printStackTrace();
                                        }
                                    }
                                },1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {});
                                queue.add(imgReq);
                            }
                            runOnUiThread(() ->{

                                /*
                                MovieDetails md = new MovieDetails("Title",title);
                                details.add(md);
                                myAdapter.notifyItemInserted(details.size()-1);
                                binding2.TextView.setText("Title: " + title);
                                */
                                binding.poster.setImageBitmap(image);
                                binding.test.setText(plot);

                            });
                            Toast.makeText(getApplicationContext(),"This is the title value"+title,Toast.LENGTH_SHORT).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    },
                    (error) ->{});
                queue.add(request);
        });

        //binding.saveBtn.setOnClickListener(); TODO

        binding.RecyclerView.setAdapter( myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ActivityMovieInformationBinding binding = ActivityMovieInformationBinding.inflate(getLayoutInflater(),parent,false);
                return new MyRowHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.content.setText("");
                MovieInfo obj = details.get(position);
                holder.content.setText(obj.getTitle());
                //add others?
            }

            @Override
            public int getItemCount() {
                return (details == null) ? 0 : details.size();
            }
        });
        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    class MyRowHolder extends RecyclerView.ViewHolder{
        TextView content;
        public MyRowHolder(@NonNull View itemView){
            super(itemView);
            content = itemView.findViewById(R.id.TextView);
        }
    }
}