package pacificsoft.pscomandera.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Modelo.Platillo;
import pacificsoft.pscomandera.R;

public class AdapterPlatillo extends RecyclerView.Adapter<AdapterPlatillo.PlatilloViewHolder> implements Filterable {
    List<Platillo> lstPlatillos;
    List<Platillo>lstPlatillosFull;
    Context context;
    protected ItemPlatilloListener mListener;
    public boolean platilloSeleccionado = false;

    public AdapterPlatillo(List<Platillo> lstPlatillos, Context context, ItemPlatilloListener mListener) {
        this.lstPlatillos = lstPlatillos;
        lstPlatillosFull =lstPlatillos;
        this.context = context;
        this.mListener = mListener;
    }

    class PlatilloViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewDescripcionPlatillo;
        public ImageView imageViewImagenPlatillo;
        public Platillo itemData;
        public ConstraintLayout contentItemPlatillo;
        public PlatilloViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            textViewDescripcionPlatillo = v.findViewById(R.id.textViewDescripcionPlatillo);
            imageViewImagenPlatillo = v.findViewById(R.id.imageViewImagenPlatillo);
            contentItemPlatillo = v.findViewById(R.id.contentItemPlatillo);
        }

        public void setData(Platillo itemData) {
            this.itemData = itemData;
            textViewDescripcionPlatillo.setText(itemData.getDescripcion());
            if(itemData.getImagen()!=null && itemData.getImagen()!=""){
                byte[] decodedString = Base64.decode(itemData.getImagen(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageViewImagenPlatillo.setImageBitmap(decodedByte);
            }
        }

        @Override
        public void onClick(View v) {

            if(mListener==null || platilloSeleccionado) return;

            platilloSeleccionado = true;
            mListener.onItemPlatilloClick(itemData);

        }
    }

    @NonNull
    @Override
    public PlatilloViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_platillo,viewGroup,false);
        return new PlatilloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatilloViewHolder platilloViewHolder, int i) {

        final Platillo platillo = lstPlatillos.get(i);

        platilloViewHolder.setData(platillo);
        platilloViewHolder.contentItemPlatillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) return;
                mListener.onItemPlatilloClick(platillo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstPlatillos.size();
    }

    public interface ItemPlatilloListener {
        void onItemPlatilloClick(Platillo platillo);
    }

    @Override
    public Filter getFilter() {
        return filtro;
    }

    private Filter filtro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Platillo> filteredList = new ArrayList<>();
            if(constraint==null || constraint.length() == 0){
                filteredList.addAll(lstPlatillos);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Platillo item : lstPlatillos){
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
            lstPlatillos.clear();
            lstPlatillos.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
