package lv.kristaps.tactictool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.create:
                intent = new Intent(MainActivity.this, CreateTacticActivity.class); //pa prieksu kur esi un pēc tam kur gribi tikt
                startActivity(intent);
                finish();
                return true;
            case R.id.edit:
                intent = new Intent(MainActivity.this, EditTacticActivity.class); //pa prieksu kur esi un pēc tam kur gribi tikt
                startActivity(intent);
                finish();
                return true;
            case R.id.view:
                intent = new Intent(MainActivity.this, ViewTacticActivity.class); //pa prieksu kur esi un pēc tam kur gribi tikt
                startActivity(intent);
                finish();
                return true;
            case R.id.profile:
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.logout:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}