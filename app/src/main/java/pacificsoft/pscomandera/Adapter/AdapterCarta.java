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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Modelo.Carta;
import pacificsoft.pscomandera.R;

public class AdapterCarta extends RecyclerView.Adapter<AdapterCarta.CartaViewHolder> implements Filterable {
  List<Carta> lstCartas;
  List<Carta> lstCartasFull;
  Context context;
  protected ItemCartaListener mListener;
  public boolean cartaSeleccionada = false;

  public AdapterCarta(List<Carta> lstCartas, Context context, ItemCartaListener mListener) {
    this.lstCartas = lstCartas;
    lstCartasFull = new ArrayList<>(this.lstCartas);
    this.context = context;
    this.mListener = mListener;
  }

  class CartaViewHolder extends RecyclerView.ViewHolder{
    public TextView textViewDescripcionCarta;
    public Carta itemData;
    public ConstraintLayout contentItemCarta;
    public CartaViewHolder(View v){
      super(v);
      textViewDescripcionCarta = v.findViewById(R.id.textViewDescripcionCarta);
      contentItemCarta = v.findViewById(R.id.contentItemCarta);
    }

    public void setData(Carta itemData){
      this.itemData = itemData;
      textViewDescripcionCarta.setText(itemData.getDescripcion());
    }
  }

  @NonNull
  @Override
  public CartaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_carta,viewGroup,false);
    return new CartaViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull CartaViewHolder cartaViewHolder, int i) {
    final Carta carta = lstCartas.get(i);

    cartaViewHolder.setData(carta);

    cartaViewHolder.contentItemCarta.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mListener == null || cartaSeleccionada) return;
        cartaSeleccionada = true;
        mListener.onItemCartaClick(carta);
      }
    });
  }

  @Override
  public int getItemCount() {
    return lstCartas.size();
  }

  public interface ItemCartaListener {
    void onItemCartaClick(Carta carta);
  }

  @Override
  public Filter getFilter() {
    return filtro;
  }

  private Filter filtro = new Filter() {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
      List<Carta> filteredList = new ArrayList<>();
      if(constraint==null || constraint.length() == 0){
        filteredList.addAll(lstCartasFull);
      }else{
        String filterPattern = constraint.toString().toLowerCase().trim();
        for (Carta item : lstCartasFull){
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
      lstCartas.clear();
      lstCartas.addAll((List) results.values);
      notifyDataSetChanged();
    }
  };
}
