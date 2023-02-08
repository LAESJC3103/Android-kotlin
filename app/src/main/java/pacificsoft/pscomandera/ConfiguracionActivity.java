package pacificsoft.pscomandera;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ingenico.pclutilities.PclUtilities;
import com.prosepago.libpropago.mylibs.LibProsepago;
import com.starmicronics.stario.PortInfo;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pacificsoft.pscomandera.Adapter.AdapterAlmacen;
import pacificsoft.pscomandera.Adapter.AdapterCaja;
import pacificsoft.pscomandera.Adapter.AdapterImpresora;
import pacificsoft.pscomandera.Adapter.AdapterModeloImpresora;
import pacificsoft.pscomandera.Adapter.AdapterPinpad;
import pacificsoft.pscomandera.Enum.Enum;
import pacificsoft.pscomandera.Enum.EnumModeloImpresora;
import pacificsoft.pscomandera.Enum.EnumTablas;
import pacificsoft.pscomandera.Enum.EnumTipoPeticionAsyncTask;
import pacificsoft.pscomandera.Enum.StaticVariables;
import pacificsoft.pscomandera.Impresion.Metodos;
import pacificsoft.pscomandera.KSOAP2.WebService;
import pacificsoft.pscomandera.LocalManager.DbLocalManager;
import pacificsoft.pscomandera.Modelo.Almacen;
import pacificsoft.pscomandera.Modelo.Caja;
import pacificsoft.pscomandera.Modelo.Configuracion;
import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Modelo.Impresora;
import pacificsoft.pscomandera.Modelo.Pinpad;
import pacificsoft.pscomandera.Modelo.ResultadoAlmacen;
import pacificsoft.pscomandera.Modelo.ResultadoCajas;
import pacificsoft.pscomandera.Modelo.Terminal;
import pacificsoft.pscomandera.Modelo.TipoModal;
import pacificsoft.pscomandera.Loading.LoadingTask;
import pacificsoft.pscomandera.Modelo.Usuario;
import pacificsoft.pscomandera.Util.CargarConfiguracion;
import pacificsoft.pscomandera.Util.ConvertXmlToJSONManager;
import pacificsoft.pscomandera.Util.CustomAlert;

public class ConfiguracionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoadingTask.LoadingTaskListener, CargarConfiguracion.CargaConfiguracionListener {

    WebService serviceServicioPrincipal, serviceServiciosTerminal;
    Configuracion configuracion = null;
    private Spinner  spinImpresora,spinModeloImpresora,spinPinpad,spinCajas, spinAlmacenMercancia,spinAlmacenMateriaPrima;
    TextView textViewTituloActividad,edEndpointServiciosPrincipales, edEndpointServiciosPagoTarjeta, lblIconEstatusServiciosPrincipales,lblIconDatosTerminal, lblIconEstatusServiciosTerminal,lblHintImpresora,lblHintModeloImpresora,lblHintPinpad,lblMsjAlertCustom,lblTituloAlertCustom, lblHintAlmacenMercancia,lblHintAlmacenMateriaPrima,lblHintCajas,lblIconTipoVendedor;
    EditText edEmpresa, edDom, edNum, edRFC, edCP, edColonia,edClaveUsuario,edTerminal,edNoSerie,edCorreoElectronicoPagoTarjeta,edTipoVendedor;
    List<Caja> lstCajas;
    List<Almacen> lstAlmacenesMercancia;
    List<Almacen> lstAlmacenesMateriaPrima;
    ArrayList<Impresora> lstImpresora;
    ArrayList<Pinpad> lstPinpad;
    ArrayList<EnumModeloImpresora> lstModeloImpresora;
    DbLocalManager dbLocalManager = null;
    LoadingTask loadingTask;
    //ImageButton imgbtn = null;
    Button btnGuardar ,btnAceptarPopup, btnCancelarPopup;

    Terminal terminal = null;

    Impresora hintImpresora = null;
    Pinpad hintPinpad = null;
    Caja hintCaja;
    Almacen hintAlmacenMercancia;
    Almacen hintAlmacenMateriaPrima;
    String puntoVenta, proveedorPagoTarjeta;

    boolean loadStartConfiguration = true;

    LibProsepago oProsepago;

    Dialog popup;
    ImageView imgIconMsjError;

    Enum enumCustom = new Enum();

    private boolean valServicioPrincipalExitoso = Boolean.FALSE,valTipoVendedor = false;
    private boolean valServicioPagoTerminal = false;

    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    Menu menuNavigationView;

    TypedValue outValue;

    float alphaDisabledValue;
    float alphaEnabledValue = 1.0f;

    TextInputLayout textInputLayoutEndPointPrincipal,textInputLayoutEmpresa,textInputLayoutDomicilio,textInputLayoutNumero,textInputLayoutCodigoPostal,textInputLayoutColonia,textInputLayoutRFC,textInputLayoutTipoVendedor;


    AdapterAlmacen adapterAlmacenMercancia,adapterAlmacenMateriaPrima;
    AdapterCaja adapterCaja;
    AdapterImpresora adapterImpresora;
    AdapterModeloImpresora adapterModeloImpresora;
    AdapterPinpad adapterPinpad;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        this.setTitle( getString(R.string.title_activity_configuracion) );
        this.updateEsatusServicioPagoTerminal(false);

        outValue = new TypedValue();
        getResources().getValue(R.dimen.alpha_textinput_layout_disabled, outValue, true);

        alphaDisabledValue = outValue.getFloat();

        /*menuNavigationView */
        toolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        textViewTituloActividad = findViewById(R.id.textViewTituloActividad);

        textViewTituloActividad.setText(getString(R.string.title_activity_configuracion));

        setSupportActionBar(toolbar);

        configureNavigationDrawer();

        configureToolbar();


        /*****SECCION PRINCIPAL****/

        textInputLayoutEndPointPrincipal = findViewById(R.id.textInputLayoutEndPointPrincipal);
        textInputLayoutEmpresa = findViewById(R.id.textInputLayoutEmpresa);
        textInputLayoutDomicilio = findViewById(R.id.textInputLayoutDomicilio);
        textInputLayoutNumero = findViewById(R.id.textInputLayoutNumero);
        textInputLayoutCodigoPostal = findViewById(R.id.textInputLayoutCodigoPostal);
        textInputLayoutColonia = findViewById(R.id.textInputLayoutColonia);
        textInputLayoutRFC = findViewById(R.id.textInputLayoutRFC);
        textInputLayoutTipoVendedor = findViewById(R.id.textInputLayoutTipoVendedor);

        edEmpresa = (EditText) findViewById( R.id.edEmpresa );
        edDom = (EditText) findViewById( R.id.edDomicilio );
        edRFC = (EditText) findViewById( R.id.edRFC );
        edCP = (EditText) findViewById( R.id.edCP );
        edNum = (EditText) findViewById( R.id.edNumero );
        edTipoVendedor = findViewById(R.id.edTipoVendedor);

