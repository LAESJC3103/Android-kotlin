package pacificsoft.pscomandera.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Adapter.AdapterModificador;
import pacificsoft.pscomandera.Modelo.ComandaModificador;
import pacificsoft.pscomandera.Modelo.Ingrediente;
import pacificsoft.pscomandera.Modelo.Intercambio;
import pacificsoft.pscomandera.Modelo.Modificador;
import pacificsoft.pscomandera.Modelo.ModificadorSeleccionado;
import pacificsoft.pscomandera.R;

import static android.content.Context.MODE_PRIVATE;

public class FragmentModificador extends Fragment implements AdapterModificador.ItemModificadorListener {


    ModificadorFragmentListener mListener;
    private List<Modificador> cantidad_items_saved_instance;
    private Modificador modificador;
    private RecyclerView recyclerViewModificadores;
    private AdapterModificador adapterModificador;
    private int currentIndexFragment = 0;
    private int index = 0;
    private SharedPreferences mPreferences;

    public interface ModificadorFragmentListener{
        void onClickItemModificador(double total,int cantidadTotal);
        void onClickButtonSiguienteResponse(ModificadorSeleccionado modificadores);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_modificador, container, false);

        String strModificador = getArguments().getString(getActivity().getString(R.string.dato_extra_modificador));
        Type typeModificador = new TypeToken<Modificador>(){}.getType();

        modificador = new Gson().fromJson(strModificador,typeModificador);
        index = getArguments().getInt("index");


        recyclerViewModificadores = vista.findViewById(R.id.recyclerViewModificadores);
        GridLayoutManager GLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerViewModificadores.setLayoutManager(GLayoutManager);
        recyclerViewModificadores.setHasFixedSize(true);

        List<Modificador> modificadoresItem = getModificadoresItem();

        modificador.setIngredientes(new ArrayList<Ingrediente>());
        modificador.setIntercambios(new ArrayList<Intercambio>());

        mPreferences = getContext().getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE);


        currentIndexFragment = mPreferences.getInt("currentFragmentIndex",0);

        int totalCantidadModificador = 0;

        if(savedInstanceState!=null){
            if(index==currentIndexFragment){
                cantidad_items_saved_instance = savedInstanceState.getParcelableArrayList("lstSavedInstanceCurrentModificadores");
                totalCantidadModificador = savedInstanceState.getInt("totalCantidadModificador");
            }else{
                cantidad_items_saved_instance = new ArrayList<>();
            }
        }

        adapterModificador = new AdapterModificador(modificador,modificadoresItem,cantidad_items_saved_instance,getContext(),this);
        adapterModificador.setCantidadSelectedModificador(totalCantidadModificador);
        recyclerViewModificadores.setAdapter(adapterModificador);

        return vista;
    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        currentIndexFragment = mPreferences.getInt("currentFragmentIndex",0);

        if(index==currentIndexFragment){
            ArrayList<Modificador> modificadores = new ArrayList<>(adapterModificador.getSelectedItems());
            outState.putParcelableArrayList("lstSavedInstanceCurrentModificadores",modificadores);
            outState.putInt("totalCantidadModificador",adapterModificador.getCantidadSelectedModificador());
        }

        //Save the fragment's state here
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ModificadorFragmentListener){
            mListener = (ModificadorFragmentListener)context;
        }else {
            throw new ClassCastException(context.toString()
                    + "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onItemModificadorClick() {
        double total = adapterModificador.getTotal();
        int totalCantidad = adapterModificador.totalCantidad;
        mListener.onClickItemModificador(total,totalCantidad);
    }


    private List<Modificador> getModificadoresItem(){
        List<Modificador> lst = new ArrayList<>();
        switch (modificador.getTipo()){
            case "1":
                if(modificador.getCantidad()<=0){
                    modificador.setCantidad(1);
                }

                lst.add(modificador);
                break;
            case "2":


                for (Intercambio obj : modificador.getIntercambios()){
                    Modificador modificador1Temp = new Modificador();
                    modificador1Temp.setId(obj.getId());
                    modificador1Temp.setDescripcion(obj.getDescripcion());
                    modificador1Temp.setCosto(obj.getCosto());
                    modificador1Temp.setCantidad(1);

                    lst.add(modificador1Temp);
                }
                if(modificador.getCantidad()<=0){
                    modificador.setCantidad(1);
                }
                modificador.setIntercambios(new ArrayList<Intercambio>());
                lst.add(0,modificador);
                break;
            case "3":

                for (Ingrediente obj : modificador.getIngredientes()){
                    Modificador modificador1Temp = new Modificador();
                    modificador1Temp.setId(obj.getId());
                    modificador1Temp.setDescripcion(obj.getDescripcion());
                    lst.add(modificador1Temp);
                }

                modificador.setIngredientes(new ArrayList<Ingrediente>());
//                lst.add(0,modificador);

                break;
        }

        return lst;
    }



    public void onClickButtonSiguiente(){
        List<Modificador> modificadores = adapterModificador.getSelectedItems();

        ModificadorSeleccionado modificadorSeleccionado = new ModificadorSeleccionado();

        modificadorSeleccionado.setOrden(modificador.getOrden());
        modificadorSeleccionado.setTipo(modificador.getTipo());
        if(modificador.getTipo().equals("3")){
            modificadorSeleccionado.setCodlista(modificador.getListaM());
        }

        modificadorSeleccionado.setModificadoresSeleccionados(modificadores);


//        List<ComandaModificador> comandaModificadorList = new ArrayList<>();
//
//        for(Modificador modificador : modificadores){
//            ComandaModificador comandaModificador = new ComandaModificador();
//            comandaModificador.setId(modificador.getId());
//            comandaModificador.setCosto(modificador.getCosto());
//            comandaModificador.setCantidad(modificador.getCantidad());
//            comandaModificador.setDescripcion(modificador.getDescripcion());
//
//            comandaModificadorList.add(comandaModificador);
//
//        }

        mListener.onClickButtonSiguienteResponse(modificadorSeleccionado);

    }


}
