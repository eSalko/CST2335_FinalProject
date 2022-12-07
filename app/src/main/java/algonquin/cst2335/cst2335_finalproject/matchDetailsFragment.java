package algonquin.cst2335.cst2335_finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.cst2335_finalproject.databinding.DetailsLayoutBinding;

public class matchDetailsFragment extends Fragment {
    match selected;
    public matchDetailsFragment(match i){selected = i;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);
        binding.compDetails.setText(selected.getComp());
        binding.urlDetails.setText(selected.getUrl());

        return binding.getRoot();
    }
}
