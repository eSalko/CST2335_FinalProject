package algonquin.cst2335.cst2335_finalproject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

import algonquin.cst2335.cst2335_finalproject.databinding.ActivityHighlightsBinding;
import algonquin.cst2335.cst2335_finalproject.databinding.GameTitleBinding;

public class HighlightsActivity extends AppCompatActivity {

//    ArrayList<match> matches= new ArrayList<>();
    RecyclerView recyclerView;
    List<match> matches;
    Adapter adapter;
    private static String stringURL = "https://www.scorebat.com/video-api/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlights);
        recyclerView = findViewById(R.id.recyclerView);
        ActivityHighlightsBinding binding = ActivityHighlightsBinding.inflate(getLayoutInflater());
        matches = new ArrayList<>();

        extractMatches();

        binding.getMatchBtn.setOnClickListener(clk ->{
            extractMatches();
//            adapter.notifyItemInserted(position);
        });



//        binding.recyclerView.setAdapter(new RecyclerView.Adapter<MyRowHolder>() {
//            @NonNull
//            @Override
//            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                GameTitleBinding binding = GameTitleBinding.inflate(getLayoutInflater());
//                return new MyRowHolder(binding.getRoot());
//            }
//
//            @Override
//            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
//
//            }
//
//            @Override
//            public int getItemCount() {
//                return 0;
//            }
//        });


        //getting JSON data
//        binding.getMatchBtn.setOnClickListener(click -> {
//            String url = "https://www.scorebat.com/video-api/v1/";
//            RequestQueue queue = null;
//
//            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                    (response) ->{
//                try {
////                    JSONObject jsonObject = new JSONObject(response);
////                    JSONArray matchArray = response.getJSONArray();
//                    JSONObject compObject = response.getJSONObject("competition");
//
//                } catch (JSONException e){
//                    e.printStackTrace();
//                }
//                    },
//                    (error) -> {
//
//                    });
//            queue = Volley.newRequestQueue(this);
//            queue.add(request);
//        });


    }//end of onCreate

    private void extractMatches() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, stringURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int r = 0; r < response.length(); r++) {
                    try {
                        JSONObject matchObject = response.getJSONObject(r);

                        match match = new match();
                        match.setMatchTitle(matchObject.getString("title"));
                        match.setMatchDate(matchObject.getString("date"));
                        match.setUrl(matchObject.getString("url"));

//                        JSONObject compObject = response.getJSONObject("competition");
//                        String comp = compObject.getString("name");






                        matches.add(match);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                Adapter adapter = new Adapter(getApplicationContext(), matches);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(request);

    }

//    class MyRowHolder extends RecyclerView.ViewHolder {
//        TextView match;
//        TextView date;
//        TextView comp;
//        TextView url;
//
//        public MyRowHolder(@NonNull View itemView) {
//            super(itemView);
//            match = itemView.findViewById(R.id.matchTitle);
//            date = itemView.findViewById(R.id.matchDate);
//
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.aboutIcon:
                Toast.makeText(getApplicationContext(),"Welcome to Soccer Highlights. App created by Eric Salkovic", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}