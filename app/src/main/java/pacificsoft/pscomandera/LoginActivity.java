package pacificsoft.pscomandera;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.gson.Gson;

import pacificsoft.pscomandera.Enum.Enum;
import pacificsoft.pscomandera.Enum.EnumColumnasTablaEmpresa;
import pacificsoft.pscomandera.Enum.EnumTablas;
import pacificsoft.pscomandera.Enum.EnumTipoPeticionAsyncTask;
import pacificsoft.pscomandera.Enum.StaticVariables;
import pacificsoft.pscomandera.Impresion.Metodos;
import pacificsoft.pscomandera.KSOAP2.WebService;
import pacificsoft.pscomandera.Loading.LoadingTask;
import pacificsoft.pscomandera.Loading.LoadingTaskRemoteService;
import pacificsoft.pscomandera.LocalManager.DbLocalManager;
import pacificsoft.pscomandera.Modelo.Configuracion;
import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Modelo.LoginComandera;
import pacificsoft.pscomandera.Modelo.ResultadoLoginComandera;
import pacificsoft.pscomandera.Modelo.Usuario;
import pacificsoft.pscomandera.Util.CargarConfiguracion;
import pacificsoft.pscomandera.Util.ConvertXmlToJSONManager;
import pacificsoft.pscomandera.serviciosweb.AMTBasicHttpBinding_IService;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoadingTask.LoadingTaskListener, CargarConfiguracion.CargaConfiguracionListener,LoadingTaskRemoteService.LoadingTaskRemoteServiceListener {

    Configuracion config = null;
    DbLocalManager lmanager = null;
    AMTBasicHttpBinding_IService service = null;

    WebService serviceServicioPrincipal, serviceServiciosTerminal;
    LoadingTask loadingTask;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private TextInputEditText mEmailView,mPasswordView;
//    private View mProgressView;
    //    private View mLoginFormView;

//    private ConstraintSet constraintSetDefaultLogin = new ConstraintSet();
//    private ConstraintSet constraintSetLoginAnimation = new ConstraintSet();
//    private ConstraintSet constraintSetLoadingAnimation = new ConstraintSet();

    private TextInputLayout txtIntputLUser;
    private TextInputLayout txtIntputLPassWord;


    private ConstraintLayout constraintLayoutLoginActivity;
//    private InputMethodManager imm;

    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;

    private Configuracion configuracion;

    private boolean LoginALt = false;

    private String username = "";
    private String password = "";

    MaterialRippleLayout mEmailSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Navigation View    ----> https://academiaandroid.com/navigation-drawer-y-clase-navigation-view/
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mEmailSignInButton = (MaterialRippleLayout) findViewById(R.id.email_sign_in_button);

        configureNavigationDrawer();

        configureToolbar();

        constraintLayoutLoginActivity = findViewById(R.id.constraintLoginActivity);

        txtIntputLUser = findViewById(R.id.txtIntputLUser);
        txtIntputLPassWord = findViewById(R.id.txtIntputLPassWord);



        // Set up the login form.
        mEmailView = (TextInputEditText) findViewById(R.id.email);

        mPasswordView = (TextInputEditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
//                    mEmailSignInButton.setEnabled(false);
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });




        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                mEmailSignInButton.setEnabled(false);

                attemptLogin();
            }
        });

//        mProgressView = findViewById(R.id.login_progress);

