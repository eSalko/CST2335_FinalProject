package algonquin.cst2335.cst2335_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import algonquin.cst2335.cst2335_finalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        Intent highlights = new Intent( this, HighlightsActivity.class);
        Intent pexels = new Intent( this, Pexels.class);
        Intent movie = new Intent( this, MoviesActivity.class);

        binding.soccerHighlightsBtn.setOnClickListener( clk-> {
            startActivity(highlights);
        } );

        binding.pexelsBtn.setOnClickListener( clk-> {
            startActivity(pexels);
        } );

        binding.movieInformationBtn.setOnClickListener( clk-> {
            startActivity(movie);
        } );
    }
}