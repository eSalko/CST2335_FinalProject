package algonquin.cst2335.cst2335_finalproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PexelsViewModel extends ViewModel {
    public MutableLiveData<ArrayList<SearchedItem>> items = new MutableLiveData<>();
    public MutableLiveData<SearchedItem> selectedItem = new MutableLiveData<>();
}