//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        //
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        //----------------------Toolbar and navigationView -----------------//


        if(getIntent().getExtras()==null){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }

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

        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        menuItem.setChecked(true);

        final int result = 1;

        switch (id){

//            case R.id.nav_mas_aplicaciones:
//
//                Intent masAplicacionesActivityIntent = new Intent(this, MasAplicacionesActivity.class);
//                startActivityForResult(masAplicacionesActivityIntent,result);
//
//                break;
//            case R.id.nav_contactanos:
//                break;
            case R.id.nav_acerca_de:
                Intent loginActivityIntent = new Intent(this,AcercaDeActivity.class);
                startActivityForResult(loginActivityIntent,result);

                break;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
//            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onPostResume() {
        //CargarConfiguracion cargarConfiguracion = new CargarConfiguracion(this,LoginActivity.this);
        //cargarConfiguracion.execute();

        try {

            lmanager = new DbLocalManager(LoginActivity.this, getString(R.string.database_name), null, new Metodos().version);
            configuracion = getConfigData();

            if (configuracion != null && configuracion.getEndPointServicioPrincipal() != null && !configuracion.getEndPointServicioPrincipal().equals("")) {
                serviceServicioPrincipal = new WebService( configuracion.getEndPointServicioPrincipal().toString());
                loadingTask = new LoadingTask(serviceServicioPrincipal, LoginActivity.this, EnumTipoPeticionAsyncTask.VALIDARSERVICIOPRINCIPAL, getString(R.string.msj_validando_servicio),null,LoginActivity.this,true);
                loadingTask.execute();
            } else {
                Intent ix = new Intent(LoginActivity.this, ConfiguracionActivity.class);
                startActivity(ix);
                finish();
            }
        }
        catch (Exception Ex){
            //Toast.makeText(LoginActivity.this,"Error Login",Toast.LENGTH_SHORT).show();
            Toast.makeText(LoginActivity.this,Ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

        super.onPostResume();
    }

    public Configuracion getConfigData() {
        Enum enumList = new Enum();
        lmanager.setNAME_TABLA( EnumTablas.TABLA_CONFIGURACION_EMPRESA );
        Cursor c = lmanager.getData();
        if (c.getCount() > 0 && c.moveToFirst()) {
            configuracion = new Configuracion();
            configuracion.setId(c.getInt( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_ID.getClaveColumna()) ));
            configuracion.setEndPointServicioPrincipal(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_ENDPOINT_SERVICIO_PRINCIPAL.getClaveColumna()) ));
            configuracion.setRfc(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_RFC.getClaveColumna()) ));
            configuracion.setEmpresa(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_NOMBRE_EMPRESA.getClaveColumna()) ));
            configuracion.setDomicilio(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_DOMICILIO.getClaveColumna()) ));
            configuracion.setNumero(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_NUMERO.getClaveColumna()) ));
            configuracion.setColonia(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_COLONIA.getClaveColumna()) ));
            configuracion.setCp(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_CP.getClaveColumna()) ));
            configuracion.setTipovendedor(c.getInt( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_TIPO_VENDEDOR.getClaveColumna()) ));
            configuracion.setCaja(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_CAJA.getClaveColumna()) ));
            configuracion.setAlmacenMateriaPrima(c.getString(c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_ALMACEN_MATERIA_PRIMA.getClaveColumna())));
            configuracion.setAlmacenMercancia(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_ALMACEN_MERCANCIA.getClaveColumna()) ));
            configuracion.setImpresora(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_IMPRESORA.getClaveColumna()) ));
            configuracion.setModeloImpresora(enumList.getModeloImpresoraById(c.getInt( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_MODELO_IMPRESORA.getClaveColumna()))));
            configuracion.setEndPointServicioPagoTerminal(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_ENDPOINT_SERVICIO_PAGO_TARJETA.getClaveColumna()) ));
            configuracion.setTerminal(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_TERMINAL.getClaveColumna()) ));
            configuracion.setNumeroSeriePinpad(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_NUMERO_SERIE_PINPAD.getClaveColumna()) ));
            configuracion.setDireccionBTPinpad(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_DIRECCION_BT_PINPAD.getClaveColumna()) ));
            configuracion.setProveedor(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_PROVEEDOR_PAGO_TARJETA.getClaveColumna()) ));
            configuracion.setCorreoElectronicoPagoTarjeta(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_CORREO_DEFAULT_PAGO_TARJETA.getClaveColumna()) ));
        }
        return configuracion;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onLoadedDataLoadingTask(EnumTipoPeticionAsyncTask peticion, DatosRegresados datosregresados) {
        switch (peticion){
            case LOGIN_COMANDERA:

                    resultLogin(datosregresados);

//                ValidarResultadoLogin validarResultadoLogin = new ValidarResultadoLogin(datosregresados);
//
//                validarResultadoLogin.execute();

                break;
        }
    }

    @Override
    public void cargaConfiguracionCompleta(DatosRegresados datosregresados) {
        if(!datosregresados.getError()){
            configuracion = Configuracion.class.cast(datosregresados.getDatosRegresados());
        }else{

        }
    }

