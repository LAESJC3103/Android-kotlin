package pacificsoft.pscomandera.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Modelo.Mesa;
import pacificsoft.pscomandera.R;

public class AdapterMesa extends RecyclerView.Adapter<AdapterMesa.MesaViewHolder> implements Filterable {
    List<Mesa> lstMesas;
    List<Mesa> lstMesasFull;
    Context context;
    protected ItemMesaListener mListener;
    public boolean mesaSeleccionada = false;

    public AdapterMesa(List<Mesa> lstMesas, Context context, ItemMesaListener mListener) {
        this.lstMesas = lstMesas;
        lstMesasFull = new ArrayList<>(lstMesas);
        this.context = context;
        this.mListener = mListener;
    }

    class MesaViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewDescripcionMesa;
        public Mesa itemData;
        public ConstraintLayout contentItemMesa;
        public ImageView imageViewEstatusMesa;
        public MesaViewHolder(View v){
            super(v);
            textViewDescripcionMesa = v.findViewById(R.id.textViewDescripcionMesa);
            imageViewEstatusMesa = v.findViewById(R.id.imageViewEstatusMesa);
            contentItemMesa = v.findViewById(R.id.contentItemMesa);
        }

        public void setData(Mesa itemData){
            this.itemData = itemData;
            textViewDescripcionMesa.setText(itemData.getDescripcion());
            imageViewEstatusMesa.setImageResource( itemData.getEstado() == 0?R.drawable.mesa_desocupada:R.drawable.mesa_ocupada);
        }

    }

    @NonNull
    @Override
    public MesaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mesa,viewGroup,false);
        return new MesaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MesaViewHolder mesaViewHolder, int i) {

        final Mesa mesa = lstMesas.get(i);
        mesaViewHolder.setData(mesa);


        mesaViewHolder.contentItemMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null || mesaSeleccionada) return;

                mesaSeleccionada = true;
                mListener.onItemMesaClick(mesa);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstMesas.size();
    }

    public interface ItemMesaListener {
        void onItemMesaClick(Mesa mesa);
    }

    @Override
    public Filter getFilter() {
        return filtro;
    }

    private Filter filtro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Mesa> filteredList = new ArrayList<>();
            if(constraint==null || constraint.length() == 0){
                filteredList.addAll(lstMesasFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Mesa item : lstMesasFull){
                    if(item.getDescripcion().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            lstMesas.clear();
            lstMesas.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
