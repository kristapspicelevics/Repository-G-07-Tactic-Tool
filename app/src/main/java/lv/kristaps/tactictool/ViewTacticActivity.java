package lv.kristaps.tactictool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ViewTacticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tactic);
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

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}