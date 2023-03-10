package fr.tom.midyie.ItemCreate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

import fr.tom.midyie.APIRequest;
import fr.tom.midyie.ItemsList.Item;
import fr.tom.midyie.MainActivity;
import fr.tom.midyie.R;

public class ItemCreateActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;

    ImageView imageView;

    TextView image;

    String convertedImageBase64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);

        Button returnButton = findViewById(R.id.add_item_return_button);
        Button add_item = findViewById(R.id.add_item);
        TextView name = findViewById(R.id.add_item_name);
        TextView minecraft_id = findViewById(R.id.add_item_minecraft_id);
        image = findViewById(R.id.image_path);
        Button selectImage = findViewById(R.id.image_selection);

        imageView = new ImageView(this);

        selectImage.setOnClickListener(view -> {

            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        });

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
            item.setImage(convertedImageBase64 == null ? "": convertedImageBase64);

            APIRequest.addItem(this, item, response -> {

                Toast.makeText(this, response ? "L'item a été ajouté avec succès :)" : "L'item n'a pas pu être ajouté :(", Toast.LENGTH_SHORT).show();
            });
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageView.setImageURI(selectedImageUri);
                    image.setText(getFilename(selectedImageUri, this));

                    convertedImageBase64 = base64Encode(imageView);
                }
            }
        }
    }

    private String base64Encode(ImageView imageView) {
        if (imageView.getDrawable() != null) {
            Bitmap avatarBitmap = ((BitmapDrawable) imageView.getDrawable())
                    .getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            avatarBitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
            byte[] avatarByteArray = baos.toByteArray();
            return Base64.encodeToString(avatarByteArray, Base64.DEFAULT);
        } else {
            return null;
        }
    }

    @SuppressLint("Range")
    private String getFilename(Uri uri, Context context) {
        String res = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor =  context.getContentResolver().query(uri, null,  null,  null, null)) {
                if (cursor  != null && cursor.moveToFirst()) {
                    res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
            if  (res ==  null) {
                res  = uri.getPath();
                int cutt  = res.lastIndexOf('/');
                if  (cutt  !=  -1)  {
                    res  = res.substring(cutt+1);
                }
            }
        }
        return res;
    }
}