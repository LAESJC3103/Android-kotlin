package pacificsoft.pscomandera.Adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.gson.Gson;

import java.util.List;

import pacificsoft.pscomandera.Modelo.Grupo;
import pacificsoft.pscomandera.Fragment.FragmentGrupo;

public class ViewPagerAdapterGrupo extends FragmentStatePagerAdapter {


    int numOfTabs;
    Fragment fragment;
    List<Grupo> lstGrupos;
    Gson gson;

//    FragmenChildListener mListener;

    public boolean platilloSeleccionado;
//
//    public interface FragmenChildListener{
//        void OnClickItemFragmentChildListener(boolean platilloSeleccionado);
//    }


    public ViewPagerAdapterGrupo(FragmentManager fm, int numOfTabs,List<Grupo> lstGrupos) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.lstGrupos = lstGrupos;
        gson = new Gson();
    }




    @Override
    public Fragment getItem(int position) {

        if(!platilloSeleccionado){
            for(int i = 0;i <numOfTabs;i++){
                if(position== i){



                    String grupoStringJson = gson.toJson(lstGrupos.get(position));

                    Bundle bundle = new Bundle();
                    bundle.putString("grupo",grupoStringJson);

                    fragment = new FragmentGrupo();
                    fragment.setArguments(bundle);

                    break;
                }
            }
        }else{
            fragment = null;
        }



        return fragment;
    }




    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lstGrupos.get(position).getDescripcion();
    }

}
