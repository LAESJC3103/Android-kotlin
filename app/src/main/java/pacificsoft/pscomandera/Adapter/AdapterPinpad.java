package pacificsoft.pscomandera.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pacificsoft.pscomandera.Modelo.Pinpad;
import pacificsoft.pscomandera.R;

public class AdapterPinpad extends BaseAdapter {

    private List<Pinpad> listPinpad;
    private Context context;

    public AdapterPinpad(List<Pinpad> listPinpad, Context context) {
        this.listPinpad = listPinpad;
        this.context = context;
    }

    public List<Pinpad> getListPinpad() {
        return listPinpad;
    }

    @Override
    public int getCount() {
        return getListPinpad().size();
    }

    @Override
    public Pinpad getItem(int position) {
        return getListPinpad().get( position );
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
        TextView spinnerLblNombreDispositivo, spinnerLblMacDispositivo,lblIconImpresoraPinpad;
        ConstraintLayout lyRowItem;

        spinnerLblNombreDispositivo = vista.findViewById(R.id.spinnerLblNombreDispositivo);
        spinnerLblMacDispositivo = vista.findViewById(R.id.spinnerLblMacDispositivo);
        lblIconImpresoraPinpad = vista.findViewById(R.id.lblIconImpresoraPinpad);
        lyRowItem = vista.findViewById(R.id.lyItemImpresoraPinpad);

        /*Asignacion de los valores*/
        if(position==0){
            lblIconImpresoraPinpad.setVisibility(View.GONE);
            spinnerLblNombreDispositivo.setTextAppearance(context,R.style.hintItemStyleSpinner);
            spinnerLblNombreDispositivo.setText( listPinpad.get( position ).getModelName() );
            spinnerLblMacDispositivo.setVisibility(View.GONE);
            lyRowItem.setPadding(0,0,0,0);
        }else{
            lblIconImpresoraPinpad.setText( R.string.icon_card);
            spinnerLblNombreDispositivo.setText( listPinpad.get( position ).getModelName() );
            spinnerLblMacDispositivo.setText( listPinpad.get( position ).getMacAddress() );
        }


        return vista;
    }

}
