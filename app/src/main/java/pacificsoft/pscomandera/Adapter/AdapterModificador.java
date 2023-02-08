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

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Modelo.Modificador;
import pacificsoft.pscomandera.R;

public class AdapterModificador extends RecyclerView.Adapter<AdapterModificador.ModificadorViewHolder>{
    List<Modificador> lstDatos;
    List<Modificador> selected_items_saved_instance;
    Context context;
    protected ItemModificadorListener mListener;
    private SparseBooleanArray selected_items;
    private  int current_selected_idx = -1;
    public int totalCantidad = 0;;
    private Modificador modificadorPrincipal;

    public interface ItemModificadorListener {
        void onItemModificadorClick();
    }

    public AdapterModificador(Modificador modificador, List<Modificador> modificadoresItem, List<Modificador> selected_items_saved_instance, Context context , ItemModificadorListener itemModificadorListener) {

        this.lstDatos = modificadoresItem;
        this.selected_items_saved_instance = selected_items_saved_instance;
        this.context = context;
        this.mListener = itemModificadorListener;

        this.modificadorPrincipal = modificador;

        if(modificador.getTipo().equals("2")||modificador.getTipo().equals("1")){
            this.selected_items = new SparseBooleanArray();
            setSelectedItem();

            if(modificadorPrincipal.getTipo().equals("2") && this.selected_items.size()<=0){
                this.selected_items.put(0, true);
            }
        }else if(modificador.getTipo().equals("3")){
            setCantidadItem();
        }

    }

    class ModificadorViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewDescripcion,textViewCantidad;
        public ImageView imageViewCheck,imageViewRadioDefault;
        public LinearLayout contentItemModificador;
        public ConstraintLayout constraintContentCheck;
        public MaterialRippleLayout rippleResetCantidad;
        public Modificador itemData;

        public ModificadorViewHolder(View v){
            super(v);
            contentItemModificador = v.findViewById(R.id.contentItemCuenta);
            textViewDescripcion = v.findViewById(R.id.textViewDescripcion);
            constraintContentCheck = v.findViewById(R.id.constraintContentCheck);
            imageViewCheck = v.findViewById(R.id.imageViewCheck);
            imageViewRadioDefault = v.findViewById(R.id.imageViewRadioDefault);
            textViewCantidad  = v.findViewById(R.id.textViewCantidad);
            rippleResetCantidad = v.findViewById(R.id.rippleResetCantidad);
        }

        public void setData(Modificador modificador) {
            this.itemData = modificador;
            textViewDescripcion.setText(itemData.getDescripcion());
            textViewCantidad.setText(String.valueOf(itemData.getCantidad()));
        }
    }

    @NonNull
    @Override
    public ModificadorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_modificador,viewGroup,false);
        return new ModificadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ModificadorViewHolder modificadorViewHolder, final int i) {

        final Modificador comandaModificador = lstDatos.get(i);

        //setea los valores a mostrar
        modificadorViewHolder.setData(comandaModificador);

        //Muestra/oculta opciones dependiendo de la modalidad
        switch (modificadorPrincipal.getTipo()){
            case "1": case "2":
                //Opcional - Intercambiable
                modificadorViewHolder.rippleResetCantidad.setVisibility(View.GONE);
                modificadorViewHolder.textViewCantidad.setVisibility(View.GONE);
                modificadorViewHolder.constraintContentCheck.setVisibility(View.VISIBLE);

                setStatusCheckItem(modificadorViewHolder,i);

                break;
            case "3":
                //Seleccionable
                modificadorViewHolder.constraintContentCheck.setVisibility(View.GONE);
                modificadorViewHolder.textViewCantidad.setVisibility(View.VISIBLE);
                modificadorViewHolder.rippleResetCantidad.setVisibility(View.VISIBLE);
                break;
        }

        switch (modificadorPrincipal.getTipo()){
            case "1":
                modificadorViewHolder.imageViewRadioDefault.setVisibility(View.GONE);
                break;
            case "2":
                modificadorViewHolder.imageViewRadioDefault.setVisibility(View.VISIBLE);
                break;
        }

        //Onclick al item
        modificadorViewHolder.contentItemModificador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) return;
                switch (modificadorPrincipal.getTipo()){
                    case "1":

                        //TIPO CHECK LIST
                        current_selected_idx = i;
                        if (selected_items.get(i, false)) {
                            selected_items.delete(i);

                            modificadorViewHolder.contentItemModificador.setActivated(false);
                        } else {
                            selected_items.put(i, true);
                            modificadorViewHolder.contentItemModificador.setActivated(true);
                        }
                        notifyItemChanged(i);


                        break;
                    case "2":
                        //TIPO RADIO
                        current_selected_idx = i;
                        if (!selected_items.get(i, false)) {

                            //Limpia el antigua seleccionado
                            notifyItemChanged(selected_items.keyAt(0));
                            selected_items.clear();

                            // agregar el nuevo seleccionado
                            selected_items.put(i, true);
                            notifyItemChanged(i);
                        }
                        break;
                    case "3" :
                        // TIPO CANTIDAD

                        lstDatos.get(i).setCantidad(lstDatos.get(i).getCantidad()+1);
                        totalCantidad += 1;

                        notifyItemChanged(i);

                        break;
                }

                mListener.onItemModificadorClick();

