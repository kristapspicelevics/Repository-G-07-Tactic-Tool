package lv.kristaps.tactictool;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateTacticActivity extends AppCompatActivity {
    String name = " ";
    String notName= "Please add tactic name";
    String userId;

    Button BSelectImage;
    ImageView IVPreviewImage;
    int SELECT_PICTURE = 200;
    EditText TextTacticName;
    FirebaseStorage storage;
    Button saveTacticButton;

    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tactic);

        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        storage = FirebaseStorage.getInstance();

        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });

        TextTacticName = (EditText) findViewById(R.id.TextTacticName);

        saveTacticButton = (Button) findViewById(R.id.saveTactic);

        saveTacticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name= TextTacticName.getText().toString();
                if (TextUtils.isEmpty(TextTacticName.getText().toString())){
                    showToast(notName);
                }else{
                    showToast(name);
                    uploadImage();
                }

            }


        });

    }
    private void uploadImage() {


        ProgressDialog diaglog = new ProgressDialog(this);
        diaglog.setMessage("Uploading...");
        diaglog.show();

        if (imageUri != null){
            String random= (UUID.randomUUID().toString());

            StorageReference reference = storage.getReference().child(random);

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseFirestore database= FirebaseFirestore.getInstance();
            userId=firebaseAuth.getCurrentUser().getUid();
            String fileName=random;
            name= TextTacticName.getText().toString();

            DocumentReference documentReference = database.collection("Images").document(userId);
            Map<String,Object> image = new HashMap<>();
            image.put("UserID", userId);
            image.put("ImageName",fileName);
            image.put("TacticName",name);
            documentReference.set(image).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    startActivity(new Intent(CreateTacticActivity.this, CreateTacticActivity.class));
                    Toast.makeText(CreateTacticActivity.this, "Image is added", Toast.LENGTH_SHORT).show();
                }
            });

            reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        diaglog.dismiss();
                        Toast.makeText(CreateTacticActivity.this, "Image Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                    }else{
                        diaglog.dismiss();
                        Toast.makeText(CreateTacticActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result !=null){
                        IVPreviewImage.setImageURI(result);
                        imageUri = result;
                    }

                }
            });

    private  void showToast(String text){
        Toast.makeText(CreateTacticActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.create:
                intent = new Intent(CreateTacticActivity.this, CreateTacticActivity.class); //pa prieksu kur esi un pēc tam kur gribi tikt
                startActivity(intent);
                finish();
                return true;
            case R.id.edit:
                intent = new Intent(CreateTacticActivity.this, EditTacticActivity.class); //pa prieksu kur esi un pēc tam kur gribi tikt
                startActivity(intent);
                finish();
                return true;
            case R.id.view:
                intent = new Intent(CreateTacticActivity.this, ViewTacticActivity.class); //pa prieksu kur esi un pēc tam kur gribi tikt
                startActivity(intent);
                finish();
                return true;
            case R.id.profile:
                intent = new Intent(CreateTacticActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(CreateTacticActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}