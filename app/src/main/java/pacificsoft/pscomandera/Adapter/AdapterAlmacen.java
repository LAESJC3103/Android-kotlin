package pacificsoft.pscomandera.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pacificsoft.pscomandera.Modelo.Almacen;
import pacificsoft.pscomandera.R;


/**
 * Created by desarrollo on 12/21/2017.
 */

public class AdapterAlmacen extends BaseAdapter {

    List<Almacen> listaDatos;
    Context context;

    public AdapterAlmacen(List<Almacen> listaDatos, Context context) {
        this.listaDatos = listaDatos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaDatos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaDatos.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista;
        LayoutInflater inflate = LayoutInflater.from( context );
        vista=inflate.inflate( R.layout.row_item_spinner,parent,false );
        TextView titulo, detalle;
        TextView imagenitem;

        titulo = (TextView) vista.findViewById( R.id.txtTitulo );
        detalle = (TextView) vista.findViewById( R.id.txtDescripcion );
        imagenitem = (TextView) vista.findViewById( R.id.imgitem );

        titulo.setText( listaDatos.get( position ).getId() );
        detalle.setText( listaDatos.get( position ).getDescripcion() );
        imagenitem.setText( context.getString(R.string.icon_print));

        /*Asignacion de los valores*/

//        if(position==0){
//
//            imagenitem.setVisibility(View.GONE);
//            detalle.setTextAppearance(context,R.style.hintItemStyleSpinner);
//            detalle.setText( listaDatos.get( position ).getDescripcion() );
//        }else{
//            titulo.setText( listaDatos.get( position ).getId() );
//            detalle.setText( listaDatos.get( position ).getDescripcion() );
//            imagenitem.setText( context.getString(R.string.icon_print));
//        }



        return vista;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View vista;
        LayoutInflater inflate = LayoutInflater.from( context );
        vista=inflate.inflate( R.layout.row_item_spinner_dropdown,parent,false );
        TextView titulo, detalle;
        TextView imagenitem;
        ConstraintLayout constraintContentRowItem;
        View dividerRowItem,dividerRowItemTitulo;

        titulo = (TextView) vista.findViewById( R.id.txtTitulo );
        detalle = (TextView) vista.findViewById( R.id.txtDescripcion );
        imagenitem = (TextView) vista.findViewById( R.id.imgitem );
        constraintContentRowItem = vista.findViewById(R.id.constraintContentRowItem);
        dividerRowItem = vista.findViewById(R.id.dividerRowItem);
        dividerRowItemTitulo = vista.findViewById(R.id.dividerRowItemTitulo);


        /*Asignacion de los valores*/
        if(position==0){
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintContentRowItem);
            constraintSet.connect(R.id.imgitem,ConstraintSet.RIGHT,R.id.txtDescripcion,ConstraintSet.LEFT,0);
            constraintContentRowItem.setConstraintSet(constraintSet);
            imagenitem.setText( context.getString(R.string.icon_print));
            detalle.setTextAppearance(context,R.style.styleLayoutItemSpinnerTituloDropdown);
            detalle.setText( listaDatos.get( position ).getDescripcion() );
            detalle.setPadding(0,context.getResources().getDimensionPixelSize(R.dimen.paddig_titulo_row_item_spinner),0,context.getResources().getDimensionPixelSize(R.dimen.paddig_titulo_row_item_spinner));
            if(listaDatos.size()>1){
                dividerRowItemTitulo.setVisibility(View.VISIBLE);
            }else{
                dividerRowItemTitulo.setVisibility(View.GONE);
            }

        }else{
            imagenitem.setVisibility(View.GONE);
            dividerRowItemTitulo.setVisibility(View.GONE);
            dividerRowItem.setVisibility(View.VISIBLE);
            titulo.setText( listaDatos.get( position ).getId() );
//            detalle.setTextAppearance(context,R.style.styleLayoutItemSpinnerDropdown);
            detalle.setText( listaDatos.get( position ).getDescripcion() );
        }
        return vista;
    }
}
