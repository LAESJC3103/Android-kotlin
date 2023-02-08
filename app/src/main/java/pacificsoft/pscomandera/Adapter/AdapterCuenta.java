package pacificsoft.pscomandera.Adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Modelo.Cuenta;
import pacificsoft.pscomandera.Modelo.Modificador;
import pacificsoft.pscomandera.R;

public class AdapterCuenta extends RecyclerView.Adapter<AdapterCuenta.CuentaViewHolder>{
    List<Cuenta> lstDatos;
    Context context;
    private SparseBooleanArray selected_items;

    public AdapterCuenta(List<Cuenta> lstCuentas, Context context ) {

        this.lstDatos = lstCuentas;
        this.context = context;

        this.selected_items = new SparseBooleanArray();

        this.selected_items.put(0, true);
    }

    class CuentaViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewDescripcion;
        public ImageView imageViewRadioDefault;
        public LinearLayout contentItemCuenta;
        public ConstraintLayout constraintContentCheck;
        public Cuenta itemData;

        public CuentaViewHolder(View v){
            super(v);
            contentItemCuenta = v.findViewById(R.id.contentItemCuenta);
            textViewDescripcion = v.findViewById(R.id.textViewDescripcion);
            constraintContentCheck = v.findViewById(R.id.constraintContentCheck);
            imageViewRadioDefault = v.findViewById(R.id.imageViewRadioDefault);
        }

        public void setData(Cuenta cuenta) {
            this.itemData = cuenta;
            textViewDescripcion.setText(context.getString(R.string.nav_drawer_cuenta)+" "+itemData.getCuenta());
        }
    }

    @NonNull
    @Override
    public CuentaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cuenta,viewGroup,false);
        return new CuentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CuentaViewHolder cuentaViewHolder, final int i) {

        final Cuenta cuenta = lstDatos.get(i);


        cuentaViewHolder.setData(cuenta);

        if(selected_items.get(i, false)){
            cuentaViewHolder.imageViewRadioDefault.setImageResource(R.drawable.ic_round_check);
            cuentaViewHolder.contentItemCuenta.setActivated(true);
        }else {
            cuentaViewHolder.imageViewRadioDefault.setImageResource(R.drawable.ic_round_uncheck);
            cuentaViewHolder.contentItemCuenta.setActivated(false);
        }

        //Onclick al item
        cuentaViewHolder.contentItemCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!selected_items.get(i, false)) {

                    //Limpia el antigua seleccionado
                    notifyItemChanged(selected_items.keyAt(0));
                    selected_items.clear();

                    // agregar el nuevo seleccionado
                    selected_items.put(i, true);
                    notifyItemChanged(i);
                }

            }

        });
    }

    @Override
    public int getItemCount() {
        return lstDatos.size();
    }

    public Cuenta getSelectedItem(){
        int position = selected_items.keyAt(0);
        Cuenta cuenta = lstDatos.get(position);
        return cuenta;
    }


}
