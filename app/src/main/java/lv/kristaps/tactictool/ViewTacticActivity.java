package lv.kristaps.tactictool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ViewTacticActivity extends AppCompatActivity {
    private StorageReference mStorageReference;

    TextView tacticName;
    String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tactic);

        tacticName = findViewById(R.id.NameOfTactic);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore database= FirebaseFirestore.getInstance();
        String userId=firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = database.collection("Images").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                tacticName.setText("" + value.getString("ImageName"));
                imageName = tacticName.getText().toString();
                tacticName.setText("Name: " +value.getString("TacticName"));

                mStorageReference = FirebaseStorage.getInstance().getReference().child(imageName);

                try {
                    final File localFile = File.createTempFile(imageName, "*");
                    mStorageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(ViewTacticActivity.this, "Picture Dowloaded", Toast.LENGTH_SHORT).show();
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            ((ImageView)findViewById(R.id.IVPreviewImage)).setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ViewTacticActivity.this, "Picture Not Dowloaded", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
                intent = new Intent(ViewTacticActivity.this, CreateTacticActivity.class); //pa prieksu kur esi un pēc tam kur gribi tikt
                startActivity(intent);
                finish();
                return true;
            case R.id.edit:
                intent = new Intent(ViewTacticActivity.this, EditTacticActivity.class); //pa prieksu kur esi un pēc tam kur gribi tikt
                startActivity(intent);
                finish();
                return true;
            case R.id.view:
                intent = new Intent(ViewTacticActivity.this, ViewTacticActivity.class); //pa prieksu kur esi un pēc tam kur gribi tikt
                startActivity(intent);
                finish();
                return true;
            case R.id.profile:
                intent = new Intent(ViewTacticActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}