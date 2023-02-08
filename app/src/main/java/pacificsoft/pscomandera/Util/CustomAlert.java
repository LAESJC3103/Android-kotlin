package pacificsoft.pscomandera.Util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Modelo.TipoModal;
import pacificsoft.pscomandera.R;

public class CustomAlert {

    private Context context;
    Button btnAceptarPopup, btnCancelarPopup;
    MaterialRippleLayout rippleCancelar;
    Dialog dialog;
    ImageView imgAlert;
    TextView lblMsjAlertCustom,lblTituloAlertCustom;
    DatosRegresados datosRegresados;
    TipoModal tipoModal;
    String msj = "";
    String titulo = "";
    String textAceptar = "";
    String textCancelar = "";

    private AceptarListener aceptarListener;
    private CancelarListener cancelarListener;



//    private CustomAlertListener mListener;


    public interface AceptarListener{
        void OnClickAceptar(final TipoModal tipoModal);
    }

    public interface CancelarListener{
        void OnClickCancelar(final TipoModal tipoModal);
    }

    public CustomAlert(Context context,final TipoModal tipoModal, final DatosRegresados datosRegresados) {
        this.context = context;
        this.tipoModal = tipoModal;
        this.datosRegresados = datosRegresados;

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alerta_custom);

        btnAceptarPopup = (Button) dialog.findViewById(R.id.btnAceptarPopup);
        btnCancelarPopup = (Button) dialog.findViewById(R.id.btnCancelarPopup);
        rippleCancelar = (MaterialRippleLayout) dialog.findViewById(R.id.rippleCancelar);

        imgAlert = (ImageView) dialog.findViewById(R.id.imgIconMsj);
        lblTituloAlertCustom = (TextView) dialog.findViewById(R.id.lblTituloAlertCustom);
        lblMsjAlertCustom = (TextView) dialog.findViewById(R.id.lblMsjAlertCustom);




        //dialog.show();
        //instacia un nuevo dialogo
        //popup = new Dialog(context);
        //comando que indica que no tendra titulo default
        //popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //selecciona el layout que sera la vista del popup
        //popup.setContentView(R.layout.alerta_custom);

        //Configuracion de icono a mostrar en el dialog
        switch (tipoModal){

            case ERROR_OPERACION:
                //Obtiene el icono que se pintara en el dialogo
                imgAlert.setImageResource(R.drawable.ic_error);
                break;
            case CERRAR_SESION:case MESA_OCUPADA:case CERRAR_SESION_SIN_GUARDAR:
                imgAlert.setImageResource(R.drawable.ic_alert);
                imgAlert.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case TOMAR_MESA:
                imgAlert.setVisibility(View.GONE);
                break;
            case OPERACION_EXITOSA:
//                imgAlert.setImageResource(R.drawable.ok);
                break;
        }
        //Configuracion de icono a mostrar en el dialog

        //Configuracion del titulo a mostrar en el error
        switch (tipoModal){

            case CERRAR_SESION:case CERRAR_SESION_SIN_GUARDAR:
                lblTituloAlertCustom.setText(context.getString(R.string.nav_drawer_cerrar_sesion));
                break;
            case TOMAR_MESA:
                lblTituloAlertCustom.setText(context.getString(R.string.msj_tomar_mesa));
                break;
            case MESA_OCUPADA:
                lblTituloAlertCustom.setText(context.getString(R.string.msj_mesa_ocupada));
                break;
        }
        //Configuracion del titulo a mostrar en el error

