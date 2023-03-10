package fr.tom.midyie.ItemsList;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;
import java.util.stream.Collectors;

import fr.tom.midyie.APIRequest;
import fr.tom.midyie.ItemCreate.ItemCreateActivity;
import fr.tom.midyie.MainActivity;
import fr.tom.midyie.R;
import fr.tom.midyie.SessionProperty;

public class ItemListFragment extends Fragment {

    List<Item> items;
    ItemAdapter itemAdapter;

    public ItemListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        SearchView searchView  = view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });

        Button addItem = view.findViewById(R.id.add_item_on_list);
        addItem.setOnClickListener(view1 -> {
            Intent intent =  new Intent(view.getContext(), ItemCreateActivity.class);
            startActivity(intent);
        });

        if (!SessionProperty.getInstance().getPrivilege().equalsIgnoreCase("administrator")) {
            addItem.setVisibility(View.INVISIBLE);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycledView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));

        APIRequest.getAllItems(this.getContext(), response -> {
            this.items = response;
            itemAdapter = new ItemAdapter(items);
            recyclerView.setAdapter(itemAdapter);
        });
    }

    private void filterList(String query) {
        List<Item> filteredList = items.stream().filter(item -> item.getName().toLowerCase().contains(query)).collect(Collectors.toList());

        if (filteredList.isEmpty()) {
            Toast.makeText(this.getContext(), "Aucun item trouvé", Toast.LENGTH_SHORT).show();
        }  else {
            itemAdapter.setFilteredList(filteredList);
        }
    }
}