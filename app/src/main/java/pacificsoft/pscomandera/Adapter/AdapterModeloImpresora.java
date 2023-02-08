package pacificsoft.pscomandera.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pacificsoft.pscomandera.Enum.EnumModeloImpresora;
import pacificsoft.pscomandera.R;

public class AdapterModeloImpresora extends BaseAdapter {

    private List<EnumModeloImpresora> listModeloImpresora;
    private Context context;

    public AdapterModeloImpresora(List<EnumModeloImpresora> listModeloImpresora, Context context) {
        this.listModeloImpresora = listModeloImpresora;
        this.context = context;
    }

    public List<EnumModeloImpresora> getListImpresora() {
        return listModeloImpresora;
    }

    @Override
    public int getCount() {
        return listModeloImpresora.size();
    }

    @Override
    public EnumModeloImpresora getItem(int position) {
        return getListImpresora().get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista;
        LayoutInflater inflater = LayoutInflater.from( context );
        vista = inflater.inflate( R.layout.row_item_impresora_pinpad_spinner, parent, false );
        TextView spinnerLblNombreDispositivo, spinnerLblMacDispositivo,lblIconImpresora;
        ConstraintLayout lyRowItem;

        spinnerLblNombreDispositivo = vista.findViewById(R.id.spinnerLblNombreDispositivo);
        spinnerLblMacDispositivo = vista.findViewById(R.id.spinnerLblMacDispositivo);
        lblIconImpresora = vista.findViewById(R.id.lblIconImpresoraPinpad);
        lyRowItem = (ConstraintLayout) vista.findViewById(R.id.lyItemImpresoraPinpad);

        /*Asignacion de los valores*/
        if(position==0){
            lblIconImpresora.setVisibility(View.GONE);
            spinnerLblNombreDispositivo.setTextAppearance(context,R.style.hintItemStyleSpinner);
            spinnerLblNombreDispositivo.setText( listModeloImpresora.get(position).getModelo());
            spinnerLblMacDispositivo.setVisibility(View.GONE);
            lyRowItem.setPadding(0,0,0,0);
        }else{
            lblIconImpresora.setText( R.string.icon_print);
            spinnerLblNombreDispositivo.setText( listModeloImpresora.get( position ).getModelo());
            spinnerLblMacDispositivo.setVisibility(View.GONE);
        }


        return vista;
    }

}
