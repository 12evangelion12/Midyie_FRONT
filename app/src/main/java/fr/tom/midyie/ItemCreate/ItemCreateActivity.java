package fr.tom.midyie.ItemCreate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import fr.tom.midyie.APIRequest;
import fr.tom.midyie.ItemsList.Item;
import fr.tom.midyie.MainActivity;
import fr.tom.midyie.R;

public class ItemCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);

        Button returnButton = findViewById(R.id.add_item_return_button);
        Button add_item = findViewById(R.id.add_item);
        TextView name = findViewById(R.id.add_item_name);
        TextView minecraft_id = findViewById(R.id.add_item_minecraft_id);
        TextView image = findViewById(R.id.add_item_image);

        returnButton.setOnClickListener(view -> {
            Intent intent =  new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        add_item.setOnClickListener(view -> {

           if (name.getText().length() == 0 || minecraft_id.getText().length() == 0) {
               Toast.makeText(this, "Merci de spécifier au moins un nom ainsi qu'un id", Toast.LENGTH_SHORT).show();
               return;
           }

            Item item = new Item();
            item.setName(name.getText().toString());
            item.setMinecraft_id(minecraft_id.getText().toString());
            item.setImage(image.getText().toString());

            APIRequest.addItem(this, item, response -> {

                Toast.makeText(this, response ? "L'item a été ajouté avec succès :)" : "L'item n'a pas pu être ajouté :(", Toast.LENGTH_SHORT).show();
            });
        });
    }
}