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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText boxemail;
    EditText boxpassword;
    Button btnregister;
    Button btnlogin;
    Button btnforgotpass;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        viewUI();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    String email = boxemail.getText().toString();
                    String pass = boxpassword.getText().toString();
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, CreateTacticActivity.class));
                                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    public void viewUI() {
        boxemail = (EditText) findViewById(R.id.email);
        boxpassword = (EditText) findViewById(R.id.password);
        btnregister = (Button) findViewById(R.id.register);
        btnlogin = (Button) findViewById(R.id.login);
        btnforgotpass = (Button) findViewById(R.id.forgotpassword);
    }

    public boolean isValid() {
        boolean valid = false;
        String email = boxemail.getText().toString();
        String pass = boxpassword.getText().toString();

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
            valid = true;
            boxpassword.setError(null);
        }
        return valid;
    }

}