//                mListener.onClickItemModificador(comandaModificador);
            }
        });

        modificadorViewHolder.rippleResetCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalCantidad = totalCantidad - lstDatos.get(i).getCantidad();
                lstDatos.get(i).setCantidad(0);
                notifyItemChanged(i);
                mListener.onItemModificadorClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstDatos.size();
    }

    public List<Modificador> getSelectedItems(){

        List<Modificador> items = new ArrayList<>();

        if(modificadorPrincipal.getTipo().equals("3")){
            for(Modificador modificador:lstDatos){
                if(modificador.getCantidad()>0){
                    items.add(modificador);
                }
            }
        }else{
            for (int i = 0; i < selected_items.size(); i++) {
                int position = selected_items.keyAt(i);
                items.add(lstDatos.get(position));
            }
        }



        return items;
    }

    public Modificador getSelectedItem(){




        return new Modificador();
    }

    private void setStatusCheckItem(final ModificadorViewHolder modificadorViewHolder,int i){
        if(selected_items.get(i, false)){

            switch (modificadorPrincipal.getTipo()){
                case "1":
                    modificadorViewHolder.imageViewCheck.setImageResource(R.drawable.ic_checkmark);
                    break;
                case "2":
                    modificadorViewHolder.imageViewCheck.setImageResource(R.drawable.ic_round_check);
                    break;
            }

            switch (modificadorPrincipal.getTipo()){
                case "1": case "2":
                    modificadorViewHolder.contentItemModificador.setActivated(true);
                    break;
            }


//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
//                revealFAB(modificadorViewHolder);
//            }else{
                modificadorViewHolder.imageViewCheck.setVisibility(View.VISIBLE);
//            }

        }else{

            switch (modificadorPrincipal.getTipo()){
                case "2":
                    modificadorViewHolder.imageViewCheck.setImageResource(R.drawable.ic_round_uncheck);
            }

            switch (modificadorPrincipal.getTipo()){
                case "1": case "2":
                    modificadorViewHolder.contentItemModificador.setActivated(false);
                    break;
            }

//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
//                hideFAB(modificadorViewHolder);
//            }else{
                modificadorViewHolder.imageViewCheck.setVisibility(View.INVISIBLE);
//            }
        }

    }

    @TargetApi(21)
    private void revealFAB(final ModificadorViewHolder modificadorViewHolder) {


        final View view = modificadorViewHolder.imageViewCheck;

        final int cx = view.getMeasuredWidth() / 2;
        final int cy = view.getMeasuredHeight() / 2;

        final float finalRadius = Math.max(view.getWidth(), view.getHeight()) / 2;

        view.post(new Runnable() {
            @Override
            public void run() {
                Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
                view.setVisibility(View.VISIBLE);

                anim.start();
            }
        });

    }

    @TargetApi(21)
    private void hideFAB(final ModificadorViewHolder modificadorViewHolder) {
        final View view = modificadorViewHolder.imageViewCheck;

        final int cx = view.getMeasuredWidth() / 2;
        final int cy = view.getMeasuredHeight() / 2;

        final float initialRadius = view.getWidth() / 2;


        view.post(new Runnable() {
            @Override
            public void run() {
                Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        view.setVisibility(View.INVISIBLE);

//                        switch (modificadorPrincipal.getTipo()){
//                            case "1":
//                                view.setVisibility(View.INVISIBLE);
//                                break;
//                            case "2":
//                                revealFAB(modificadorViewHolder);
//                                break;
//                        }
                    }
                });

                anim.start();
            }
        });


    }

    private int getCantidadModificador(Modificador modificador){

        if(selected_items_saved_instance!=null){
            if(selected_items_saved_instance.contains(modificador)){
                return selected_items_saved_instance.get(selected_items_saved_instance.indexOf(modificador)).getCantidad();
            }else{
                return 0;
            }
        }else{
            return 0;
        }

    }


    public double getTotal(){
        double total = 0;

        switch (modificadorPrincipal.getTipo()){
            case "1":case "2":
                for (int i = 0;i<lstDatos.size();i++){
                    if(selected_items.get(i, false)){
                        total += lstDatos.get(i).getCosto();
                    }
                }
                break;
            case "3":
                if(totalCantidad>modificadorPrincipal.getMaximo()){
                    total += (modificadorPrincipal.getCosto() * (totalCantidad-modificadorPrincipal.getMaximo()));
                }
                break;
        }

        return total;
    }

    private void setCantidadItem(){
        for(int i = 0;i<lstDatos.size();i++){
            lstDatos.get(i).setCantidad(getCantidadModificador(lstDatos.get(i)));
        }
    }

    private void setSelectedItem(){
        if(selected_items_saved_instance!=null) {
            for (Modificador modificador : selected_items_saved_instance) {
                if (lstDatos.contains(modificador)) {
                    selected_items.put(lstDatos.indexOf(modificador), true);
                }
            }
        }
    }
    public int getCantidadSelectedModificador(){
        return this.totalCantidad;
    }
    public void setCantidadSelectedModificador(int cantidad){
        this.totalCantidad = cantidad;
    }

}
