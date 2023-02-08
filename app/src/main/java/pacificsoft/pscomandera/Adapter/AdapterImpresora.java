package pacificsoft.pscomandera.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starmicronics.stario.PortInfo;

import java.util.List;

import pacificsoft.pscomandera.Modelo.Impresora;
import pacificsoft.pscomandera.R;

public class AdapterImpresora extends BaseAdapter {

    private List<Impresora> listImpresora;
    private Context context;

    public AdapterImpresora(List<Impresora> listaArticulo, Context context) {
        this.listImpresora = listaArticulo;
        this.context = context;
    }

    public List<Impresora> getListImpresora() {
        return listImpresora;
    }

    public void setListImpresora(List<Impresora> listImpresora) {
        this.listImpresora = listImpresora;
    }

    @Override
    public int getCount() {
        return getListImpresora().size();
    }

    @Override
    public PortInfo getItem(int position) {
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
//        LinearLayout lyRowItem;

        spinnerLblNombreDispositivo = vista.findViewById(R.id.spinnerLblNombreDispositivo);
        spinnerLblMacDispositivo = vista.findViewById(R.id.spinnerLblMacDispositivo);
        lblIconImpresora = vista.findViewById(R.id.lblIconImpresoraPinpad);
//        lyRowItem = (LinearLayout) vista.findViewById(R.id.lyItemImpresoraPinpad);

        /*Asignacion de los valores*/
        if(position==0){
            lblIconImpresora.setVisibility(View.GONE);
            spinnerLblNombreDispositivo.setTextAppearance(context,R.style.hintItemStyleSpinner);
            spinnerLblNombreDispositivo.setText( listImpresora.get( position ).getPortName() );
            spinnerLblMacDispositivo.setVisibility(View.GONE);
//            lyRowItem.setPadding(0,0,0,0);
        }else{
            lblIconImpresora.setText( R.string.icon_print);
            spinnerLblNombreDispositivo.setText( listImpresora.get( position ).getPortName() );
            spinnerLblMacDispositivo.setText( listImpresora.get( position ).getMacAddress() );
        }


        return vista;
    }

}
