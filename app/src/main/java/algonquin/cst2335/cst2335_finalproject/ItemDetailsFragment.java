package algonquin.cst2335.cst2335_finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.cst2335_finalproject.databinding.DetailsLayoutBinding;

public class ItemDetailsFragment extends Fragment {
    SearchedItem selected;

    public ItemDetailsFragment(SearchedItem i){selected = i;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);
        binding.fragImg.setImageBitmap(selected.getImagePic());
        binding.sizeText.setText("Width: " + selected.getWidth() + "px Height :" + selected.getHeight() + "px");
        binding.urlText.setText(selected.getUrl());
        return binding.getRoot();
    }
}
