package pacificsoft.pscomandera.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.codesgood.views.JustifiedTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Modelo.ComandaArticulo;
import pacificsoft.pscomandera.Modelo.ComandaModificador;
import pacificsoft.pscomandera.Modelo.Modificador;
import pacificsoft.pscomandera.Modelo.ModificadorSeleccionado;
import pacificsoft.pscomandera.R;

public class AdapterComanda extends RecyclerView.Adapter<AdapterComanda.ComandaViewHolder> implements Filterable {
    public List<ComandaArticulo> lstComandaArticulo;
    public List<ComandaArticulo> lstComandaArticuloFull;

    private SparseBooleanArray selected_items;
    private  int current_selected_idx = -1;

    Context context;
    protected AdapterComandaListener mListener;

    public void setOnClickListener(AdapterComandaListener adapterComandaListener) {
        this.mListener = adapterComandaListener;
    }

    public AdapterComanda(List<ComandaArticulo> lstComandaArticulo, Context context) {
        this.lstComandaArticulo = lstComandaArticulo;
        lstComandaArticuloFull = new ArrayList<>(lstComandaArticulo);
        this.context = context;
        selected_items = new SparseBooleanArray();
    }

    class ComandaViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewDescripcion,textViewPrecio;
        public TextView textViewNotas,textViewModificadoresNormales,textViewCantidadArticulo;
        public ComandaArticulo itemData;
        public ConstraintLayout contentHeaderItemComanda;
        public MaterialRippleLayout rippleEditarArticuloComanda,rippleMostrarMasArticuloComanda;
        public ImageView imageViewTransparentText,imageViewFadeDeleteItem,imageViewMostrarMas;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        public ComandaViewHolder(View v){
            super(v);

            textViewDescripcion = v.findViewById(R.id.textViewDescripcion);
            textViewPrecio = v.findViewById(R.id.textViewPrecio);
            textViewNotas = v.findViewById(R.id.textViewNotas);
            textViewModificadoresNormales = v.findViewById(R.id.textViewModificadoresNormales);
            textViewCantidadArticulo = v.findViewById(R.id.textViewCantidadArticulo);
//            contentItemComanda = v.findViewById(R.id.contentItemComanda);
//            contentNotasModificadores = v.findViewById(R.id.contentNotasModificadores);
            rippleEditarArticuloComanda = v.findViewById(R.id.rippleEditarArticuloComanda);
            rippleMostrarMasArticuloComanda = v.findViewById(R.id.rippleMostrarMasArticuloComanda);
            contentHeaderItemComanda = v.findViewById(R.id.contentHeaderItemComanda);
            imageViewTransparentText = v.findViewById(R.id.imageViewTransparentText);
            imageViewFadeDeleteItem = v.findViewById(R.id.imageViewFadeDeleteItem);
            imageViewMostrarMas = v.findViewById(R.id.imageViewMostrarMas);

        }

        public void setData(ComandaArticulo itemData){
            this.itemData = itemData;
            textViewDescripcion.setText(itemData.getDES_PRO());
            textViewPrecio.setText(decimalFormat.format(itemData.getPRE_ART() * itemData.getCAN_PRO()));
            textViewCantidadArticulo.setText(String.valueOf(itemData.getCAN_PRO()));

            String stringNotas = getStringNotas(itemData);
            textViewNotas.setText(stringNotas);

            if(itemData.getModificable()==1){
                textViewModificadoresNormales.setVisibility(View.VISIBLE);
                textViewModificadoresNormales.setText(getStringModificadores(itemData));
            }else{
                textViewModificadoresNormales.setVisibility(View.GONE);
            }


        }

    }

    @NonNull
    @Override
    public ComandaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comanda,viewGroup,false);
        return new ComandaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComandaViewHolder comandaViewHolder, final int position) {

        final ComandaArticulo articulo = lstComandaArticulo.get(position);

//        comandaViewHolder.contentItemComanda.setActivated(selected_items.get(position, false));

        if(selected_items.get(position, false)){
            comandaViewHolder.imageViewFadeDeleteItem.setVisibility(View.VISIBLE);
        }else{
            comandaViewHolder.imageViewFadeDeleteItem.setVisibility(View.GONE);
        }

        ConstraintLayout.LayoutParams layoutParamsContentModificadoresNotas = (ConstraintLayout.LayoutParams)comandaViewHolder.rippleMostrarMasArticuloComanda.getLayoutParams();

        if(articulo.isExpanded()){

            comandaViewHolder.imageViewMostrarMas.animate().rotation(180).start();

            layoutParamsContentModificadoresNotas.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            comandaViewHolder.rippleMostrarMasArticuloComanda.setLayoutParams(layoutParamsContentModificadoresNotas);

            comandaViewHolder.imageViewTransparentText.setVisibility(View.GONE);
        }else{

            comandaViewHolder.imageViewMostrarMas.animate().rotation(0).start();
            layoutParamsContentModificadoresNotas.height = (int) context.getResources().getDimension(R.dimen.height_content_modificadores_notas_item_comanda);

            comandaViewHolder.rippleMostrarMasArticuloComanda.setLayoutParams(layoutParamsContentModificadoresNotas);

            comandaViewHolder.imageViewTransparentText.setVisibility(View.VISIBLE);
        }



//        comandaViewHolder.contentItemComanda.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mListener == null || getSelectedItemCount()<=0) return;
//                mListener.onItemClick(v, articulo, position);
//            }
//        });

        comandaViewHolder.imageViewFadeDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null || getSelectedItemCount()<=0) return;
                mListener.onItemClick(v, articulo, position);
            }
        });

        comandaViewHolder.rippleMostrarMasArticuloComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) return;
                //desplegable

                lstComandaArticulo.get(position).setExpanded(!articulo.isExpanded());

                notifyItemChanged(position);
            }
        });

        comandaViewHolder.rippleEditarArticuloComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener == null) return;
                mListener.onItemClickEditar(v, articulo, position);


            }
        });

        comandaViewHolder.contentHeaderItemComanda.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mListener == null) return false;
                mListener.onItemLongClick(v, articulo, position);
                return true;
            }
        });

