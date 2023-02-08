package pacificsoft.pscomandera.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.gson.Gson;

import java.util.List;

import pacificsoft.pscomandera.Fragment.FragmentModificador;
import pacificsoft.pscomandera.Modelo.Modificador;
import pacificsoft.pscomandera.R;


public class ViewPagerAdapterModificadores extends FragmentStatePagerAdapter {


    int numOfTabs;
    Fragment fragment;
    List<Modificador> lstModificadores;
    Gson gson;
    private Context context;
    private int currentIndexFragment;

//    FragmenChildListener mListener;

    public boolean platilloSeleccionado;
//
//    public interface FragmenChildListener{
//        void OnClickItemFragmentChildListener(boolean platilloSeleccionado);
//    }


    public ViewPagerAdapterModificadores(FragmentManager fm, int numOfTabs, List<Modificador> lstModificadores, Context context,int currentIndexFragment) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.lstModificadores = lstModificadores;
        gson = new Gson();
        this.context = context;
        this.currentIndexFragment = currentIndexFragment;
    }


    @Override
    public Fragment getItem(int position) {

        if (!platilloSeleccionado) {
            for (int i = 0; i < numOfTabs; i++) {
                if (position == i) {

                    String mnodificadorStringJson = gson.toJson(lstModificadores.get(position));

                    Bundle bundle = new Bundle();
                    bundle.putString(context.getString(R.string.dato_extra_modificador), mnodificadorStringJson);
                    bundle.putInt("index",position);

                    fragment = new FragmentModificador();
                    fragment.setArguments(bundle);

                    break;
                }
            }
        } else {
            fragment = null;
        }

        return fragment;
    }


    @Override
    public int getCount() {
        return numOfTabs;
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return lstModificadores.get(position).getDescripcion();
//    }


}

