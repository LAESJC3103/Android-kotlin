package pacificsoft.pscomandera.Impresion.starBluetooth;

import android.os.AsyncTask;


/**
 * Created by desarrollo ORM on 2/28/2018.
 * Impresion en starIO
 */

public class Imprimir extends AsyncTask<Boolean, Void, Communication.Result> {
    @Override
    protected Communication.Result doInBackground(Boolean... booleans) {
        return null;
    }

    //    private static Metodos met = new Metodos();
//    Metodos meto = new Metodos();
//    private List<PortInfo> mPortList;
//    Context context = null;
//    Activity act = null;
//    Configuracion config;
//    Usuario us;
//    byte[] data = null;
//    String folio = null,propina="",subTotal="",totalPagar="";
//    Total total;
//    byte[] datax = null;
//    PortInfo port;
//   //EnumModalidad modalidad = null;
//    TransResult transaccion;
//    String datosQR;
//    Boolean imprimirTicket ,imprimirVoucher;
//    Util util = new Util();
//    TipoModal tipoModal ;
//    Mesa mesa;
//
//    /***************/
//    int numberCharectersRow = 32;
//    /***************/
//
//    String Resultado = null;
//    soft.pacific.mx.pspagocuenta.PrinterSetting setting = null;
//    soft.pacific.mx.pspagocuenta.impresion.starBluetooth.PrinterSetting settings = null;
//    StarIoExt.Emulation emulation;
//    private StarIoExtManager mStarIoExtManager;
//    private IConnectionCallback callback = new IConnectionCallback() {
//        @Override
//        public void onDisconnected() {
//            mStarIoExtManager.disconnect( callback );
//        }
//
//        @Override
//        public void onConnected(IConnectionCallback.ConnectResult connectResult) {
//            mStarIoExtManager.connect( callback );
//        }
//    };
//
//
//    public byte[] Ticket(String folio, Total total,String propina,String subTotal,String totalPagar, Configuracion config) {
//        //List<String> ResultLista = new ArrayList<>();
//
//
//
//        Date date = new Date();
//        String rowString = "";
//        DateFormat fecha = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
//        DateFormat fechaymd = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
//        CommandDataList sb = new CommandDataList();
//        ColumnDataFormatPrint columnDataFormatPrint = null;
//        List<ColumnDataFormatPrint> columns = new ArrayList<>();
//        sb.add( 0x1b, 0x1d, 0x61, 0x00 );               // Alignment(left)
//        //sb.add( modalidad.getDescripcion().toUpperCase()+"\r\n\n");
//        sb.add( config.getEmpresa().toUpperCase() + "\r\n" );
//        sb.add( config.getDomicilio() + " " + config.getNumero() + "\r\n" );
//        sb.add( "Colonia " + config.getColonia().toUpperCase() + "\r\n" );
//        sb.add( "CP " + config.getCp() + "\r\n" );
//
//        sb.add( "R.F.C. " + config.getRfc() + "\r\n\n" );
//
//        sb.add( "Folio: " + folio + "  " + fecha.format( date ) + "\r\n" );
//
//        // sb.append( "SURTIDOR: " + config.getUsuario() + "\r\n" );
//        sb.add( "--------------------------------" + "\r\n" );
//
////        sb.add( "Cant. " );
////        sb.add( "Descripción    " );
////        sb.add( "Total   \n" );
////        DecimalFormat dformat = new DecimalFormat( "####,####.000" );
//////        this.Resultado = "ENC " +
//////                sp.getCaja() + "," +
//////                fechaymd.format( date ).replace( "/", "" ).replace( ":", "" ) + "," +
//////                folio + "," +
//////                us.getUsuario() +
//////                " FINENC  DETALLE ";
////
////        for (int r = 0; r < lista.size(); r++) {
////            Articulo articulo = lista.get( r );
////            String cantidad = met.formato( articulo.getCantidad() );
////            sb.add( cantidad + "  " );
////            String descripcion = articulo.getDescripcion();
////            sb.add( descripcion + "  " );
////            String str_Total = articulo.getStrImporte().trim();
////            String strtotal = dformat.format( articulo.getImporte() );
////            total += Double.valueOf( articulo.getStrImporte().replace("$","").replace( ",","" ) );
////            sb.add( strtotal + "\n" );
//////            Resultado += ObtenerStrJson( articulo );
////        }
////
//////        Resultado = Resultado.substring( 0, Resultado.length() - image_nav_header_default_comanda ) + " FINDETALLE";
//        sb.add(mesa.getDescripcion()+"\r\n");
//
//
//
//        for (Cuenta cuenta :total.getListasCuenta()){
//            if(cuenta.isChecked()){
//
//                columns = new ArrayList<>();
//
//                columnDataFormatPrint= new ColumnDataFormatPrint(16,cuenta.getCuentaTexto(),image_nav_header_default_comanda);
//
//                columns.add(columnDataFormatPrint);
//                columnDataFormatPrint= new ColumnDataFormatPrint(16,cuenta.getTotal(),3);
//
//                columns.add(columnDataFormatPrint);
//
//                ColumnStructurePrint columnStructurePrint = new ColumnStructurePrint(32,columns);
//
//                rowString =getRowString(columnStructurePrint);
//                sb.add(rowString);
//
//                //sb.add(cuenta.getCuentaTexto()+"            "+cuenta.getTotal()+"\n");
//            }
//        }
//
//        sb.add( "--------------------------------\r\n" );
//        //RowSubtotal
//        columns = new ArrayList<>();
//        columnDataFormatPrint= new ColumnDataFormatPrint(16,"Subtotal:",image_nav_header_default_comanda);
//        columns.add(columnDataFormatPrint);
//        columnDataFormatPrint= new ColumnDataFormatPrint(16,"$" + subTotal,3);
//        columns.add(columnDataFormatPrint);
//
//        ColumnStructurePrint columnStructurePrint = new ColumnStructurePrint(32,columns);
//
//        rowString =getRowString(columnStructurePrint);
//        sb.add(rowString);
//
//        //RowSubtotal
//
//        //RowPropina
//        columns = new ArrayList<>();
//        columnDataFormatPrint= new ColumnDataFormatPrint(16,"Propina:",image_nav_header_default_comanda);
//        columns.add(columnDataFormatPrint);
//        columnDataFormatPrint= new ColumnDataFormatPrint(16,"$" + propina,3);
//        columns.add(columnDataFormatPrint);
//
//        columnStructurePrint = new ColumnStructurePrint(32,columns);
//
//        rowString =getRowString(columnStructurePrint);
//        sb.add(rowString);
//        //RowPropina
//        sb.add( "--------------------------------\r\n" );
//
//        //RowTotal
//        columns = new ArrayList<>();
//        columnDataFormatPrint= new ColumnDataFormatPrint(16,"Total:",image_nav_header_default_comanda);
//        columns.add(columnDataFormatPrint);
//        columnDataFormatPrint= new ColumnDataFormatPrint(16,"$" + totalPagar,3);
//        columns.add(columnDataFormatPrint);
//
//        columnStructurePrint = new ColumnStructurePrint(32,columns);
//
//        rowString =getRowString(columnStructurePrint);
//        sb.add(rowString);
//        //RowTotal
//
//        sb.add( "--------------------------------\r\n\r\n" );
//
//
//
//            //TITULO
//            columns = new ArrayList<>();
//            columnDataFormatPrint= new ColumnDataFormatPrint(32,"PAGO CON TARJETA",2);
//            columns.add(columnDataFormatPrint);
//
//            columnStructurePrint = new ColumnStructurePrint(32,columns);
//
//            rowString =getRowString(columnStructurePrint);
//            sb.add(rowString);
//            //TITULO
//
//            sb.add( "\r\n" );
//
//            //FECHA
//            columns = new ArrayList<>();
//            columnDataFormatPrint= new ColumnDataFormatPrint(12,"FECHA:",image_nav_header_default_comanda);
//            columns.add(columnDataFormatPrint);
//            columnDataFormatPrint= new ColumnDataFormatPrint(20,fecha.format(transaccion.getoRespuesta_iso().getFecha()),image_nav_header_default_comanda);
//            columns.add(columnDataFormatPrint);
//
//            columnStructurePrint = new ColumnStructurePrint(32,columns);
//
//            rowString =getRowString(columnStructurePrint);
//            sb.add(rowString);
//            //FECHA
//
//            //TARJETA
//            columns = new ArrayList<>();
//            columnDataFormatPrint= new ColumnDataFormatPrint(12,"TARJETA:",image_nav_header_default_comanda);
//            columns.add(columnDataFormatPrint);
//            columnDataFormatPrint= new ColumnDataFormatPrint(20,"XXXX-XXXX-XXXX-"+transaccion.getoRespuesta_iso().getTarjeta(),image_nav_header_default_comanda);
//            columns.add(columnDataFormatPrint);
//
//            columnStructurePrint = new ColumnStructurePrint(32,columns);
//
//            rowString =getRowString(columnStructurePrint);
//            sb.add(rowString);
//            //TARJETA
//            //ESTATUS
//            columns = new ArrayList<>();
//            columnDataFormatPrint= new ColumnDataFormatPrint(12,"ESTATUS:",image_nav_header_default_comanda);
//            columns.add(columnDataFormatPrint);
//            columnDataFormatPrint= new ColumnDataFormatPrint(20,transaccion.getoRespuesta_iso().getStatus_comentario().toUpperCase(),image_nav_header_default_comanda);
//            columns.add(columnDataFormatPrint);
//
//            columnStructurePrint = new ColumnStructurePrint(32,columns);
//
//            rowString =getRowString(columnStructurePrint);
//            sb.add(rowString);
//            //ESTATUS
//            //APROBACION
//            columns = new ArrayList<>();
//            columnDataFormatPrint= new ColumnDataFormatPrint(12,"APROBACION:",image_nav_header_default_comanda);
//            columns.add(columnDataFormatPrint);
//            columnDataFormatPrint= new ColumnDataFormatPrint(20,transaccion.getoRespuesta_iso().getAprobacion(),image_nav_header_default_comanda);
//            columns.add(columnDataFormatPrint);
//
//            columnStructurePrint = new ColumnStructurePrint(32,columns);
//
//            rowString =getRowString(columnStructurePrint);
//            sb.add(rowString);
//            //APROBACION
//            //TRANSACCION
//            columns = new ArrayList<>();
//            columnDataFormatPrint= new ColumnDataFormatPrint(12,"TRANSACCION:",image_nav_header_default_comanda);
//            columns.add(columnDataFormatPrint);
//            columnDataFormatPrint= new ColumnDataFormatPrint(20,transaccion.getoRespuesta_iso().getId_transaccion()+"",image_nav_header_default_comanda);
//            columns.add(columnDataFormatPrint);
//
//            columnStructurePrint = new ColumnStructurePrint(32,columns);
//
//            rowString =getRowString(columnStructurePrint);
//            sb.add(rowString);
//            //TRANSACCION
//            //REFERENCIA
//            columns = new ArrayList<>();
//            columnDataFormatPrint= new ColumnDataFormatPrint(12,"REFERENCIA:",image_nav_header_default_comanda);
//            columns.add(columnDataFormatPrint);
//            columnDataFormatPrint= new ColumnDataFormatPrint(20,transaccion.getoRespuesta_iso().getReferencia()+"",image_nav_header_default_comanda);
//            columns.add(columnDataFormatPrint);
//
//            columnStructurePrint = new ColumnStructurePrint(32,columns);
//
//            rowString =getRowString(columnStructurePrint);
//            sb.add(rowString);
//            //REFERENCIA
//            sb.add( "--------------------------------\r\n\r\n" );
//
//
//
//        return sb.getByteArray();
//    }
//
//    public Imprimir(Context context, Activity act, String folio, Total total, Mesa mesa,String propina,String subTotal,String totalPagar, Configuracion config, TransResult transaccion, String datosQR, Boolean imprimirTicket, Boolean imprimirVoucher, TipoModal tipoModal) {
//
//        this.context = context;
//        this.act = act;
//        this.folio = folio;
//        setting = new PrinterSetting( context );
//        settings = new soft.pacific.mx.pspagocuenta.impresion.starBluetooth.PrinterSetting( context );
//        this.transaccion = transaccion;
//        this.datosQR = datosQR;
//        this.config = config;
//        this.imprimirTicket = imprimirTicket;
//        this.imprimirVoucher = imprimirVoucher;
//        this.tipoModal = tipoModal;
//        this.total = total;
//        this.mesa = mesa;
//        this.propina = propina;
//        this.subTotal = subTotal;
//        this.totalPagar = totalPagar;
//
//        /*realizacion de cmds del tickets */
//        this.data = Ticket( folio.toString(),total,propina,subTotal,totalPagar, config);
//
//
//        /*search printer star bluetooth*/
//        try {
//            mPortList = StarIOPort.searchPrinter( "BT:", act );
//            String starmacdir = config.getImpresora();
//            PortInfo mportInfo = null;
//            if (mPortList != null) {
//                for (int u = 0; u < mPortList.size(); u++) {
//                    mportInfo = mPortList.get( u );
//                    String portmodel = mportInfo.getMacAddress();
//                    if (portmodel.equals( starmacdir )) {
//                        this.port = mportInfo;
//                        break;
//                    }
//                }
//
//
//
//            }
//        } catch (StarIOPortException e) {
//            mPortList = new ArrayList<>();
//        }
//
//
//       // mStarIoExtManager = new StarIoExtManager( StarIoExtManager.Type.OnlyBarcodeReader, setting.getPortName(), setting.getPrinterType(), 10000, context );     // 10000mS!!!
//        //mStarIoExtManager.setListener( mStarIoExtManagerListener );
//
//        if(this.port!=null){
//
//        }
//
//        settings.write( this.port.getModelName(), this.port.getPortName(), this.port.getMacAddress(), ModelCapability.getPortSettings(config.getModeloImpresora().getIdModelo()),ModelCapability.getEmulation(config.getModeloImpresora().getIdModelo()), true );
//        /*fin de impresora star*/
//
//    }
//    public static byte[] GetQr(byte[] qr) {
//        CommandDataList commands = new CommandDataList();
//
//
//        commands.add( 0x1b, 0x20, 0x00 );                     // ANK Right Space
//        commands.add( 0x1b, 0x1d, 0x61, 0x01 );               // Alignment(center)
//        commands.add( qr );
//
//        return commands.getByteArray();
//    }
//    private final StarIoExtManagerListener mStarIoExtManagerListener = new StarIoExtManagerListener() {
//
//        @Override
//        public void onPrinterImpossible() {
//            super.onPrinterImpossible();
//        }
//
//        @Override
//        public void onPrinterOnline() {
//            super.onPrinterOnline();
//        }
//
//        @Override
//        public void onPrinterOffline() {
//            super.onPrinterOffline();
//        }
//
//        @Override
//        public void onPrinterPaperReady() {
//            super.onPrinterPaperReady();
//        }
//
//        @Override
//        public void onPrinterPaperNearEmpty() {
//            super.onPrinterPaperNearEmpty();
//        }
//
//        @Override
//        public void onPrinterPaperEmpty() {
//            super.onPrinterPaperEmpty();
//        }
//
//        @Override
//        public void onPrinterCoverOpen() {
//            super.onPrinterCoverOpen();
//        }
//
//        @Override
//        public void onPrinterCoverClose() {
//            super.onPrinterCoverClose();
//        }
//
//        @Override
//        public void onAccessoryConnectSuccess() {
//            super.onAccessoryConnectSuccess();
//        }
//
//        @Override
//        public void onAccessoryConnectFailure() {
//            super.onAccessoryConnectFailure();
//        }
//
//        @Override
//        public void onAccessoryDisconnect() {
//            super.onAccessoryDisconnect();
//        }
//
//        @Override
//        public void onStatusUpdate(String s) {
//            super.onStatusUpdate( s );
//        }
//    };
//
//    AlertDialog oPd;
//
//    @Override
//    protected void onPreExecute() {
//
//        PagoCuentaActivity actividad = (PagoCuentaActivity)act;
//
//        try{
//            AlertDialog.Builder builder;
//
//
//            Context mContext = actividad;
//            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.progress_dialog_custom,
//                    (ViewGroup) actividad.findViewById(R.id.lyProgressDialogCustom));
//
//            TextView text = (TextView) layout.findViewById(R.id.txtProgressDialogCustom);
//            text.setText(actividad.getString(R.string.msjImprimiendo));
//
//            builder = new AlertDialog.Builder(mContext);
//            builder.setView(layout);
//            builder.setCancelable(false);
//            oPd = builder.create();
//            oPd.show();
//
//        }catch (Exception e){
//            //actividad.habilitarReimprimirTicket();
//            MensajeClass.toast( context,e.getMessage()).show();
//        }
//
//
//    }
//
//    @Override
//    protected Communication.Result doInBackground(Boolean... booleans) {
//        Communication.Result result = null;
//
//        StarIoExt.Emulation emulation = ModelCapability.getEmulation(config.getModeloImpresora().getIdModelo());
//
//        if(imprimirTicket) {
//            /*imprimo los datos */
//            result = soft.pacific.mx.pspagocuenta.impresion.starBluetooth.Communication.sendCommands(data, settings.getPortName(), ModelCapability.getPortSettings(config.getModeloImpresora().getIdModelo()), 10000, context);
//            /* a lo ultimo el qr */
//        }
//
//            if(imprimirTicket) {
//                ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);
//                builder.appendCutPaper(ICommandBuilder.CutPaperAction.PartialCutWithFeed);
//                soft.pacific.mx.pspagocuenta.impresion.starBluetooth.Communication.sendCommands(builder.getCommands(), settings.getPortName(), ModelCapability.getPortSettings(config.getModeloImpresora().getIdModelo()), 10000, context);
//            }
////        }
//
//        return result;
//    }
//
//    @Override
//    protected void onPostExecute(Communication.Result result) {
//
//        oPd.dismiss();
//
//        PagoCuentaActivity actividad = (PagoCuentaActivity)act;
//
//        if(Communication.Result.Success!=result){
//            actividad.habilitarReimprimirTicket();
//            MensajeClass.toast( context, "Ocurrio un error al imprimir, intentelo de nuevo" ).show();
//        }else{
//
//            if(imprimirVoucher){
//
//                switch (tipoModal){
//                    case IMPRIMIR_COPIA_VOUCHER:{
//                        actividad.showPopup(TipoModal.IMPRIMIR_TICKET,null);
//                        break;
//                    }
//                    default:{
//                        actividad.showPopup(TipoModal.IMPRIMIR_COPIA_VOUCHER,null);
//                    }
//                }
//
//            }else{
//                actividad.finalizarOperacion();
//            }
//        }
//
//    }
//
//        public  byte[] createDataBitmap(StarIoExt.Emulation emulation) {
//
//
//        //Bitmap imgBitmap = util.getBitmapImagePdf(context,transaccion.getoRespuesta_ticket().getStatus_comentario());
//
//        Bitmap imgBitmap = null;
//        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);
//
//        builder.beginDocument();
//
//
//        builder.appendBitmapWithAlignment(imgBitmap, true,380,true,ICommandBuilder.AlignmentPosition.Center);
//
//        builder.appendUnitFeed(32);
//
//        builder.endDocument();
//
//        builder.appendCutPaper(ICommandBuilder.CutPaperAction.PartialCutWithFeed);
//
//        return builder.getCommands();
//    }
//
//    public  byte[] createDataQR(StarIoExt.Emulation emulation) {
//        byte[] data = datosQR.getBytes();
//
//        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);
//
//        builder.beginDocument();
//        builder.appendQrCodeWithAlignment(data, ICommandBuilder.QrCodeModel.No2, ICommandBuilder.QrCodeLevel.L, 8, ICommandBuilder.AlignmentPosition.Center);
//
//        builder.appendCutPaper(ICommandBuilder.CutPaperAction.PartialCutWithFeed);
//
//        builder.appendUnitFeed(32);
//
//        builder.endDocument();
//
//        return builder.getCommands();
//    }
//
//
//    public String getRowString (ColumnStructurePrint ColumnStructure){
//
////        Number of columns with number of characters
////        Number of total of characters in a row
//
//        //Get total of characters introduced per column
//        // if this are ok then do the operation to write the row
//
////
//
//        String row = "";
//
//        int totalCharacteresColumnas = 0;
//
//        for(ColumnDataFormatPrint column : ColumnStructure.getDataColumn()){
//            totalCharacteresColumnas = totalCharacteresColumnas + column.getNumberCharecters();
//        }
//
//
//        if(totalCharacteresColumnas<=ColumnStructure.getTotalCharacters()){
//            for(ColumnDataFormatPrint column : ColumnStructure.getDataColumn()){
//                String columnString = column.getCharacters();
//                switch (column.getAlign()){
//                    case image_nav_header_default_comanda:{
//
//                        if(columnString.length()>column.getNumberCharecters()){
//                            columnString = columnString.substring(0,column.getNumberCharecters()-image_nav_header_default_comanda)+".";
//                        }
//
//                        while (columnString.length()<column.getNumberCharecters()){
//                            columnString+=" ";
//                        }
//
//                        break;
//                    }case 2:{
//
//                        if(columnString.length()>column.getNumberCharecters()){
//                            columnString = columnString.substring(0,column.getNumberCharecters()-image_nav_header_default_comanda)+".";
//                        }
//
//                        int totalPreFill = (column.getNumberCharecters()-columnString.length())/2;
//
//                        String preFillString = "";
//
//                        for (int i = 0; i<totalPreFill;i++){
//                            preFillString+=" ";
//                        }
//
//                        columnString = preFillString+columnString;
//
//                        while (columnString.length()<column.getNumberCharecters()){
//                            columnString+=" ";
//                        }
//
//                        break;
//                    }case 3 :{
//
//                        if(columnString.length()>column.getNumberCharecters()){
//                            columnString = columnString.substring(0,column.getNumberCharecters()-image_nav_header_default_comanda)+".";
//                        }
//
//                        int totalPreFill = column.getNumberCharecters() -columnString.length();
//
//                        String preFillString = "";
//
//                        for (int i = 0; i<totalPreFill;i++){
//                            preFillString+=" ";
//                        }
//
//                        columnString = preFillString+columnString;
//
//                        break;
//                    }
//                }
//                row+=columnString;
//            }
//        }
//
//
//
//
//
//        return row;
//    }


}
