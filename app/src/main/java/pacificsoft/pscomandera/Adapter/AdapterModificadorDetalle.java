package pacificsoft.pscomandera.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pacificsoft.pscomandera.Modelo.Modificador;
import pacificsoft.pscomandera.R;

public class AdapterModificadorDetalle extends RecyclerView.Adapter<AdapterModificadorDetalle.ModificadorDetalleViewHolder>{
    private Context context;
    private List<Modificador> lstData;

//    private SparseBooleanArray selected_items = new SparseBooleanArray();;
//    private  int current_selected_idx = -1;
//
//    public void setListenerNotas(AdapterNotaListener adapterNotaListener) {
//        this.mListener = adapterNotaListener;
//    }

    public AdapterModificadorDetalle(Context context, List<Modificador> lstData) {
        this.context = context;
        this.lstData = lstData;
    }

    class ModificadorDetalleViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewCantidad,textViewDescripcion;
        public ModificadorDetalleViewHolder(View v) {
            super(v);
            textViewCantidad = v.findViewById(R.id.textViewCantidad);
            textViewDescripcion = v.findViewById(R.id.textViewDescripcion);
        }

    }

    @NonNull
    @Override
    public ModificadorDetalleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_modificador_detalle,viewGroup,false);
        return new ModificadorDetalleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModificadorDetalleViewHolder modificadorDetalleViewHolder, final int position) {
        final Modificador modificador = lstData.get(position);
        modificadorDetalleViewHolder.textViewCantidad.setText(String.valueOf(modificador.getCantidad()));
        modificadorDetalleViewHolder.textViewDescripcion.setText(modificador.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public Modificador getItem(int position) {
        return lstData.get(position);
    }

}