//        toggleCheckedIcon(holder, position);
//        displayImage(holder, inbox);

        comandaViewHolder.setData(lstComandaArticulo.get(position));
    }

    @Override
    public int getItemCount() {
        return lstComandaArticulo.size();
    }

    @Override
    public Filter getFilter() {
        return filtro;
    }

    private Filter filtro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ComandaArticulo> filteredList = new ArrayList<>();
            if(constraint==null || constraint.length() == 0){
                filteredList.addAll(lstComandaArticuloFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ComandaArticulo item : lstComandaArticuloFull){
                    if(item.getDES_PRO().toLowerCase().contains(filterPattern)){
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
            lstComandaArticulo.clear();
            lstComandaArticulo.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

//    private void displayImage(ViewHolder holder, Inbox inbox) {
//        if (inbox.image != null) {
//            holder.image.setImageResource(inbox.image);
//            holder.image.setColorFilter(null);
//            holder.image_letter.setVisibility(View.GONE);
//        } else {
//            holder.image.setImageResource(R.drawable.shape_circle);
//            holder.image.setColorFilter(inbox.color);
//            holder.image_letter.setVisibility(View.VISIBLE);
//        }
//    }

//    private void toggleCheckedIcon(ComandaViewHolder holder, int position) {
//        if (selected_items.get(position, false)) {
//            holder.lyt_image.setVisibility(View.GONE);
//            holder.lyt_checked.setVisibility(View.VISIBLE);
//            if (current_selected_idx == position) resetCurrentIndex();
//        } else {
//            holder.lyt_checked.setVisibility(View.GONE);
//            holder.lyt_image.setVisibility(View.VISIBLE);
//            if (current_selected_idx == position) resetCurrentIndex();
//        }
//    }

    public ComandaArticulo getItem(int position) {
        return lstComandaArticulo.get(position);
    }

    public void toggleSelection(int pos) {
        current_selected_idx = pos;
        if (selected_items.get(pos, false)) {
            selected_items.delete(pos);
        } else {
            selected_items.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selected_items.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selected_items.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selected_items.size());
        for (int i = 0; i < selected_items.size(); i++) {
            items.add(selected_items.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        lstComandaArticulo.remove(position);
        lstComandaArticuloFull = new ArrayList<>(lstComandaArticulo);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        current_selected_idx = -1;
    }

    public interface AdapterComandaListener {
        void onItemClick(View view, ComandaArticulo obj, int pos);

        void onItemClickEditar(View view, ComandaArticulo obj, int pos);

        void onItemClickDesplegar(View view, ComandaArticulo obj, int pos);

        void onItemLongClick(View view, ComandaArticulo obj, int pos);
    }


    private String getStringModificadores(ComandaArticulo comandaArticulo){
        String stringModificadores = "";

        int i = 0;

        for(ModificadorSeleccionado modificadorSeleccionado : comandaArticulo.getModificadorSeleccionados()){
            i++;

            int j = 0;
            for (Modificador modificador: modificadorSeleccionado.getModificadoresSeleccionados()) {
                String tempComanda = modificador.getCantidad()+" "+modificador.getDescripcion();

                if(i == comandaArticulo.getModificadorSeleccionados().size() && j == modificadorSeleccionado.getModificadoresSeleccionados().size()){
                    tempComanda =(!stringModificadores.equals("")?" ":"")+tempComanda+".";
                }else{
                    tempComanda = (!stringModificadores.equals("")?" ":"")+tempComanda+",";
                }

                stringModificadores+=tempComanda;
            }
        }

        return stringModificadores;
    }

    private String getStringNotas(ComandaArticulo comandaArticulo){
        String stringNotas = "";

        for(int i = 0 ; i<comandaArticulo.getNotasList().size();i++){
            if(i != 0){
                stringNotas += "\r\n"+comandaArticulo.getNotasList().get(i);
            }else{
                stringNotas += comandaArticulo.getNotasList().get(i);
            }

        }

        return stringNotas;
    }
}