//    private void showLoginAltAnimation(){
//
//        ChangeBounds changeBounds = new ChangeBounds();
//
//        changeBounds.setInterpolator(new AccelerateInterpolator());
//
//        changeBounds.setDuration(400);
//
//        TransitionManager.beginDelayedTransition(constraintLayoutLoginActivity,changeBounds);
//
//        constraintSetLoginAnimation.applyTo(constraintLayoutLoginActivity);
//
//    }
//
//    private void hideLoginAltAnimation(){
//
//        ChangeBounds changeBounds = new ChangeBounds();
//
//        changeBounds.setInterpolator(new AccelerateInterpolator());
//
//        changeBounds.setDuration(400);
//
//        TransitionManager.beginDelayedTransition(constraintLayoutLoginActivity,changeBounds);
//
//        constraintSetDefaultLogin.applyTo(constraintLayoutLoginActivity);
//
//    }
//
//    private void showLoadingAnimation(){
//        ChangeBounds changeBounds = new ChangeBounds();
//
//
//        changeBounds.setInterpolator(new AccelerateInterpolator());
//
//        changeBounds.setDuration(400);
//
//        TransitionManager.beginDelayedTransition(constraintLayoutLoginActivity,changeBounds);
//
//        constraintSetLoadingAnimation.applyTo(constraintLayoutLoginActivity);
//    }
//
//    private void hideLoadingAnimation(){
//        ChangeBounds changeBounds = new ChangeBounds();
//
//
//        changeBounds.setInterpolator(new AccelerateInterpolator());
//
//        changeBounds.setDuration(400);
//
//        TransitionManager.beginDelayedTransition(constraintLayoutLoginActivity,changeBounds);
//
//        constraintSetLoginAnimation.applyTo(constraintLayoutLoginActivity);
//    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        mEmailSignInButton.setEnabled(false);

