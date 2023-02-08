package pacificsoft.pscomandera.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.R;

public class AdapterNotasRecyclerView extends RecyclerView.Adapter<AdapterNotasRecyclerView.NotaViewHolder>{
    private Context context;
    private List<String> lstData;
    public AdapterNotaListener mListener;

    private SparseBooleanArray selected_items = new SparseBooleanArray();;
    private  int current_selected_idx = -1;

    public void setListenerNotas(AdapterNotaListener adapterNotaListener) {
        this.mListener = adapterNotaListener;
    }

    public AdapterNotasRecyclerView(Context context, List<String> lstData) {
        this.context = context;
        this.lstData = lstData;
    }

    class NotaViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewNota;
        public View dividerRight;
        public ConstraintLayout constraintContentItemNota;
        public NotaViewHolder(View v) {
            super(v);
            textViewNota = v.findViewById(R.id.textViewDescripcion);
            dividerRight = v.findViewById(R.id.dividerRight);
            constraintContentItemNota = v.findViewById(R.id.constraintContentItemNota);
        }

    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nota,viewGroup,false);
        return new NotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder notaViewHolder, final int position) {
        final String nota = lstData.get(position);
        notaViewHolder.textViewNota.setText(nota);

        notaViewHolder.constraintContentItemNota.setActivated(selected_items.get(position, false));

        notaViewHolder.constraintContentItemNota.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mListener == null) return false;
                mListener.onItemLongClick(v, nota, position);
                return true;
            }
        });

        notaViewHolder.constraintContentItemNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) return;
                mListener.onItemClick(v, nota, position);
            }
        });


//        if (position%2==0){
//            notaViewHolder.dividerRight.setVisibility(View.VISIBLE);
//        }else{
//            notaViewHolder.dividerRight.setVisibility(View.GONE);
//        }

    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public String getItem(int position) {
        return lstData.get(position);
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
        lstData.remove(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        current_selected_idx = -1;
    }

    public interface AdapterNotaListener {
        void onItemLongClick(View view, String obj, int pos);
        void onItemClick(View view,String obj, int pos);
    }
}
