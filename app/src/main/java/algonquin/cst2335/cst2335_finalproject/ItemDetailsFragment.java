package algonquin.cst2335.cst2335_finalproject;



import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.cst2335_finalproject.databinding.DetailsLayoutBinding;

public class ItemDetailsFragment extends Fragment {
    /**
     * SearchedItem variable to define the current item that is selected from user
     * */
    SearchedItem selected;
    /**
     * DAO variable to access and insert into database
     * */
    SearchedItemDAO iDAO;

    /**
     * @param DAO initializes iDAO from DAO defined in Pexels activity
     * @param i the selected item from user
     * */
    public ItemDetailsFragment(SearchedItem i, SearchedItemDAO DAO){
        selected = i;
        iDAO = DAO;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);
        binding.fragImg.setImageBitmap(selected.getImagePic());
        binding.sizeText.setText("Width: " + selected.getWidth() + "px Height: " + selected.getHeight() + "px");
        binding.urlText.setText(selected.getUrl());

        binding.favBtn.setOnClickListener(clk -> {
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                iDAO.insertItem(selected);

            });
            Toast.makeText(getContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();

        });
        return binding.getRoot();
    }
}