//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        username = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
//        else if (!isEmailValid(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            mEmailSignInButton.setEnabled(true);
        } else {
//            showProgress(true);

            if(configuracion!=null && configuracion.getEndPointServicioPrincipal()!=null && !configuracion.getEndPointServicioPrincipal().equals("")){

                LoginComandera loginComandera = new LoginComandera(username,password,configuracion.getCaja(),String.valueOf(configuracion.getTipovendedor()));

                WebService webService = new WebService(configuracion.getEndPointServicioPrincipal());

                LoadingTask loadingTask = new LoadingTask(webService,LoginActivity.this,EnumTipoPeticionAsyncTask.LOGIN_COMANDERA,getString(R.string.msj_inciando_sesion),loginComandera,this,true);
                loadingTask.execute();

            }else{
                mEmailSignInButton.setEnabled(true);
                llamarActividad(StaticVariables.REQUEST.CONFIGURACION_ACTIVITY);

            }
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
        }
    }


    public void resultLogin(DatosRegresados datosRegresados){

        if(!datosRegresados.getError()){

            ResultadoLoginComandera resultadoLoginComandera = ResultadoLoginComandera.class.cast(datosRegresados.getDatosRegresados());

            switch (resultadoLoginComandera.getId()){
                case "0":{

                    Usuario usuario = resultadoLoginComandera.getUsuario();

                    usuario.setCodigo(username);

                    String usuarioString = new Gson().toJson(usuario);

//                    hideLoginAltAnimation();

                    SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE).edit();
                    editor.putString(getString(R.string.SHPTAGUser),usuarioString);
                    editor.commit();

                    switch (usuario.getTipo()){
                        case "0":case "1":{
                            llamarActividad(StaticVariables.REQUEST.CONFIGURACION_ACTIVITY);
                            //llamarActividad(StaticVariables.REQUEST.CONFIGURACION_ACTIVITY);
                            break;
                        }
                        default:{
                            llamarActividad(StaticVariables.REQUEST.AREA_ACTIVITY);

                            break;
                        }
                    }

                    break;
                }
                case "1":{

                    mEmailView.requestFocus();
                    Toast.makeText(LoginActivity.this,resultadoLoginComandera.getDescripcion(),Toast.LENGTH_LONG).show();
                    break;
                }

                case "2":{
                    mEmailView.setError(resultadoLoginComandera.getDescripcion());
                    mEmailView.requestFocus();
                    mPasswordView.setText("");

                    break;
                }

                case "3":{
                    mPasswordView.setError(resultadoLoginComandera.getDescripcion());
                    mPasswordView.requestFocus();
                    break;
                }

            }

        } else {
            llamarActividad(StaticVariables.REQUEST.CONFIGURACION_ACTIVITY);

            Toast.makeText(LoginActivity.this,Exception.class.cast(datosRegresados.getDatosRegresados()).getMessage(),Toast.LENGTH_LONG).show();
        }

        mEmailSignInButton.setEnabled(true);

    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

//    /**
//     * Shows the progress UI and hides the login form.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//
//
//            if(show){
//                showLoadingAnimation();
//            }else{
//                hideLoadingAnimation();
//            }
//
//        } else {
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        navigationView.getCheckedItem().setChecked(false);
    }

    private void llamarActividad(int request){

        AlertDialog pd;

        AlertDialog.Builder builder;
        Context mContext = LoginActivity.this;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.progress_dialog_custom,null);

        TextView text = (TextView) layout.findViewById(R.id.txtProgressDialogCustom);
        text.setText(getString(R.string.msj_obtieniendo_datos));

        builder = new AlertDialog.Builder(mContext);
        builder.setView(layout);
        builder.setCancelable(false);
        pd = builder.create();
        pd.show();

        int Parent;

        switch (request){
            case StaticVariables.REQUEST.AREA_ACTIVITY:
                startActivityForResult(new Intent(LoginActivity.this,AreaActivity.class),request);
                finish();
                break;

            case StaticVariables.REQUEST.CONFIGURACION_ACTIVITY:
                startActivityForResult(new Intent(LoginActivity.this,ConfiguracionActivity.class),request);
                finish();
                break;
        }

        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


    }

    @Override
    public void OnLoadingTasnkFinish(DatosRegresados datosRegresados, int peticion) {
        if(!datosRegresados.getError()){
            switch (peticion){
                case LoadingTaskRemoteService.LOGIN:
                    break;
                case LoadingTaskRemoteService.VALIDARCON:{
                    String xmlData = datosRegresados.getDatosRegresadosString();

                    if(xmlData == null || xmlData.equals("")){
                        Intent ix = new Intent( LoginActivity.this, ConfiguracionActivity.class );
                        startActivity( ix );
                        finish();
                    }else{
                        String codigo = ConvertXmlToJSONManager.Convertidor( xmlData, "Codigo", "Respuesta" ).get(0);

                        if(!codigo.equals("0")){
                            Intent ix = new Intent( LoginActivity.this, ConfiguracionActivity.class );
                            startActivity( ix );
                            finish();
                        }
                    }
                }
            }
        }else{
            switch (peticion){
                case LoadingTaskRemoteService.VALIDARCON:{
                    Intent ix = new Intent( LoginActivity.this, ConfiguracionActivity.class );
                    startActivity( ix );
                    finish();
                    break;
                }
            }
        }
    }
}

