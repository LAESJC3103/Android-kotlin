package pacificsoft.pscomandera;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Adapter.AdapterAplicaciones;
import pacificsoft.pscomandera.Modelo.Aplicacion;


public class MasAplicacionesActivity extends AppCompatActivity {


    List<Aplicacion> aplicaciones = new ArrayList<>();

    RecyclerView recyclerViewAplicaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_aplicaciones);

        recyclerViewAplicaciones = findViewById(R.id.recyclerViewAplicaciones);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerViewAplicaciones.setLayoutManager(llm);
        recyclerViewAplicaciones.setHasFixedSize(true);

        aplicaciones.add(new Aplicacion("Pago en piso","http://",R.drawable.background_generico,"Esta aplicacion tiene la funcionalidad..."));
        aplicaciones.add(new Aplicacion("Pago en piso","http://",R.drawable.background_generico,"Esta aplicacion tiene la funcionalidad..."));
        aplicaciones.add(new Aplicacion("Pago en piso","http://",R.drawable.background_generico,"Esta aplicacion tiene la funcionalidad..."));
        aplicaciones.add(new Aplicacion("Pago en piso","http://",R.drawable.background_generico,"Esta aplicacion tiene la funcionalidad..."));
        aplicaciones.add(new Aplicacion("Pago en piso","http://",R.drawable.background_generico,"Esta aplicacion tiene la funcionalidad..."));
        InitializeAdapter();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

    }

    private void InitializeAdapter(){
        AdapterAplicaciones adapter = new AdapterAplicaciones(MasAplicacionesActivity.this,aplicaciones);
        recyclerViewAplicaciones.setAdapter(adapter);
    }

    @Override
    public void finish() {
        setResult(RESULT_OK,getIntent());
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
