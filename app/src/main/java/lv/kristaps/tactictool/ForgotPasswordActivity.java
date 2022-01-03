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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText boxemail;
    Button btnchangepass;
    Button btnback;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        viewUI();
        auth = FirebaseAuth.getInstance();

        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    String email = boxemail.getText().toString();
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "Password Reset Email Sent!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
            }
        });
    }
    public void viewUI() {
        boxemail = (EditText) findViewById(R.id.email);
        btnchangepass = (Button) findViewById(R.id.changepass);
        btnback = (Button) findViewById(R.id.back);
    }

    public boolean isValid() {
        boolean valid = false;
        String email = boxemail.getText().toString();

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

        return valid;
    }
}