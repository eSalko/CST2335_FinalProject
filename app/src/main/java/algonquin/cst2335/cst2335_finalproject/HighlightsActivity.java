package algonquin.cst2335.cst2335_finalproject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.cst2335_finalproject.databinding.ActivityHighlightsBinding;

public class HighlightsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Match> Matches;
    MatchViewModel matchModel;
    matchDAO mDAO;
    private static String stringURL = "https://www.scorebat.com/video-api/v1/";
    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlights);
        recyclerView = findViewById(R.id.recyclerView);
        ActivityHighlightsBinding binding = ActivityHighlightsBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolBar);
        Matches = new ArrayList<Match>();

        //db
        MatchDB db = Room.databaseBuilder(getApplicationContext(), MatchDB.class, "Match Database").build();
        mDAO = db.mDAO();

        matchModel = new ViewModelProvider(this).get(MatchViewModel.class);
        Matches = matchModel.matches.getValue();

        if(Matches == null) {
            matchModel.matches.setValue(Matches = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();

            thread.execute(() ->{
                Matches.addAll(mDAO.getAllMatches());
                binding.recyclerView.setAdapter(myAdapter);
            });
        }



        binding.urlBtn.setOnClickListener(clk -> {
            try {
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringURL));
                startActivity(urlIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Cannot launch url/url not found", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        });


        extractMatches();

        binding.getMatchBtn.setOnClickListener(clk ->{
            extractMatches();
//            adapter.notifyItemInserted(position);
        });

        matchModel.selectedMatch.observe(this, (newItemValue) -> {
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            matchDetailsFragment matchFragment = new matchDetailsFragment(newItemValue);
            tx.replace(R.id.fragment, matchFragment);
            tx.commit();
        });
    }//end of onCreate

    private void extractMatches() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, stringURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int r = 0; r < response.length(); r++) {
                    try {
                        JSONObject matchObject = response.getJSONObject(r);

                        Match match = new Match();
                        match.setMatchTitle(matchObject.getString("title"));
                        match.setMatchDate(matchObject.getString("date"));
                        match.setComp(matchObject.getJSONObject("competition").getString("name"));
                        match.setUrl(matchObject.getString("url"));

                        Matches.add(match);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                Adapter adapter = new Adapter(getApplicationContext(), Matches, matchModel);
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