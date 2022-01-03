package lv.kristaps.tactictool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText boxemail;
    EditText boxname;
    EditText boxpassword;
    Button btnregister;
    Button btnlogin;
    FirebaseFirestore database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        viewUI();
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    String email = boxemail.getText().toString();
                    String name = boxname.getText().toString();
                    String pass = boxpassword.getText().toString();

                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                                DocumentReference documentReference = database.collection("Users").document(userID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("name",name);
                                user.put("pass",pass);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        Toast.makeText(RegisterActivity.this, "Account is created!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    public void viewUI() {
        boxemail = findViewById(R.id.email);
        boxname = findViewById(R.id.name);
        boxpassword = findViewById(R.id.password);
        btnlogin = findViewById(R.id.login);
        btnregister = findViewById(R.id.register);
    }

    public boolean isValid() {
        boolean valid = false;
        String email = boxemail.getText().toString();
        String name = boxname.getText().toString();
        String pass = boxpassword.getText().toString();

        if (name.isEmpty()) {
            valid = false;
            boxname.setError("Please enter valid first name!");
        } else {
            valid = true;
            boxname.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            valid = false;
            boxemail.setError("Please enter valid email!");
        } else {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                valid = false;
                boxemail.setError("Please enter valid email!");
            } else {
                valid = true;
                boxemail.setError(null);
            }
        }

        if (pass.isEmpty()) {
            valid = false;
            boxpassword.setError("Please enter valid password!");
        } else {
            if (pass.length() >= 6) {
                valid = true;
                boxpassword.setError(null);
            } else {
                valid = false;
                boxpassword.setError("Password is to short!");
            }
        }
        return valid;
    }
}