        edEndpointServiciosPrincipales = (TextView) findViewById( R.id.edEndpointServiciosPrincipales);
        lblIconEstatusServiciosPrincipales = (TextView) findViewById( R.id.lblIconEstatusServiciosPrincipales);
        lblIconTipoVendedor = findViewById(R.id.lblIconTipoVendedor);
        /*****SECCION PRINCIPAL****/


        /****SECCION CAJA-ALMACEN-IMPRESORA*****/
        lblHintCajas = findViewById(R.id.lblHintCajas);
        lblHintAlmacenMercancia = findViewById(R.id.lblHintAlmacenMercancia);
        lblHintAlmacenMateriaPrima = findViewById(R.id.lblHintAlmacenMateriaPrima);
        lblHintImpresora = findViewById(R.id.lblHintImpresora);
        lblHintModeloImpresora = findViewById(R.id.lblHintModeloImpresora);

        spinCajas = findViewById(R.id.spinCajas);
        spinAlmacenMercancia = findViewById(R.id.spinAlmacenMercancia);
        spinAlmacenMateriaPrima = findViewById(R.id.spinAlmacenMateriaPrima);
        spinImpresora = findViewById(R.id.spinImpresora);
        spinModeloImpresora = findViewById(R.id.spinModeloImpresora);


        /****SECCION CAJA-ALMACEN-IMPRESORA*****/

        /******SECCION TERMINAL******/
        spinPinpad = findViewById(R.id.spinPinpad);

        edColonia = (EditText) findViewById( R.id.edColonia );
        edEndpointServiciosPagoTarjeta = (TextView) findViewById( R.id.edEndpointServiciosPagoTarjeta);
        edClaveUsuario = (EditText) findViewById(R.id.edClaveUsuario);
        edTerminal = (EditText) findViewById(R.id.edTerminal);
        edNoSerie = (EditText) findViewById(R.id.edNoSerie);
        edCorreoElectronicoPagoTarjeta = findViewById(R.id.edCorreoElectronicoPagoTarjeta);

        lblHintPinpad = findViewById(R.id.lblHintPinpad);
        lblIconEstatusServiciosTerminal = findViewById(R.id.lblIconEstatusServiciosTerminal);
        lblIconDatosTerminal = findViewById(R.id.lblIconDatosTerminal);

        /******SECCION TERMINAL******/

        /************SECCION FINAL***********/
        btnGuardar = (Button) findViewById( R.id.btnguardar );
        /************SECCION FINAL***********/

        /*****************CARGAR VALORES INICIALES*****************/



        lblIconEstatusServiciosPrincipales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
//                        serviceServicioPrincipal = new WebService( edEndpointServiciosPrincipales.getText().toString() );
                    serviceServicioPrincipal = new WebService( edEndpointServiciosPrincipales.getText().toString() );
                    loadingTask = new LoadingTask(serviceServicioPrincipal, ConfiguracionActivity.this, EnumTipoPeticionAsyncTask.VALIDARSERVICIOPRINCIPAL, getString(R.string.msj_validando_servicio),null,ConfiguracionActivity.this,true);

                    loadingTask.execute();
                }catch (Exception ex){
                    Toast.makeText(ConfiguracionActivity.this,"lblIconEstatusServiciosPrincipales : "+ex.getMessage(),Toast.LENGTH_LONG);
                }

            }
        });

        edEndpointServiciosPrincipales.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lblIconEstatusServiciosPrincipales.setTextColor(getResources().getColor(R.color.colorError));
                habilitarSeccionDatosEmpresa(false);
                valServicioPrincipalExitoso = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        lblIconTipoVendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edTipoVendedor.getText().toString().trim()!=""){
                    valTipoVendedor = true;
                    lblIconTipoVendedor.setTextColor(getResources().getColor(R.color.colorSuccess));
                    fillComboCajasAlmacen(EnumTipoPeticionAsyncTask.CAJAS);
                    habilitarSeccionCajasAlmacenImpresora(true);
                }
            }
        });

        edTipoVendedor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valTipoVendedor = false;
                lblIconTipoVendedor.setTextColor(getResources().getColor(R.color.colorError));
                habilitarSeccionCajasAlmacenImpresora(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spinCajas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    lblHintCajas.setVisibility(View.VISIBLE);
                }else{
                    lblHintCajas.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinAlmacenMercancia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    lblHintAlmacenMercancia.setVisibility(View.VISIBLE);
                }else{
                    lblHintAlmacenMercancia.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinAlmacenMateriaPrima.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    lblHintAlmacenMateriaPrima.setVisibility(View.VISIBLE);
                }else{
                    lblHintAlmacenMateriaPrima.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        spinImpresora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                if(position>0){
//                    habilitarModeloImpresora(true);
//                }else{
//                    habilitarModeloImpresora(false);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//
//        });
//
//        spinModeloImpresora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                if(position>0){
//                    lblHintModeloImpresora.setVisibility(View.VISIBLE);
//                }else{
//                    lblHintModeloImpresora.setVisibility(View.INVISIBLE);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//
//        });


        spinPinpad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position>0){
                    lblHintPinpad.setVisibility(View.VISIBLE);
                }else{
                    lblHintPinpad.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });






        lblIconEstatusServiciosTerminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    serviceServiciosTerminal = new WebService( edEndpointServiciosPagoTarjeta.getText().toString() );
                    loadingTask = new LoadingTask(serviceServiciosTerminal, ConfiguracionActivity.this, EnumTipoPeticionAsyncTask.VALIDARSERVICIOTERMINAL, getString(R.string.msj_validando_servicio),null,ConfiguracionActivity.this,true);

                    loadingTask.execute();
                }catch (Exception ex){
                    Toast.makeText(ConfiguracionActivity.this,"lblIconEstatusServiciosTerminal : "+ex.getMessage(),Toast.LENGTH_LONG);
                }

            }
        });

        lblIconDatosTerminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Terminal temp = new Terminal();
                    temp.setTerminal(edTerminal.getText().toString());
                    loadingTask = new LoadingTask(serviceServiciosTerminal,ConfiguracionActivity.this,EnumTipoPeticionAsyncTask.DATOSTERMINAL,getString(R.string.msj_obteniendo_datos_terminal),terminal,ConfiguracionActivity.this,true);
                }catch (Exception ex){
                    Toast.makeText(ConfiguracionActivity.this,"lblIconDatosTerminal : "+ex.getMessage(),Toast.LENGTH_LONG);
                }
            }
        });

        btnGuardar.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                guardarConfiguracion();

            }
        } );


        startValues();




            //----------------------Toolbar and navigationView -----------------//

