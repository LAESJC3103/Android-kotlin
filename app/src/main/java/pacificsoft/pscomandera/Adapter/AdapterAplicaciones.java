package pacificsoft.pscomandera.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pacificsoft.pscomandera.Modelo.Aplicacion;
import pacificsoft.pscomandera.R;

public class AdapterAplicaciones extends RecyclerView.Adapter<AdapterAplicaciones.AplicacionViewHolder> {


    ConstraintSet constraintSetDefault = new ConstraintSet();
    ConstraintSet constraintSetShowText = new ConstraintSet();
    Context context;

    ConstraintLayout constraintLayout ;

    List<Aplicacion> aplicaciones;

    private int expandedPosition = -1;


    public AdapterAplicaciones(Context context, List<Aplicacion> aplicaciones) {
        this.context = context;
        this.aplicaciones = aplicaciones;
    }

    public class AplicacionViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewItemAplicacion;
        ImageView imageViewItemAplicacion;
        TextView textViewTituloAplicacion;
        Button buttonDescargarAplicacion;
        ConstraintLayout constraintLayout;
        ImageView imageViewArrowExpand;
        TextView textViewInformacion;


        AplicacionViewHolder(View view) {
            super(view);
            cardViewItemAplicacion = (CardView) itemView.findViewById(R.id.cardViewItemAplicacion);
            imageViewItemAplicacion = (ImageView) itemView.findViewById(R.id.imageViewItemAplicacion);
            textViewTituloAplicacion = (TextView) itemView.findViewById(R.id.textViewTituloAplicacion);
            buttonDescargarAplicacion = (Button) itemView.findViewById(R.id.buttonDescargarAplicacion);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraintLayoutAplicacion);
            imageViewArrowExpand = (ImageView) itemView.findViewById(R.id.imageViewArrowExpand);
            textViewInformacion = (TextView) itemView.findViewById(R.id.textViewInformacion);
        }
    }





    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return aplicaciones.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @NonNull
    @Override
    public AdapterAplicaciones.AplicacionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_aplicacion, viewGroup, false);
        AplicacionViewHolder svh = new AplicacionViewHolder(v);
        return svh;

    }

    @Override
    public void onBindViewHolder(final AplicacionViewHolder aplicacionViewHolder, int i){

        final int position = i;
        final boolean isExpanded = position==expandedPosition;


        aplicacionViewHolder.textViewTituloAplicacion.setText(aplicaciones.get(i).getNombreAplicacion());
        aplicacionViewHolder.imageViewItemAplicacion.setImageResource(aplicaciones.get(i).getIconoAplicacion());
        aplicacionViewHolder.textViewInformacion.setText(aplicaciones.get(i).getDescripcion());

        constraintLayout = aplicacionViewHolder.constraintLayout;

        //Layout principal o  Default
        constraintSetDefault.clone(context,R.layout.content_row_aplicacion);
        //Layout con los cambios a los elementos
        constraintSetShowText.clone(context, R.layout.content_row_aplicacion_alt);

        if(isExpanded){
            showRowAplicacionAltAnimation();
        }else{
            hideRowAplicacionAnimation();
        }

        aplicacionViewHolder.imageViewArrowExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expandedPosition = isExpanded?-1:position;
                notifyDataSetChanged();

            }
        });

    }

    private void showRowAplicacionAltAnimation(){


        ChangeBounds changeBounds = new ChangeBounds();


        changeBounds.setInterpolator(new AccelerateInterpolator());

        changeBounds.setDuration(800);

        TransitionManager.beginDelayedTransition(constraintLayout,changeBounds);

        constraintSetShowText.applyTo(constraintLayout);


    }

    private void hideRowAplicacionAnimation(){


        ChangeBounds changeBounds = new ChangeBounds();


        changeBounds.setInterpolator(new AccelerateInterpolator());

        changeBounds.setDuration(800);

        TransitionManager.beginDelayedTransition(constraintLayout,changeBounds);

        constraintSetDefault.applyTo(constraintLayout);

    }



}
