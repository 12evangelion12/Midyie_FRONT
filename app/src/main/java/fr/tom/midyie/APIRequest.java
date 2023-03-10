package fr.tom.midyie;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.tom.midyie.ItemsList.Item;

public class APIRequest {

    public static void getAllItems(Context context, Response.Listener<List<Item>> listener) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://10.0.2.2:6666/items";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {

                    List<Item> items = new ArrayList<>();

                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);

                            System.out.println("jsonObject: "+jsonObject);

                            Item item = new Item();
                            item.setName(jsonObject.getString("name"));
                            item.setMinecraft_id(jsonObject.getString("minecraftId"));
                            item.setImage(jsonObject.getString("image"));
                            items.add(item);
                        }

                        listener.onResponse(items);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                }

        );

        queue.add(jsonArrayRequest);
    }


    public static void isMinecraftNameAvailable(Context context, String pseudonyme, Response.Listener<Boolean> listener) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://10.0.2.2:6666/pseudoAvailable";

        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("pseudo", pseudonyme);
        url = builder.build().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        listener.onResponse(Boolean.parseBoolean(response.getString("available")));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {

                }
        );

        queue.add(jsonObjectRequest);

    }
}
