package algonquin.cst2335.cst2335_finalproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MovieViewModel extends ViewModel {
    public MutableLiveData<ArrayList<MovieDetails>> md = new MutableLiveData<>();
}
