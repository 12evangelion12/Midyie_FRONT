package fr.tom.midyie.NameFinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import fr.tom.midyie.APIRequest;
import fr.tom.midyie.R;

public class NameFinderFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name_finder, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        SearchView searchView  = view.findViewById(R.id.minecraft_available_search);
        ImageView imageView = view.findViewById(R.id.minecraft_available_image);
        TextView textView  = view.findViewById(R.id.minecraft_available_text);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if  (s.length() >  25) {
                    imageView.setImageResource(R.mipmap.ic_pseudo_unavailable);
                    textView.setText("Un pseudonyme ne peux pas dépasser les 25 caractères !");
                    return true;
                }

                APIRequest.isMinecraftNameAvailable(view.getContext(), s, response -> {

                    imageView.setImageResource(response ? R.mipmap.ic_pseudo_available : R.mipmap.ic_pseudo_unavailable);
                    textView.setText(response ? "Le pseudo \""+s+"\"\nest disponible :)" : "Le pseudo \""+s+"\"\nest indisponible :(");
                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }
}