//            //Toolbar
//            toolbar = (Toolbar) findViewById(R.id.toolbar);
//            configureToolbar();
//
//            //Navigation View    ----> https://academiaandroid.com/navigation-drawer-y-clase-navigation-view/
//            mDrawerLayout = findViewById(R.id.drawer_layout_configuracion);
//            navigationView = findViewById(R.id.navigationView);
//
//            configureNavigationDrawer();
//
////            if (android.os.Build.VERSION.SDK_INT >= 21) {
////                findViewById(R.id.shadow_view).setVisibility(View.GONE);
////            }


    }

    @Override
    protected void onPostResume() {
        startValues();
        cargarConfiguracion();
//
//        cargarDispositivosBluetooth();
//
//        if(configuracion!=null){
//            fillComboCajasAlmacen();
//        }

        super.onPostResume();
    }




    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen( GravityCompat.START )) {
            mDrawerLayout.closeDrawer( GravityCompat.START );
        } else {
            alertCerrarSesion();
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id){
            case R.id.nav_actual_activity:
                break;
            case R.id.nav_acerca_de:
                llamarActividad(StaticVariables.REQUEST.ACERCA_DE_ACTIVITY);
                break;
            case R.id.cerrar_sesion:
                alertCerrarSesion();
                break;
        }


        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setSelectedItemNavigationView(R.id.nav_actual_activity);
    }

    @Override
    public void onLoadedDataLoadingTask(EnumTipoPeticionAsyncTask peticion, DatosRegresados datosregresados) {

            switch (peticion){
                case VALIDARSERVICIOPRINCIPAL:
                    //Cambia el color del icono de conexion exitosa en configuracion
                    if(!datosregresados.getError()){

                        if (!datosregresados.getError() && !datosregresados.getDatosRegresadosString().equals("")) {
                            try {

                                String xmlData = datosregresados.getDatosRegresadosString();
                                JSONObject obj = new JSONObject(xmlData);
                                JSONObject infoEmpresa = obj.getJSONObject("DatosEmpresa");

                                String codigo = obj.getString("Codigo");

                                if (codigo.equals("0")) {

                                    lblIconEstatusServiciosPrincipales.setTextColor(getResources().getColor(R.color.colorSuccess));
                                    habilitarSeccionDatosEmpresa(true);

                                    Toast.makeText(getApplicationContext(), getString(R.string.msj_conexion_exitosa), Toast.LENGTH_LONG).show();
                                    this.valServicioPrincipalExitoso = true;

                                    edEmpresa.setText(infoEmpresa.getString("Nombre"));
                                    edDom.setText(infoEmpresa.getString("Domicilio"));
                                    edColonia.setText(infoEmpresa.getString("Colonia"));
                                    edNum.setText(infoEmpresa.getString("Numero"));
                                    edCP.setText(infoEmpresa.getString("CP"));
                                    edRFC.setText(infoEmpresa.getString("RFC"));

                                }

                            }catch (Exception ex) {
                                StringWriter stringWriter = new StringWriter();
                                ex.printStackTrace(new PrintWriter(stringWriter));
                            }

                        } else {

                            lblIconEstatusServiciosPrincipales.setTextColor(getResources().getColor(R.color.colorError));
                            habilitarSeccionDatosEmpresa(false);
                            Toast.makeText( getApplicationContext(), getString(R.string.msj_conexion_no_disponible),Toast.LENGTH_LONG ).show();
                            this.valServicioPrincipalExitoso = false;

                        }
                    }else{
                        lblIconEstatusServiciosPrincipales.setTextColor(getResources().getColor(R.color.colorError));
                        habilitarSeccionDatosEmpresa(false);
                        Exception ex = Exception.class.cast(datosregresados.getDatosRegresados());
                        Toast.makeText( getApplicationContext(), ex.getMessage(),Toast.LENGTH_LONG ).show();
                    }

                    break;
                case VALIDARSERVICIOTERMINAL:
                    break;
                case DATOSTERMINAL:
                    break;
                case CAJAS:
                    if(!datosregresados.getError()){
                        ResultadoCajas resultadoCajas = ResultadoCajas.class.cast(datosregresados.getDatosRegresados());
                        llenarCajas(resultadoCajas.getCajas());
                        fillComboCajasAlmacen(EnumTipoPeticionAsyncTask.ALMACEN_MERCANCIA);
                    }else {
                        Exception ex = Exception.class.cast(datosregresados.getDatosRegresados());
                        Toast.makeText( getApplicationContext(), ex.getMessage(),Toast.LENGTH_LONG ).show();
                    }
                    break;
                case ALMACEN_MERCANCIA:case ALMACEN_MATERIA_PRIMA:
                    if(!datosregresados.getError()){

                        ResultadoAlmacen resultadoAlmacen = ResultadoAlmacen.class.cast(datosregresados.getDatosRegresados());
                        llenarAlmacen(resultadoAlmacen.getAlmacenes(), peticion);

                        if(peticion == EnumTipoPeticionAsyncTask.ALMACEN_MERCANCIA){
                            fillComboCajasAlmacen(EnumTipoPeticionAsyncTask.ALMACEN_MATERIA_PRIMA);
                        }
                    }else {
                        Exception ex = Exception.class.cast(datosregresados.getDatosRegresados());
                        Toast.makeText( getApplicationContext(), ex.getMessage(),Toast.LENGTH_LONG ).show();
                    }
                    break;
            }


    }

    @Override
    public void cargaConfiguracionCompleta(DatosRegresados datosregresados) {
        if(!datosregresados.getError()){
            configuracion = Configuracion.class.cast(datosregresados.getDatosRegresados());
            if(loadStartConfiguration){
                loadStartConfiguration = !loadStartConfiguration;
                if(configuracion!=null && configuracion.getEndPointServicioPrincipal()!=null && !configuracion.getEndPointServicioPrincipal().equals("")){
                    setUsuarioLogueado();
                    cargarDatosConfiguracion();
                    if(usuario!=null && usuario.getNombre()!=null){
                        fillComboCajasAlmacen(EnumTipoPeticionAsyncTask.CAJAS);
                    }
                }
            }

        }else{
            Exception ex = Exception.class.cast(datosregresados.getDatosRegresados());
            Toast.makeText( getApplicationContext(), ex.getMessage(),Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setSelectedItemNavigationView(int id){
        MenuItem nav_actual_activity = menuNavigationView.findItem(id);
        nav_actual_activity.setChecked(true);
    }

    private void setUsuarioLogueado(){

        if(configuracion!=null){
            /**/
            SharedPreferences mPreferences;

            mPreferences = getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE);

            String stringUsuario = mPreferences.getString(getString(R.string.SHPTAGUser),"");

            Type typeUsuario = new TypeToken<Usuario>(){}.getType();

            usuario = new Gson().fromJson(stringUsuario,typeUsuario);

            View headerView = navigationView.getHeaderView(0);
            TextView navUsername = (TextView) headerView.findViewById(R.id.textViewUserName);

            navUsername.setText(usuario!=null && usuario.getNombre()!=null?usuario.getNombre():"");

            /**/
        }else{
            View headerView = navigationView.getHeaderView(0);
            TextView navUsername = (TextView) headerView.findViewById(R.id.textViewUserName);
            navUsername.setText("");
        }

    }

    private void startValues(){

        hintCaja = new Caja("",getString(R.string.hintCaja));
        hintAlmacenMercancia = new Almacen("",getString(R.string.hintAlmacenMercancia));
        hintAlmacenMateriaPrima = new Almacen("",getString(R.string.hintAlmacenMateriaPrima));
        hintImpresora = new Impresora(getString(R.string.hintImpresora),"","","");
        hintPinpad = new Pinpad("",getString(R.string.hintImpresora));

        lblHintAlmacenMercancia.setVisibility(View.INVISIBLE);
        lblHintAlmacenMateriaPrima.setVisibility(View.INVISIBLE);
        lblHintCajas.setVisibility(View.INVISIBLE);
        lblHintImpresora.setVisibility(View.INVISIBLE);
        lblHintPinpad.setVisibility(View.INVISIBLE);
        lblHintModeloImpresora.setVisibility(View.INVISIBLE);

        lstModeloImpresora = enumCustom.getValuesEnumModeloImpresora();

        lstCajas = new ArrayList<>();
        lstCajas.add(hintCaja);
        adapterCaja = new AdapterCaja(lstCajas,ConfiguracionActivity.this);
        spinCajas.setAdapter(adapterCaja);

        lstAlmacenesMercancia = new ArrayList<>();
        lstAlmacenesMercancia.add(hintAlmacenMercancia);
        adapterAlmacenMercancia = new AdapterAlmacen(lstAlmacenesMercancia,ConfiguracionActivity.this);
        spinAlmacenMercancia.setAdapter(adapterAlmacenMercancia);

        lstAlmacenesMateriaPrima = new ArrayList<>();
        lstAlmacenesMateriaPrima.add(hintAlmacenMateriaPrima);
        adapterAlmacenMateriaPrima = new AdapterAlmacen(lstAlmacenesMateriaPrima,ConfiguracionActivity.this);
        spinAlmacenMateriaPrima.setAdapter(adapterAlmacenMateriaPrima);

        lstImpresora = new ArrayList<>();
        lstImpresora.add(hintImpresora);
        adapterImpresora = new AdapterImpresora(lstImpresora,ConfiguracionActivity.this);
        spinImpresora.setAdapter(adapterImpresora);

        adapterModeloImpresora = new AdapterModeloImpresora(lstModeloImpresora,ConfiguracionActivity.this);
        spinModeloImpresora.setAdapter(adapterModeloImpresora);

        lstPinpad = new ArrayList<>();
        lstPinpad.add(hintPinpad);
        adapterPinpad = new AdapterPinpad(lstPinpad,ConfiguracionActivity.this);
        spinPinpad.setSelection(0);

        habilitarSeccionDatosEmpresa(false);
        habilitarSeccionCajasAlmacenImpresora(false);
        habilitarModeloImpresora(false);

        spinAlmacenMercancia.setSelection(0);
        spinCajas.setSelection(0);
        spinImpresora.setSelection(0);
        spinModeloImpresora.setSelection(0);
        spinPinpad.setSelection(0);


    }

    private void cargarDatosConfiguracion(){
        edEndpointServiciosPrincipales.setText(configuracion.getEndPointServicioPrincipal());
        edEmpresa.setText(configuracion.getEmpresa());
        edDom.setText(configuracion.getDomicilio());
        edNum.setText(configuracion.getNumero());
        edCP.setText(configuracion.getCp());
        edColonia.setText(configuracion.getColonia());
        edRFC.setText(configuracion.getRfc());
        edTipoVendedor.setText(String.valueOf(configuracion.getTipovendedor()));
    }

    public void llenarImpresora (){
        List<PortInfo> mPortList;
        lstImpresora = new ArrayList<Impresora>();
        Impresora impresora;

        try {
            mPortList = StarIOPort.searchPrinter("BT:", ConfiguracionActivity.this);
            mPortList.add(0,hintImpresora);

            if(valServicioPrincipalExitoso){
                for(PortInfo portInfo : mPortList){
                    impresora = new Impresora(portInfo.getPortName(),portInfo.getMacAddress(),portInfo.getModelName(),portInfo.getUSBSerialNumber());
                    lstImpresora.add(impresora);
                }
            }

            //Aqui se llena el combo

            AdapterImpresora adapterImpresora = new AdapterImpresora(lstImpresora,ConfiguracionActivity.this);

            spinImpresora.setAdapter(adapterImpresora);

        }
        catch (StarIOPortException e) {
            Toast.makeText( getApplicationContext(), getString(R.string.msj_error_llenado_impresora),Toast.LENGTH_LONG ).show();
        }

    }

    private void cargarDispositivosBluetooth(){
        llenarImpresora();
        llenarPinpad();

        if(configuracion !=null){


            Impresora tempImp = new Impresora("",configuracion.getImpresora(),"","");
            setImpresora(tempImp);

            EnumModeloImpresora enumModeloImpresora = configuracion.getModeloImpresora();
            setModeloImpresora(enumModeloImpresora);

            Pinpad pinpad = new Pinpad(configuracion.getDireccionBTPinpad(),"");
            setPinpad(pinpad);

        }else{
            setImpresora(hintImpresora);
            setModeloImpresora(EnumModeloImpresora.NONE);
            setPinpad(hintPinpad);
        }
    }


    private void habilitarSolicitarRFC(boolean habilitar){

    }

    private void habilitarSeccionDatosEmpresa(boolean habilitar){

        edEmpresa.setEnabled(habilitar);
        edDom.setEnabled(habilitar);
        edNum.setEnabled(habilitar);
        edCP.setEnabled(habilitar);
        edColonia.setEnabled(habilitar);
        edRFC.setEnabled(habilitar);
        edTipoVendedor.setEnabled(habilitar);

        if(habilitar){
            textInputLayoutEmpresa.setAlpha(alphaEnabledValue);

            textInputLayoutDomicilio.setAlpha(alphaEnabledValue);

            textInputLayoutNumero.setAlpha(alphaEnabledValue);

            textInputLayoutCodigoPostal.setAlpha(alphaEnabledValue);

            textInputLayoutColonia.setAlpha(alphaEnabledValue);

            textInputLayoutRFC.setAlpha(alphaEnabledValue);

            textInputLayoutEmpresa.setAlpha(alphaEnabledValue);

            textInputLayoutTipoVendedor.setAlpha(alphaEnabledValue);

            lblIconTipoVendedor.setAlpha(alphaEnabledValue);
        }else{
            textInputLayoutEmpresa.setAlpha(alphaDisabledValue);

            textInputLayoutDomicilio.setAlpha(alphaDisabledValue);

            textInputLayoutNumero.setAlpha(alphaDisabledValue);

            textInputLayoutCodigoPostal.setAlpha(alphaDisabledValue);

            textInputLayoutColonia.setAlpha(alphaDisabledValue);

            textInputLayoutRFC.setAlpha(alphaDisabledValue);

            textInputLayoutEmpresa.setAlpha(alphaDisabledValue);

            textInputLayoutTipoVendedor.setAlpha(alphaDisabledValue);

            lblIconTipoVendedor.setAlpha(alphaDisabledValue);
        }

    }


    private void habilitarSeccionCajasAlmacenImpresora(boolean habilitar){

        if(habilitar){
            lblHintCajas.setEnabled(habilitar);
            lblHintCajas.setAlpha(alphaEnabledValue);
            spinCajas.setAlpha(alphaEnabledValue);
            spinCajas.setClickable(habilitar);

            lblHintAlmacenMercancia.setEnabled(habilitar);
            lblHintAlmacenMercancia.setAlpha(alphaEnabledValue);
            spinAlmacenMercancia.setAlpha(alphaEnabledValue);
            spinAlmacenMercancia.setClickable(habilitar);

            lblHintAlmacenMateriaPrima.setEnabled(habilitar);
            lblHintAlmacenMateriaPrima.setAlpha(alphaEnabledValue);
            spinAlmacenMateriaPrima.setAlpha(alphaEnabledValue);
            spinAlmacenMateriaPrima.setClickable(habilitar);

            lblHintImpresora.setEnabled(habilitar);
            lblHintImpresora.setAlpha(alphaEnabledValue);
            spinImpresora.setAlpha(alphaEnabledValue);
            spinImpresora.setClickable(habilitar);


        }else{
            lblHintCajas.setEnabled(habilitar);
            lblHintCajas.setAlpha(alphaDisabledValue);
            spinCajas.setAlpha(alphaDisabledValue);
            spinCajas.setClickable(habilitar);

            lblHintAlmacenMercancia.setEnabled(habilitar);
            lblHintAlmacenMercancia.setAlpha(alphaDisabledValue);
            spinAlmacenMercancia.setAlpha(alphaDisabledValue);
            spinAlmacenMercancia.setClickable(habilitar);

            lblHintAlmacenMateriaPrima.setEnabled(habilitar);
            lblHintAlmacenMateriaPrima.setAlpha(alphaDisabledValue);
            spinAlmacenMateriaPrima.setAlpha(alphaDisabledValue);
            spinAlmacenMateriaPrima.setClickable(habilitar);

            lblHintImpresora.setEnabled(habilitar);
            lblHintImpresora.setAlpha(alphaDisabledValue);
            spinImpresora.setAlpha(alphaDisabledValue);
            spinImpresora.setClickable(habilitar);

        }
    }


    private void habilitarModeloImpresora(boolean habilitar){

        lblHintModeloImpresora.setEnabled(habilitar);
        spinModeloImpresora.setClickable(habilitar);
        if(habilitar){

            lblHintModeloImpresora.setAlpha(alphaEnabledValue);
            spinModeloImpresora.setAlpha(alphaEnabledValue);

        }else{
            lblHintModeloImpresora.setAlpha(alphaDisabledValue);
            spinModeloImpresora.setAlpha(alphaDisabledValue);
        }

    }

    private void habilitarValidarServiciosTerminal(boolean habilitar){

    }

    private void habilitarSolicitarTerminal(boolean habilitar){

    }

    private void habilitarSeccionInformacionTerminal(boolean habilitar){

    }

    public void fillComboCajasAlmacen(EnumTipoPeticionAsyncTask peticion){
        //if (txttipoVendedor.getText() != null && !txttipoVendedor.getText().toString().equals( "" )) {
        serviceServicioPrincipal = new WebService( edEndpointServiciosPrincipales.getText().toString().trim() );

        switch (peticion){
            case ALMACEN_MERCANCIA:
                loadingTask = new LoadingTask( serviceServicioPrincipal, ConfiguracionActivity.this, EnumTipoPeticionAsyncTask.ALMACEN_MERCANCIA,getString(R.string.msj_obteniendo_almacenes),null,ConfiguracionActivity.this,true);
                loadingTask.execute();
                break;
            case ALMACEN_MATERIA_PRIMA:
                loadingTask = new LoadingTask( serviceServicioPrincipal, ConfiguracionActivity.this, EnumTipoPeticionAsyncTask.ALMACEN_MATERIA_PRIMA,getString(R.string.msj_obteniendo_almacenes),null,ConfiguracionActivity.this,true);
                loadingTask.execute();
                break;
            case CAJAS:
                loadingTask = new LoadingTask( serviceServicioPrincipal, ConfiguracionActivity.this,EnumTipoPeticionAsyncTask.CAJAS ,getString(R.string.msj_obteniendo_cajas),null,ConfiguracionActivity.this,true);
                loadingTask.execute();
                break;
        }




//        }else{
//            MensajeClass.toast( getApplicationContext(), getString(R.string.msjTipoVendedorNoSeleccionado) ).show();
//        }
    }




    private void guardarConfiguracion(){
        DatosRegresados ValidarDatos = validarDatos();

        if (!ValidarDatos.getError()) {

            Almacen almacenMercanciaseleccionado = (Almacen) spinAlmacenMercancia.getSelectedItem();
            Almacen almacenMateriaPrimaSeleccionado = (Almacen) spinAlmacenMateriaPrima.getSelectedItem();
            Caja cajaSeleccionada = (Caja) spinCajas.getSelectedItem();
            EnumModeloImpresora objModeloImpresoraSeleccionado = (EnumModeloImpresora) spinModeloImpresora.getSelectedItem();
            Impresora impresoraSeleccioanda = (Impresora) spinImpresora.getSelectedItem();
            Pinpad pinpadSeleccionado = (Pinpad)spinPinpad.getSelectedItem();
            Caja cajaSeleccioanda = (Caja)spinCajas.getSelectedItem();

//            String campos = EnumColumnasTablaEmpresa.TABLA_EMPRESA_ID.getClaveColumna()+","
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_ENDPOINT_SERVICIO_PRINCIPAL.getClaveColumna()+"," /*0*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_RFC.getClaveColumna()+"," /*1*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_NOMBRE_EMPRESA.getClaveColumna()+"," /*2*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_DOMICILIO.getClaveColumna()+"," /*3*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_NUMERO.getClaveColumna()+"," /*4*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_COLONIA.getClaveColumna()+"," /*5*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_CP.getClaveColumna()+"," /*6*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_TIPO_VENDEDOR.getClaveColumna()+"," /*7*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_ALMACEN_MERCANCIA.getClaveColumna()+"," /*8*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_ALMACEN_MATERIA_PRIMA.getClaveColumna()+"," /*9*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_CAJA.getClaveColumna()+"," /*10*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_IMPRESORA.getClaveColumna()+"," /*11*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_MODELO_IMPRESORA.getClaveColumna()+"," /*12*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_ENDPOINT_SERVICIO_PAGO_TARJETA.getClaveColumna()+"," /*13*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_TERMINAL.getClaveColumna()+"," /*14*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_NUMERO_SERIE_PINPAD.getClaveColumna()+"," /*15*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_DIRECCION_BT_PINPAD.getClaveColumna()+"," /*16*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_PROVEEDOR_PAGO_TARJETA.getClaveColumna()+"," /*17*/
//                    +EnumColumnasTablaEmpresa.TABLA_EMPRESA_CORREO_DEFAULT_PAGO_TARJETA.getClaveColumna(); /*18*/


            List<String> params = new ArrayList<>();
            params.add(0,edEndpointServiciosPrincipales.getText().toString() );
            params.add(1,edRFC.getText().toString() );
            params.add(2,edEmpresa.getText().toString());
            params.add(3,edDom.getText().toString() );
            params.add(4,edNum.getText().toString() );
            params.add(5,edColonia.getText().toString() );
            params.add(6,edCP.getText().toString() );
            params.add(7,edTipoVendedor.getText().toString());
            params.add(8,cajaSeleccionada.getCodigo());
            params.add(9,almacenMercanciaseleccionado.getId());
            params.add(10,almacenMateriaPrimaSeleccionado.getId());
            params.add(11,impresoraSeleccioanda.getMacAddress());
            params.add(12,String.valueOf(objModeloImpresoraSeleccionado.getIdModelo()));
            params.add(13,edEndpointServiciosPagoTarjeta.getText().toString());
            params.add(14,edTerminal.getText().toString());
            params.add(15,edNoSerie.getText().toString());
            params.add(16,/*pinpadSeleccionado.getMacAddress()*/"");
            params.add(17, proveedorPagoTarjeta ==null?"": proveedorPagoTarjeta);
            params.add(18,edCorreoElectronicoPagoTarjeta.getText().toString());

            try{
                dbLocalManager = new DbLocalManager( ConfiguracionActivity.this, getString(R.string.database_name), null, new Metodos().version );
                dbLocalManager.Save( params, configuracion==null?0:configuracion.getId(), EnumTablas.TABLA_CONFIGURACION_EMPRESA );
                finalizarActividad(StaticVariables.RESULT_ACTIVITY.CERRAR_SESION);
            }catch (Exception ex){
                Toast.makeText(ConfiguracionActivity.this, ex.getMessage(),Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText( getApplicationContext(), ValidarDatos.getMsjError(),Toast.LENGTH_LONG).show();
        }
    }

    private void cargarConfiguracion(){
        CargarConfiguracion cargarConfiguracion = new CargarConfiguracion(ConfiguracionActivity.this,ConfiguracionActivity.this);
        cargarConfiguracion.execute();
    }

    private void configureToolbar() {

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

    }


    private void configureNavigationDrawer(){

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        toggle.syncState();

        menuNavigationView = navigationView.getMenu();

        MenuItem nav_actual_activity = menuNavigationView.findItem(R.id.nav_actual_activity);
        nav_actual_activity.setTitle(getString(R.string.nav_drawer_aplicacion_actual_configuracion));
        nav_actual_activity.setIcon(R.drawable.ic__configuracion);


        setSelectedItemNavigationView(R.id.nav_actual_activity);


        navigationView.setNavigationItemSelectedListener(this);

    }


    public boolean getEstatusServicioPagoTerminal() {
        return valServicioPagoTerminal;
    }

    public void updateEsatusServicioPagoTerminal(boolean valor) {
        this.valServicioPagoTerminal = valor;
    }


    public void llenarPinpad(){
        lstPinpad = new ArrayList<Pinpad>();
        Pinpad pinpad;

        try {

            lstPinpad.add(0,hintPinpad);

            PclUtilities var3 = new PclUtilities(ConfiguracionActivity.this, ConfiguracionActivity.this.getPackageName(), "pairing_addr.txt");
            Iterator var4 = var3.GetPairedCompanions().iterator();

            while(var4.hasNext()) {
                PclUtilities.BluetoothCompanion var5 = (PclUtilities.BluetoothCompanion)var4.next();
                pinpad = new Pinpad(var5.getBluetoothDevice().getAddress(),var5.getName());
                lstPinpad.add(pinpad);
            }


            //Aqui se llena el combo

            AdapterPinpad adapterPinpad = new AdapterPinpad(lstPinpad,ConfiguracionActivity.this);

            spinPinpad.setAdapter(adapterPinpad);

        }
        catch (Exception e) {
            lstPinpad = new ArrayList<Pinpad>();
            lstPinpad.add(0,hintPinpad);
            AdapterPinpad adapterPinpad = new AdapterPinpad(lstPinpad,ConfiguracionActivity.this);
            spinPinpad.setAdapter(adapterPinpad);

            Toast.makeText( getApplicationContext(), getString(R.string.msj_error_llenado_pinpad),Toast.LENGTH_LONG ).show();
        }

    }

//    public void showPopup(final TipoModal tipoModal, final DatosRegresados datosRegresados){
//        //instacia un nuevo dialogo
//        popup = new Dialog(ConfiguracionActivity.this);
//        //comando que indica que no tendra titulo default
//        popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //selecciona el layout que sera la vista del popup
//        popup.setContentView(R.layout.alerta_custom);
//
//
//        imgIconMsjError = (ImageView) popup.findViewById(R.id.imgIconMsj);
//        lblTituloAlertCustom = (TextView) popup.findViewById(R.id.lblTituloAlertCustom);
//        lblMsjAlertCustom = (TextView) popup.findViewById(R.id.lblMsjAlertCustom);
//
//        //Obtiene el boton de Aceptar del popup
//        btnAceptarPopup = (Button) popup.findViewById(R.id.btnAceptarPopup);
//
//        //Obtiene el boton de Cancelar del popup
//        btnCancelarPopup = (Button) popup.findViewById(R.id.btnCancelarPopup);
//
//        //Configuracion de icono a mostrar en el dialog
//        switch (tipoModal){
//
//            case ERROR_OPERACION:{
//                //Obtiene el icono que se pintara en el dialogo
//                imgIconMsjError.setImageResource(R.drawable.ic_error);
//                break;
//            }
//
//        }
//        //Configuracion de icono a mostrar en el dialog
//
//        //Configuracion del titulo a mostrar en el error
//        switch (tipoModal){
//            case OPERACION_EXITOSA: case INICIALIZACION_LLAVES_CORRECTA:{
//                lblTituloAlertCustom.setText(getString(R.string.msj_titulo_alerta_operacion_exitosa));
//                break;
//            }
//            case ERROR_OPERACION:{
//                lblTituloAlertCustom.setText(getString(R.string.msj_titulo_alerta_error_operacion));
//                break;
//            }
//
//        }
//        //Configuracion del titulo a mostrar en el error
//
//        //Configuracion del mensaje descriptivo de la alerta
//        switch (tipoModal){
//            case ERROR_OPERACION:{
//                //setea el mensaje del error que esta presentando
//                //lblMsjAlertCustom.setText("La impresora se encuentra apagada o fuera del alcance, por favor revise el dispositivo.");
//                lblMsjAlertCustom.setText(datosRegresados.getMsjError());
//                break;
//            }
//
//            case OPERACION_EXITOSA:{
//                lblMsjAlertCustom.setVisibility(View.GONE);
//                break;
//            }
//
//        }
//        //Configuracion del mensaje descriptivo de la alerta
//
//
//        switch (tipoModal){
//            case ERROR_OPERACION:{
//                //Setea el backgruond que tendra el botton
//                btnAceptarPopup.setBackgroundResource(R.drawable.boton_error_radius);
//                //Setea el style que corresponde al texto de exte boton
//                btnAceptarPopup.setTextAppearance(ConfiguracionActivity.this,R.style.btnErrorAceptarAlert);
//                //
//                btnAceptarPopup.setText(getString(R.string.msj_boton_aceptar));
//                break;
//            }
//        }
//
//        //Genera el evento onclick
//        btnAceptarPopup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                popup.dismiss();
//
//                switch (tipoModal){
//                    case ERROR_OPERACION:{
//                        break;
//                    }
//                }
//
//            }
//        });
//
//
//        switch (tipoModal){
//            case ERROR_OPERACION:{
//                btnCancelarPopup.setVisibility(View.GONE);
//                break;
//            }
//        }
//
//        //Genera el evento onclick
//        btnCancelarPopup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                popup.dismiss();
//
//                switch (tipoModal){
//
//                }
//
//            }
//        });
//
//        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        popup.setCancelable(false);
//
//        popup.show();
//
//    }
//    public void setConfiguracion(Configuracion config) {
//        /*Datos de la empresa*/
//        edEmpresa.setText( config.getEmpresa());
//        edRFC.setText( config.getRfc());
//        edNum.setText( config.getNumero());
//        edCP.setText( config.getCp());
//        edDom.setText( config.getDomicilio());
//        edColonia.setText( config.getColonia() );
//        edTerminal.setText(config.getTerminal());
//        edNoSerie.setText(config.getNumeroSeriePinpad());
//        edEndpointServiciosPrincipales.setText(config.getEndPointServicioPrincipal() );
//        edEndpointServiciosPagoTarjeta.setText(config.getEndPointServicioPagoTerminal());
//        edTerminal.setText(config.getTerminal());
//        edNoSerie.setText(config.getNumeroSeriePinpad());
//        spinImpresora = findViewById(R.id.spinImpresora);
//        spinPinpad = findViewById(R.id.spinPinpad);
//        serviceServicioPrincipal = new WebService( edEndpointServiciosPrincipales.getText().toString() );
//        serviceServiciosTerminal = new WebService(edEndpointServiciosPagoTarjeta.getText().toString());
//        edCorreoElectronicoPagoTarjeta.setText(config.getCorreoElectronicoPagoTarjeta());
//
//
//    }


    public void setImpresora(Impresora impresora) {

        int index = lstImpresora.indexOf(impresora);

        spinImpresora.setSelection(index);

//            if(index>0){
//                lblHintImpresora.setVisibility(View.VISIBLE);
//            }

    }

    public void setModeloImpresora(EnumModeloImpresora enumModeloImpresora) {


        int index = lstModeloImpresora.indexOf(enumModeloImpresora);


        spinModeloImpresora.setSelection(index);
//            if(index>0){
//                lblHintModeloImpresora.setVisibility(View.VISIBLE);
//            }else{
//                lblHintModeloImpresora.setVisibility(View.GONE);
//            }

    }

    public void setAlmacen(Almacen almacen,EnumTipoPeticionAsyncTask enumTipoPeticionAsyncTask) {

        switch (enumTipoPeticionAsyncTask){
            case ALMACEN_MERCANCIA:
                int indexMercancia = lstAlmacenesMercancia.indexOf(almacen);
                spinAlmacenMercancia.setSelection(indexMercancia);

                break;
            case ALMACEN_MATERIA_PRIMA:
                int indexMateriaPrima = lstAlmacenesMateriaPrima.indexOf(almacen);
                spinAlmacenMateriaPrima.setSelection(indexMateriaPrima);

                break;
        }


    }

    public void setCaja(Caja caja) {
        int index = lstCajas.indexOf(caja);

        if(index>0){
            spinCajas.setSelection(index);
        }

    }

    public void setPinpad(Pinpad pinpad) {

        int index = lstPinpad.indexOf(pinpad);

        if(index>0){
            spinPinpad.setSelection(index);

//            if(index>0){
//                lblHintPinpad.setVisibility(View.VISIBLE);
//            }
        }

    }



    public void resetConfiguracionServidor(){
//        spincajas.setSelection(0);
//        spinAlmacenes.setSelection(0);
        //lyContenedorConfiguracionServidor.setVisibility(View.GONE);
    }

    public void resetImpresora(){
        spinImpresora.setSelection(0);
        spinModeloImpresora.setSelection(0);
    }

    public void resetModalidad(){
        spinImpresora.setSelection(0);
        //lyContenedorConfiguracionModaliadad.setVisibility(View.GONE);
    }

    public void resetTerminal(){

        spinPinpad.setSelection(0);
        //edPuntoVenta.setText("");
        puntoVenta = "";
        edTerminal.setText("");
        edNoSerie.setText("");
        edClaveUsuario.setText("");
        edEndpointServiciosPagoTarjeta.setText("");
        edCorreoElectronicoPagoTarjeta.setText("");

    }


    public DatosRegresados validarDatos(){
        DatosRegresados datosRegresados = new DatosRegresados();
        datosRegresados.setError(false);
        String msjError = "";

        if(!edEndpointServiciosPrincipales.getText().toString().trim().equals("") && !valServicioPrincipalExitoso){
            datosRegresados.setError(true);
            if(msjError.equals("")){
                msjError += getString(R.string.msj_error_url_principal);
            }else{
                msjError += ", "+getString(R.string.msj_error_url_principal).toLowerCase();
            }
        }

        if(!validarTipoVendedor()){
            datosRegresados.setError(true);
            if(msjError.equals("")){
                msjError += getString(R.string.msj_error_tipo_vendedor);
            }else{
                msjError += ", "+getString(R.string.msj_error_tipo_vendedor).toLowerCase();
            }
        }

        if(spinCajas.getSelectedItemPosition()<=0){
            datosRegresados.setError(true);
            if(msjError.equals("")){
                msjError += getString(R.string.msj_error_caja);
            }else{
                msjError += ", "+getString(R.string.msj_error_caja).toLowerCase();
            }
        }

        if(spinAlmacenMercancia.getSelectedItemPosition()<=0){
            datosRegresados.setError(true);
            if(msjError.equals("")){
                msjError += getString(R.string.msj_error_almacen_mercancia);
            }else{
                msjError += ", "+getString(R.string.msj_error_almacen_mercancia).toLowerCase();
            }
        }

        if(spinAlmacenMateriaPrima.getSelectedItemPosition()<=0){
            datosRegresados.setError(true);
            if(msjError.equals("")){
                msjError += getString(R.string.msj_error_almacen_materia_prima);
            }else{
                msjError += ", "+getString(R.string.msj_error_almacen_materia_prima).toLowerCase();
            }
        }

//        if(spinImpresora.getSelectedItemPosition()<=0){
//            datosRegresados.setError(true);
//            if(msjError.equals("")){
//                msjError += "Debe de seleccionar la impresora";
//            }else{
//                msjError += ", Debe de seleccionar la impresora";
//            }
//        }
//
//        if(spinModeloImpresora.getSelectedItemPosition()<=0){
//            datosRegresados.setError(true);
//            if(msjError.equals("")){
//                msjError += "Debe de seleccionar el modelo de la impresora";
//            }else{
//                msjError += ", Debe de seleccionar el modelo de la impresora";
//            }
//        }

//        if(edEndpointServiciosPagoTarjeta.getText().toString().trim().equals("")){
//            datosRegresados.setError(true);
//            if(msjError.equals("")){
//                msjError += "Debe de ingresar la url para el guardado de movimientos";
//            }else{
//                msjError += ", Debe de ingresar la url para el guardado de movimientos";
//            }
//        }
//
//        if(edTerminal.getText().toString().trim().equals("")){
//            datosRegresados.setError(true);
//            if(msjError.equals("")){
//                msjError += "Debe de ingresar la terminal";
//            }else{
//                msjError += ", Debe de ingresar la terminal";
//            }
//        }
//        if(spinPinpad.getSelectedItemPosition()<=0){
//            datosRegresados.setError(true);
//            if(msjError.equals("")){
//                msjError += "Debe de seleccionar la pinpad";
//            }else{
//                msjError += ", Debe de seleccionar la pinpad";
//            }
//        }

        datosRegresados.setMsjError(msjError);

        return datosRegresados;
    }

    public boolean validarTipoVendedor(){
        if(!lblIconTipoVendedor.getText().toString().trim().equals("") && !valTipoVendedor){
            return false;
        }else {
            return true;
        }
    }

//    //Un puente a transformacion de los datos de la terminal
//    public void resultadoDatosTerminal(DatosRegresados resultado){
//
//        if (!resultado.getError()) {
//
//            lblIconDatosTerminal.setTextColor(getResources().getColor(R.color.colorSuccess));
//            edClaveUsuario.setText(terminal.getCliente());
//            edNoSerie.setText(terminal.getSerie());
//            proveedorPagoTarjeta = terminal.getProveedor();
//
//        } else {
//
//            edClaveUsuario.setText("");
//            edNoSerie.setText("");
//            proveedorPagoTarjeta = "";
//            lblIconDatosTerminal.setTextColor(getResources().getColor(R.color.colorError));
//            showPopup(TipoModal.ERROR_OPERACION, resultado);
//        }
//    }


    public void llenarAlmacen(List<Almacen> lstAlmacen, EnumTipoPeticionAsyncTask enumTipoPeticionAsyncTask) {

        switch (enumTipoPeticionAsyncTask){
            case ALMACEN_MERCANCIA:
                lstAlmacenesMercancia = new ArrayList<>(lstAlmacen);

                lstAlmacenesMercancia.add(0, hintAlmacenMercancia);
                adapterAlmacenMercancia = new AdapterAlmacen(lstAlmacenesMercancia, getApplicationContext() );
                spinAlmacenMercancia.setAdapter(adapterAlmacenMercancia);

                if(configuracion!=null){
                    Almacen almacenSurtido = new Almacen(configuracion.getAlmacenMercancia(),"");
                    setAlmacen(almacenSurtido,enumTipoPeticionAsyncTask);
                }else{
                    setAlmacen(hintAlmacenMercancia,enumTipoPeticionAsyncTask);
                }

                break;
            case ALMACEN_MATERIA_PRIMA:
                lstAlmacenesMateriaPrima = new ArrayList<>(lstAlmacen);

                lstAlmacenesMateriaPrima.add(0, hintAlmacenMateriaPrima);
                adapterAlmacenMateriaPrima= new AdapterAlmacen(lstAlmacenesMateriaPrima, getApplicationContext() );
                spinAlmacenMateriaPrima.setAdapter(adapterAlmacenMateriaPrima);

                if(configuracion!=null){
                    Almacen almacenSurtido = new Almacen(configuracion.getAlmacenMateriaPrima(),"");
                    setAlmacen(almacenSurtido,enumTipoPeticionAsyncTask);
                }else{
                    setAlmacen(hintAlmacenMateriaPrima,enumTipoPeticionAsyncTask);
                }

                break;
        }

    }

    public void llenarCajas(List<Caja> lstCajastepm) {
        this.lstCajas = new ArrayList<Caja>(lstCajastepm);
        this.lstCajas.add(0,hintCaja);

        final AdapterCaja dataCaja = new AdapterCaja( lstCajas, getApplicationContext() );
        spinCajas.setAdapter( dataCaja );
        if(configuracion!=null){
            Caja caja = new Caja(configuracion.getCaja(),"");
            setCaja(caja);
        }else{
            setCaja(hintCaja);
        }

    }

    private void alertCerrarSesion(){
        final CustomAlert alert = new CustomAlert(ConfiguracionActivity.this,TipoModal.CERRAR_SESION,null);
        alert.setAceptarClickListener(new CustomAlert.AceptarListener() {
            @Override
            public void OnClickAceptar(TipoModal tipoModal) {
                alert.dismiss();
                cerrarSesion();
            }
        },"");

        alert.setCancelarClickListener(new CustomAlert.CancelarListener() {
            @Override
            public void OnClickCancelar(TipoModal tipoModal) {
                setSelectedItemNavigationView(R.id.nav_actual_activity);
                alert.dismiss();
            }
        },"");
        alert.show();
    }

    private void cerrarSesion(){
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE).edit();
        editor.putString(getString(R.string.SHPTAGUser),"");
        editor.commit();
        finalizarActividad(StaticVariables.RESULT_ACTIVITY.CERRAR_SESION);
    }


    private void llamarActividad(int requestActivity){
        Intent i;
        int Parent = StaticVariables.REQUEST.MESA_ACTIVITY;

        switch (requestActivity){
            case StaticVariables.REQUEST.ACERCA_DE_ACTIVITY:
                i = new Intent(ConfiguracionActivity.this,AcercaDeActivity.class);
                //Dato extra para saber quien hace el llamado
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                //Se envia en el request la actividad que s esta llamando
                startActivityForResult(i, requestActivity);
                break;
            case StaticVariables.REQUEST.CONFIGURACION_ACTIVITY:
                i = new Intent(ConfiguracionActivity.this,ConfiguracionActivity.class);
                //Dato extra para saber quien hace el llamado
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                //Se envia en el request la actividad que s esta llamando
                startActivityForResult(i, requestActivity);
                break;
            case StaticVariables.REQUEST.COMANDA_ACTIVITY:
                break;
        }
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }


    private void finalizarActividad(int resultActivity){
        Intent i;
        switch (resultActivity){
            case StaticVariables.RESULT_ACTIVITY.CERRAR_SESION:case StaticVariables.RESULT_ACTIVITY.CERRAR_SESION_PARENT:{
                if(resultActivity == StaticVariables.RESULT_ACTIVITY.CERRAR_SESION){
                    i = new Intent(ConfiguracionActivity.this,LoginActivity.class);
                    i.putExtra(getString(R.string.dato_extra_intent_request), StaticVariables.REQUEST.OTHER_ACTIVITY);
                    startActivity(i);
                    resultActivity = StaticVariables.RESULT_ACTIVITY.CERRAR_SESION_PARENT;
                }

//                i = new Intent();
//                i.putExtra(getString(R.string.dato_extra_intent_tipo_request),RequestAndResultActivity.CERRAR_SESION);
                setResult(resultActivity);
                finish();
            }
        }
    }


}
