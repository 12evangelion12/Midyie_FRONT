package fr.tom.midyie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Button logButton;
    private EditText identifiant;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        identifiant = findViewById(R.id.identifiant_field);
        password = findViewById(R.id.password_field);
        logButton = findViewById(R.id.connexion_button);

        logButton.setOnClickListener(view -> {
            validateConnexion(identifiant.getText().toString(), password.getText().toString());
        });
    }

    private void validateConnexion(String identifiant, String userPassword) {

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url = getResources().getString(R.string.api_url) + "/account";

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", identifiant);
            jsonObject.put("password", userPassword);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObject,
                    response -> {
                        String message = response.optString("message");
                        if (message.equalsIgnoreCase("login successful")) {

                            Intent intent =  new Intent(this, MainActivity.class);
                            SessionProperty.getInstance().setPrivilege(response.optString("account_type"));
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Connexion réalisée avec succès !", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(this, "L'identifiant ou le mot de passe est incorrect !", Toast.LENGTH_SHORT).show();
                    }
            );

            queue.add(jsonObjectRequest);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
