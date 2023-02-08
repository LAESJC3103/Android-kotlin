package pacificsoft.pscomandera.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Adapter.AdapterPlatillo;
import pacificsoft.pscomandera.KSOAP2.WebService;
import pacificsoft.pscomandera.Modelo.Comanda;
import pacificsoft.pscomandera.Modelo.ComandaArticulo;
import pacificsoft.pscomandera.Modelo.Configuracion;
import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Modelo.Grupo;
import pacificsoft.pscomandera.Modelo.Mesa;
import pacificsoft.pscomandera.Modelo.Platillo;
import pacificsoft.pscomandera.R;
import pacificsoft.pscomandera.Util.CargarConfiguracion;

import static android.content.Context.MODE_PRIVATE;

public class FragmentGrupo extends Fragment implements AdapterPlatillo.ItemPlatilloListener{

    RecyclerView recyclerViewPlatillos;
    Grupo grupo ;

    ItemFragmentListener mlistener;



    public interface ItemFragmentListener{
        void OnClickItemFragmentGrupo(Platillo platilloSeleccionado);
    }

    public FragmentGrupo() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ItemFragmentListener){
            mlistener = (ItemFragmentListener)context;
        }else {
            throw new ClassCastException(context.toString()
                    + "must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_grupo, container, false);

        String strGrupo = getArguments().getString("grupo");
        Type typeGrupo = new TypeToken<Grupo>(){}.getType();

        grupo = new Gson().fromJson(strGrupo,typeGrupo);

        recyclerViewPlatillos = vista.findViewById(R.id.recyclerViewPlatillos);
        GridLayoutManager GLayoutManager = new GridLayoutManager(getContext(),getResources().getInteger(R.integer.columnsGridViewPlatillos));
        recyclerViewPlatillos.setLayoutManager(GLayoutManager);
        recyclerViewPlatillos.setHasFixedSize(true);

        AdapterPlatillo adapterPlatillo = new AdapterPlatillo(grupo.getPlatillos(),getContext(),this);
        recyclerViewPlatillos.setAdapter(adapterPlatillo);

        return vista;
    }


    @Override
    public void onItemPlatilloClick(Platillo platillo) {

        mlistener.OnClickItemFragmentGrupo(platillo);

    }


}