        //Configuracion del mensaje descriptivo de la alerta
        switch (tipoModal){
            case CERRAR_SESION:
                //setea el mensaje del error que esta presentando
                //lblMsjAlertCustom.setText("La impresora se encuentra apagada o fuera del alcance, por favor revise el dispositivo.");
                msj = context.getString(R.string.msj_alert_cerrar_sesion);
                break;
            case CERRAR_SESION_SIN_GUARDAR:
                msj = context.getString(R.string.msj_alert_cerrar_sesion_sin_guardar);
                break;
            case TOMAR_MESA:
                //setea el mensaje del error que esta presentando
                //lblMsjAlertCustom.setText("La impresora se encuentra apagada o fuera del alcance, por favor revise el dispositivo.");
                msj = context.getString(R.string.msj_tomar_mesa_descripcion);
                break;
            case MESA_OCUPADA:
                msj = context.getString(R.string.msj_mesa_ocupada_descripcion);
                break;

        }

    }

    public void show(){

        lblMsjAlertCustom.setText(msj);


        btnAceptarPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aceptarListener!=null){
                    aceptarListener.OnClickAceptar(tipoModal);
                }else{
                    dialog.dismiss();
                }
            }
        });


        btnCancelarPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cancelarListener!=null){
                    cancelarListener.OnClickCancelar(tipoModal);
                }else{
                    dialog.dismiss();
                }
            }
        });


        rippleCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cancelarListener!=null){
                    cancelarListener.OnClickCancelar(tipoModal);
                }else{
                    dialog.dismiss();
                }
            }
        });

        //Configuracion del mensaje descriptivo de la alerta

        switch (tipoModal){
            case CERRAR_SESION:case TOMAR_MESA:case MESA_OCUPADA:case CERRAR_SESION_SIN_GUARDAR:{
                //Setea el backgruond que tendra el botton
                //btnAceptarPopup.setBackgroundResource(R.drawable.boton_primary_alert_radius);
                //Setea el style que corresponde al texto de exte boton
                //btnAceptarPopup.setTextAppearance(context,R.style.btnPrimaryAceptarAlertCustom);
                //
                btnAceptarPopup.setText(context.getString(R.string.msj_boton_aceptar));
                break;
            }

        }

        switch (tipoModal){
            case CERRAR_SESION:case TOMAR_MESA:case CERRAR_SESION_SIN_GUARDAR:{
                //Setea el backgruond que tendra el botton
                //btnCancelarPopup.setBackground(null);
                //Setea el style que corresponde al texto de exte boton
                //btnCancelarPopup.setTextAppearance(context,R.style.btnPrimaryCancelAlertCustom);
                //
                btnCancelarPopup.setText(context.getString(R.string.msj_boton_cancelar));
                break;
            }
            case MESA_OCUPADA:
                btnCancelarPopup.setVisibility(View.GONE);
                rippleCancelar.setVisibility(View.GONE);
        }



        /*
        //Genera el evento onclick
        btnAceptarPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aceptarListener!=null){
                    aceptarListener.OnClickAceptar(tipoModal);
                }else{
                    dismiss();
                }
            }
        });


        //Genera el evento onclick
        btnCancelarPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cancelarListener!=null){
                    cancelarListener.OnClickCancelar(tipoModal);
                }else{
                    dismiss();
                }

            }
        });

        rippleCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cancelarListener!=null){
                    cancelarListener.OnClickCancelar(tipoModal);
                }else{
                    dismiss();
                }
            }
        });

        */
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

    }

    public void setImagen(int drawable){
        imgAlert.setImageResource(drawable);
    }

    public void setTitulo(String titulo){
        lblTituloAlertCustom.setText(titulo);
    }
    public void setMensaje(String msj){
        this.msj=msj;
    }

    public void setMostrarImagen(int visivility){

        imgAlert.setVisibility(visivility);
    }

    public void setMostrarTitulo(int visivility){
        lblTituloAlertCustom.setVisibility(visivility);
    }

    public void setMostrarMensaje(int visivility){
        lblMsjAlertCustom.setVisibility(visivility);
    }

    public void setAceptarClickListener(AceptarListener mlistener,String texto){

        aceptarListener = mlistener;
        btnAceptarPopup.setText(texto);

    }

    public void setCancelarClickListener(CancelarListener mListener,String texto){
        cancelarListener = mListener;
    }

    public void dismiss(){
        dialog.dismiss();
    }

}
