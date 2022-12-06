package algonquin.cst2335.cst2335_finalproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MatchViewModel extends ViewModel {
    public MutableLiveData<ArrayList<match>> matches = new MutableLiveData<>();
    public MutableLiveData<match> match = new MutableLiveData<>();

}
