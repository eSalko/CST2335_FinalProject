package algonquin.cst2335.cst2335_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.cst2335_finalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //binding
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //toolbar
        setSupportActionBar(binding.toolBar);
        //button intent
        Intent nextPage = new Intent(MainActivity.this, HighlightsActivity.class);


        binding.button.setOnClickListener(click -> {
            startActivity(nextPage);
        });

    }//end of onCreate

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
    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "Application now visible");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.w(TAG, "Now responding to user input");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.w(TAG, "No longer responding to user input");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.w(TAG, "Application no longer visible");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.w(TAG, "Any memory used by the application is freed");
    }
}