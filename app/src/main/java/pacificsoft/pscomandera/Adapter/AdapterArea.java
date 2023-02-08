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

import pacificsoft.pscomandera.Modelo.Area;
import pacificsoft.pscomandera.R;

public class AdapterArea extends RecyclerView.Adapter<AdapterArea.AreaViewHolder> implements Filterable {
  List<Area> lstAreas;
  List<Area> lstAreasFull;
  Context context;
  protected ItemAreaListener mListener;
  public boolean areaSeleccionada = false;


  public AdapterArea(List<Area> lstAreas, Context context, ItemAreaListener mListener) {
    this.lstAreas = lstAreas;
    lstAreasFull = new ArrayList<>(lstAreas);
    this.context = context;
    this.mListener = mListener;
  }

  class AreaViewHolder extends RecyclerView.ViewHolder{
    public TextView textViewDescripcionArea;
    public Area itemData;
    public ConstraintLayout contentItemArea;
    public AreaViewHolder(View v){
      super(v);
      textViewDescripcionArea = v.findViewById(R.id.textViewDescripcionArea);
      contentItemArea = v.findViewById(R.id.contentItemArea);
    }

    public void setData(Area itemData){
      this.itemData = itemData;
      textViewDescripcionArea.setText(itemData.getArea());
    }

  }

  @NonNull
  @Override
  public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_area,viewGroup,false);
    return new AreaViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull AreaViewHolder areaViewHolder, int i) {

    final Area area = lstAreas.get(i);

    areaViewHolder.setData(area);

    areaViewHolder.contentItemArea.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mListener == null || areaSeleccionada) return;
        areaSeleccionada = true;
        mListener.onItemAreaClick(area);
      }
    });

  }

  @Override
  public int getItemCount() {
    return lstAreas.size();
  }

  public interface ItemAreaListener{
    void onItemAreaClick(Area area);
  }

  @Override
  public Filter getFilter() {
    return filtro;
  }

  private Filter filtro = new Filter() {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
      List<Area> filteredList = new ArrayList<>();
      if(constraint==null || constraint.length() == 0){
        filteredList.addAll(lstAreasFull);
      }else{
        String filterPattern = constraint.toString().toLowerCase().trim();
        for (Area item : lstAreasFull){
          if(item.getArea().toLowerCase().contains(filterPattern)){
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
      lstAreas.clear();
      lstAreas.addAll((List) results.values);
      notifyDataSetChanged();
    }
  };

}
