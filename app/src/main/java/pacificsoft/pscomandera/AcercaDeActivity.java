package pacificsoft.pscomandera;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class AcercaDeActivity extends AppCompatActivity {


    Toolbar toolbar;
    TextView textViewTituloActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        toolbar = findViewById(R.id.toolbar);

        textViewTituloActividad = findViewById(R.id.textViewTituloActividad);
        textViewTituloActividad.setText(R.string.title_activity_acerca_de);

        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        configureToolbar();
    }

    @Override
    public void finish() {
        setResult(RESULT_OK,getIntent());
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void configureToolbar() {

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        actionbar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return true;
    }
}
