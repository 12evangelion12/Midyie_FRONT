package fr.tom.midyie.ItemsList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.tom.midyie.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> items;

    public ItemAdapter(List<Item>  items)  {
        this.items =  items;
    }

    public void setFilteredList(List<Item>  filteredList) {
        this.items = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,  parent,  false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.display(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView minecraft_id;
        private final ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            name =  itemView.findViewById(R.id.item_name);
            minecraft_id = itemView.findViewById(R.id.item_minecraft_id);
            imageView = itemView.findViewById(R.id.item_image);
        }

        void display(Item item) {
            name.setText(item.getName());
            minecraft_id.setText(item.getMinecraft_id());

            byte[] decodedString = Base64.decode(item.getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
        }
    }
}
