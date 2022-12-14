package algonquin.cst2335.cst2335_finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.cst2335_finalproject.databinding.DetailsHighlightsBinding;
import algonquin.cst2335.cst2335_finalproject.databinding.DetailsLayoutBinding;

public class matchDetailsFragment extends Fragment {
    Match selected;
    public matchDetailsFragment(Match i){selected = i;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        DetailsHighlightsBinding binding = DetailsHighlightsBinding.inflate(inflater);
        binding.compDetails.setText(selected.getComp());
        binding.urlDetails.setText(selected.getUrl());

        return binding.getRoot();
    }
}
