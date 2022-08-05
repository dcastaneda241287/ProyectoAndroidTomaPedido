package com.farmagro.tomapedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.Almacen;
import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.Contado;
import com.farmagro.tomapedido.modelo.Credito;
import com.farmagro.tomapedido.modelo.DescuentoCliente;
import com.farmagro.tomapedido.modelo.FormaPago;
import com.farmagro.tomapedido.modelo.Letra;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.modelo.VendedorBodega;
import com.farmagro.tomapedido.modelo.Zona;
import com.farmagro.tomapedido.modelo.ZonaVendedor;
import com.farmagro.tomapedido.util.Constante;
import com.farmagro.tomapedido.util.Framework;
import com.farmagro.tomapedido.util.FrameworkAdapter;
import com.farmagro.tomapedido.util.FrameworkAdapterOne;
import com.farmagro.tomapedido.util.Gps;
import com.farmagro.tomapedido.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PedidoActivity extends Fragment {
    private static final int DATE_DIALOG_ID = 2;
    private ImageButton btnCal;
    /* access modifiers changed from: private */
    public Cliente currentCliente;
    /* access modifiers changed from: private */
    public Zona currentZona;
    /* access modifiers changed from: private */
    public EditText editFecha;
    /* access modifiers changed from: private */
    public EditText editInicioLetra;
    /* access modifiers changed from: private */
    public EditText editLetra;
    /* access modifiers changed from: private */
    public EditText editSeparacion;
    private int indexMoneda;
    /* access modifiers changed from: private */
    public LocationManager locManager;
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            PedidoActivity.this.mYear = year;
            PedidoActivity.this.mMonth = monthOfYear;
            PedidoActivity.this.mDay = dayOfMonth;
            PedidoActivity.this.updateDisplay();
        }
    };
    /* access modifiers changed from: private */
    public int mDay;
    /* access modifiers changed from: private */
    public int mMonth;
    /* access modifiers changed from: private */
    public int mYear;
    /* access modifiers changed from: private */
    public ProgressDialog progressProcess;
    /* access modifiers changed from: private */
    //public Spinner spnAlmacen;
    /* access modifiers changed from: private */
    public Spinner spnCondicionPago;
    /* access modifiers changed from: private */
    public Spinner spnFormaPago;
    /* access modifiers changed from: private */
    public Spinner spnNivelPrecio;
    /* access modifiers changed from: private */
    public Spinner spnTipoVenta;
    private TextView txtCodigo;
    private TextView txtDocumento;
    /* access modifiers changed from: private */
    public TextView txtInicioLetra;
    private TextView txtLabel;
    /* access modifiers changed from: private */
    public TextView txtLetra;
    private TextView txtMoneda;
    private TextView txtRazon;
    /* access modifiers changed from: private */
    public TextView txtSeparacion;
    protected SQLibraryApp sqLibraryApp;
    private ArrayList<Framework> frameworks;
    private Framework framework;
    private FrameworkAdapterOne adapter;
    private int x, y = 0;
    //private LinearLayout contenidoLayout;
    private ImageView imgFramework;
    private TextView txtNomFramework, txtContenido, txtdescuento;
    DescuentoCliente beandescuento;
    Location location = null;
    private CabPedido currentPedido;
    LocationManager mlocManager;
    Localizacion Local;
    private String sDirUbicacion;
    /* access modifiers changed from: protected */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_pedido, container, false);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());


        return rootView;
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_pedido));

        Usuario beanUsuario = this.sqLibraryApp.getDataManager().getUsuario();

        Calendar c = Calendar.getInstance();
        setHasOptionsMenu(true);
        this.mYear = c.get(1);
        this.mMonth = c.get(2);
        this.mDay = c.get(5);
        //Bundle bundle = getArguments().getBundle();
        this.currentCliente = (Cliente) getArguments().get("currentCliente");
        this.currentZona = (Zona) getArguments().get("currentZona");
        this.txtLetra = view.findViewById(R.id.txtLetra);
        this.txtInicioLetra = view.findViewById(R.id.txtInicioLetra);
        this.txtLabel = view.findViewById(R.id.txtLabel);
        this.txtCodigo = view.findViewById(R.id.txtCodigo);
        this.txtRazon = view.findViewById(R.id.txtRazon);
        this.txtSeparacion = view.findViewById(R.id.txtSeparacion);
        this.txtDocumento = view.findViewById(R.id.txtDocumento);
        this.txtMoneda = view.findViewById(R.id.txtMoneda);
        this.spnTipoVenta = view.findViewById(R.id.spnTipoVenta);
        this.spnFormaPago = view.findViewById(R.id.spnFormaPago);
        this.spnCondicionPago = view.findViewById(R.id.spnCondicionPago);
        this.spnNivelPrecio = view.findViewById(R.id.spnNivelPrecio);
        //this.spnAlmacen = (Spinner) findViewById(R.id.spnAlmacen);
        this.editFecha = view.findViewById(R.id.editFecha);
        this.editLetra = view.findViewById(R.id.editLetra);
        this.editInicioLetra = view.findViewById(R.id.editInicioLetra);
        this.editSeparacion = view.findViewById(R.id.editSeparacion);
        this.btnCal = view.findViewById(R.id.btnCal);
        this.txtCodigo.setText(currentCliente.getCodigo());
        this.imgFramework = view.findViewById(R.id.imgframework);
        this.txtdescuento = view.findViewById(R.id.txtDescEstructurado);

        beandescuento = PedidoActivity.this.sqLibraryApp.getDataManager().DevolverDescuentoPorCLiente(currentCliente.getCodigo());
        if(beandescuento != null) {
            txtdescuento.setText(String.valueOf(beandescuento.getDeDescuento() + " - " + beandescuento.getvDescripcion()));
        }
        //this.txtNomFramework = view.findViewById(R.id.txtframework);
        //this.txtContenido = view.findViewById(R.id.txtContenido);
        //this.contenidoLayout = view.findViewById(R.id.contenido);
        //this.contenidoLayout.setVisibility(View.GONE);

        if (this.currentCliente.getCodigo().length() > 8) {
            this.txtLabel.setText("Razon Social");
        } else {
            this.txtLabel.setText("Nombre");
        }
        this.txtRazon.setText(this.currentCliente.getNombre());
        if (this.currentCliente.getDocGenerar().equalsIgnoreCase(Constante.COD_FACTURA)) {
            this.txtDocumento.setText(Constante.DES_FACTURA);
        } else {
            this.txtDocumento.setText(Constante.DES_BOLETA);
        }
        if (this.currentCliente.getMonedaNivel().equalsIgnoreCase(Constante.COD_LMONEDA)) {
            this.txtMoneda.setText("Local");
            this.indexMoneda = 0;
        } else {
            this.txtMoneda.setText("Dolar");
            this.indexMoneda = 1;
        }
        //ArrayAdapter<CharSequence> createFromResource = ArrayAdapter.createFromResource(getActivity(), R.array.tipoventa, android.R.layout.simple_spinner_item);
        //createFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //int count = getNumberOfSongsAvailable();

        frameworks = new ArrayList<>();
        Resources res = getResources();
        String[] tipoventa = res.getStringArray(R.array.tipoventa);
        //framework = new Framework(0 , "[Seleccione]",0);
        //frameworks.add(framework);
        for (x = 0; x < tipoventa.length; x++) {
            framework = new Framework(0 , tipoventa[x].toString(),0,"");
            frameworks.add(framework);
        }
        adapter = new FrameworkAdapterOne(getActivity(),frameworks);
        this.spnTipoVenta.setAdapter(adapter);
        this.spnTipoVenta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
                frameworks = new ArrayList<>();
                try {

                List<FormaPago> formasPagos = new ArrayList<>();
                if (pos == 0) {
                    //contenidoLayout.setVisibility(View.GONE);
                    /*FormaPago bean = new FormaPago();
                    bean.setDescripcion("Documento de venta");
                    formasPagos.add(bean);*/
                    framework = new Framework(0 , "Documento de venta",0,"");
                    frameworks.add(framework);
                } else {
                    //contenidoLayout.setVisibility(View.VISIBLE);
                    //imgFramework.setImageResource(framework1.getImg());
                    //txtNomFramework.setText(framework1.getNombre());
                    //txtContenido.setText(framework1.getContenido());
                    /*FormaPago uno = new FormaPago();
                    uno.setDescripcion("Doc. Venta + Canje x letra");
                    formasPagos.add(uno);
                    FormaPago dos = new FormaPago();
                    dos.setDescripcion("Documento de venta");
                    formasPagos.add(dos);*/

                    framework = new Framework(0 , "Doc. Venta + Canje x letra",0, "");
                    frameworks.add(framework);
                    framework = new Framework(0 , "Documento de venta",0,"");
                    frameworks.add(framework);
                }
                /*ArrayAdapter<FormaPago> formaPagAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, formasPagos);
                formaPagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/

                adapter = new FrameworkAdapterOne(getActivity(),frameworks);
                PedidoActivity.this.spnFormaPago.setAdapter(adapter);
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Error al obtener valor: " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnFormaPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
                List<Letra> letras = PedidoActivity.this.sqLibraryApp.getDataManager().getLetraList();


                if (PedidoActivity.this.spnTipoVenta.getSelectedItemPosition() == 0) {
                    //ArrayAdapter<Contado> contadoAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, PedidoActivity.this.sqLibraryApp.getDataManager().getContadoList());
                    //contadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    frameworks = new ArrayList<>();
                    for (Contado contado : PedidoActivity.this.sqLibraryApp.getDataManager().getContadoList()) {
                        framework = new Framework(0 , contado.getDescondicion(),Integer.parseInt(contado.getDiasneto()),contado.getCodcondicion());
                        frameworks.add(framework);
                    }
                    adapter = new FrameworkAdapterOne(getActivity(),frameworks);
                    PedidoActivity.this.spnCondicionPago.setAdapter(adapter);
                    PedidoActivity.this.hideFields();
                } else if (PedidoActivity.this.spnFormaPago.getSelectedItemPosition() == 0) {
                    //ArrayAdapter<Letra> letraAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, PedidoActivity.this.sqLibraryApp.getDataManager().getLetraList());
                    //letraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    frameworks = new ArrayList<>();
                    for (Letra letra : letras) {
                        framework = new Framework(0 , letra.getDescondicion(), Integer.parseInt(letra.getDiasneto()), letra.getCodcondicion());
                        frameworks.add(framework);
                    }
                    adapter = new FrameworkAdapterOne(getActivity(),frameworks);

                    PedidoActivity.this.spnCondicionPago.setAdapter(adapter);
                    PedidoActivity.this.txtLetra.setVisibility(View.GONE);
                    PedidoActivity.this.editLetra.setVisibility(View.GONE);
                    PedidoActivity.this.editLetra.setText("1");
                    PedidoActivity.this.txtInicioLetra.setVisibility(View.GONE);
                    String inicio = "";
                    if (PedidoActivity.this.spnFormaPago.getSelectedItemPosition() == 0) {
                        inicio = String.valueOf(((Framework) PedidoActivity.this.spnCondicionPago.getItemAtPosition(PedidoActivity.this.spnCondicionPago.getSelectedItemPosition())).getContenido());
                    }
                    PedidoActivity.this.editInicioLetra.setVisibility(View.GONE);
                    PedidoActivity.this.editInicioLetra.setText(inicio);
                    PedidoActivity.this.txtSeparacion.setVisibility(View.GONE);
                    PedidoActivity.this.editSeparacion.setVisibility(View.GONE);
                    PedidoActivity.this.editSeparacion.setText(Constante.NO);
                    int i = 0;
                    for (Letra letra : letras) {
                        if (letra.getCodcondicion().equalsIgnoreCase(PedidoActivity.this.currentCliente.getCondicionPago())) {
                            PedidoActivity.this.spnCondicionPago.setSelection(i);
                            return;
                        }
                        i++;
                    }
                } else {
                    //ArrayAdapter<Credito> creditoAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, PedidoActivity.this.sqLibraryApp.getDataManager().getCreditoList());
                    //creditoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    List<Credito> lstcredito = PedidoActivity.this.sqLibraryApp.getDataManager().getCreditoList();
                    frameworks = new ArrayList<>();
                    for (Credito credito : lstcredito) {
                        framework = new Framework(0 , credito.getDescondicion(),Integer.parseInt(credito.getDiasneto()),credito.getCodcondicion());
                        frameworks.add(framework);
                    }
                    adapter = new FrameworkAdapterOne(getActivity(),frameworks);
                    PedidoActivity.this.spnCondicionPago.setAdapter(adapter);
                    PedidoActivity.this.hideFields();
                    int i2 = 0;
                    for (Credito credito : lstcredito) {
                        if (credito.getCodcondicion().equalsIgnoreCase(PedidoActivity.this.currentCliente.getCondicionPago())) {
                            PedidoActivity.this.spnCondicionPago.setSelection(i2);
                            return;
                        }
                        i2++;
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        this.spnCondicionPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
                Framework condicion =(Framework) PedidoActivity.this.spnCondicionPago.getItemAtPosition(pos);
                String tipocon = condicion.getNombre();
                String substr = tipocon.substring(0,2);
                if (substr.equals("LE") || substr.equals("Le")) {
                    PedidoActivity.this.editInicioLetra.setText(String.valueOf(condicion.getContenido()));
                } else {
                    PedidoActivity.this.editInicioLetra.setText("");
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (this.indexMoneda == 0) {
            frameworks = new ArrayList<>();
            String[] nivelprecio = res.getStringArray(R.array.nivelprecio1);

            for (x = 0; x < nivelprecio.length; x++) {
                framework = new Framework(0 , nivelprecio[x].toString(),0,"");
                frameworks.add(framework);
            }
            adapter = new FrameworkAdapterOne(getActivity(),frameworks);

            this.spnNivelPrecio.setAdapter(adapter);
            if (this.currentCliente.getNivelPrecio().equalsIgnoreCase(Constante.NIVELPREC1)) {
                this.spnNivelPrecio.setSelection(0);
            } else {
                this.spnNivelPrecio.setSelection(1);
            }
        } else {
            frameworks = new ArrayList<>();
            String[] nivelprecio2 = res.getStringArray(R.array.nivelprecio2);
            for (x = 0; x < nivelprecio2.length; x++) {
                framework = new Framework(0 , nivelprecio2[x].toString(),0,"");
                frameworks.add(framework);
            }
            adapter = new FrameworkAdapterOne(getActivity(),frameworks);
            this.spnNivelPrecio.setAdapter(adapter);
            if (this.currentCliente.getNivelPrecio().equalsIgnoreCase(Constante.NIVELPREC2)) {
                this.spnNivelPrecio.setSelection(0);
            } else {
                this.spnNivelPrecio.setSelection(1);
            }
        }
        List<Almacen> listAlmacen = new ArrayList<>();

        for (VendedorBodega vendedorBodega : this.sqLibraryApp.getDataManager().getVendedorBodegaList(beanUsuario.getClave(),1)) {
            Almacen beanAlmacen = new Almacen();
            beanAlmacen.setCodigo(vendedorBodega.getvCodBodega());
            beanAlmacen.setDescripcion(vendedorBodega.getvDescripcion());
            listAlmacen.add(beanAlmacen);
        }

        this.editFecha.setText(Util.getToday());
        this.btnCal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                final Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(1);
                int mm = calendario.get(2);
                int dd = calendario.get(5);


                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editFecha.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year));
                    }
                }, yy, mm, dd);

                datePicker.show();
            }
        });

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

    }

    /* access modifiers changed from: private */
    public void hideFields() {
        this.txtLetra.setVisibility(View.GONE);
        this.editLetra.setVisibility(View.GONE);
        this.editLetra.setText("");
        this.editInicioLetra.setVisibility(View.GONE);
        this.editInicioLetra.setText("");
        this.txtSeparacion.setVisibility(View.GONE);
        this.editSeparacion.setVisibility(View.GONE);
        this.editSeparacion.setText("");
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 2:
                return new DatePickerDialog(getActivity(), this.mDateSetListener, this.mYear, this.mMonth, this.mDay);
            default:
                return null;
        }
    }

    /* access modifiers changed from: private */
    public void updateDisplay() {
        this.editFecha.setText(String.valueOf(this.mDay) + "/" + (this.mMonth + 1) + "/" + this.mYear);
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pedido, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.opcPedido) {
            return true;
        }
        //finalizar();
        new GrabarTask(this, (GrabarTask) null).execute(new Void[0]);
        return true;
    }

    private class GrabarTask extends AsyncTask<Void, String, Integer> {

        private GrabarTask() {
            currentPedido = new CabPedido();
        }

        GrabarTask(PedidoActivity pedidoActivity, GrabarTask grabarTask) {
            getActivity();
        }

        public void onPreExecute() {
            PedidoActivity.this.progressProcess = ProgressDialog.show(getActivity(), "Pedido", "Grabando...", true, false);
            PedidoActivity.this.progressProcess.show();
            PedidoActivity.this.locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            } else {
                return;
            }

            Toast.makeText(getActivity(), "Active el GPS", Toast.LENGTH_LONG).show();
            cancel(true);
        }

        public Integer doInBackground(Void... arg0) {
            //Location location = null;
            while (location == null) {
                try {
                    //location = Gps.getLocation();
                    currentPedido.setLatitud(String.valueOf(location.getLatitude()));
                    currentPedido.setLongitud(String.valueOf(location.getLongitude()));
                } catch (Exception e) {
                    Log.e(Constante.TAG_FARMAGRO, "EXCEPTION GETLOCATION", e);
                }
            }
            stopListening();
            return 1;
        }

        public void onPostExecute(Integer result) {
            String tipoVenta;
            String formapago;
            String fec;

            currentPedido = new CabPedido();
            //Gps.stopListening();
            if (PedidoActivity.this.progressProcess.isShowing()) {
                PedidoActivity.this.progressProcess.dismiss();
            }

            currentPedido.setLatitud(String.valueOf(location.getLatitude()));
            currentPedido.setLongitud(String.valueOf(location.getLongitude()));
            currentPedido.setDirecciongps(sDirUbicacion);
            fec = PedidoActivity.this.editFecha.getText().toString();

            currentPedido.setFechaprom(PedidoActivity.this.editFecha.getText().toString());
            currentPedido.setZona(PedidoActivity.this.currentZona.getCodigo());
            if (PedidoActivity.this.spnTipoVenta.getSelectedItemPosition() == 0) {
                tipoVenta = "C";
                formapago = Constante.COD_FACTURA;
            } else {
                tipoVenta = "X";
                if (PedidoActivity.this.spnFormaPago.getSelectedItemPosition() == 0) {
                    formapago = Constante.COD_LMONEDA;
                } else {
                    formapago = Constante.COD_FACTURA;
                }
            }
            if (PedidoActivity.this.currentCliente.getDocGenerar().equalsIgnoreCase(Constante.COD_FACTURA)) {
                currentPedido.setDocumento(Constante.COD_FACTURA);
            } else {
                currentPedido.setDocumento(Constante.COD_BOLETA);
            }
            if (PedidoActivity.this.currentCliente.getMonedaNivel().equalsIgnoreCase(Constante.COD_LMONEDA)) {
                currentPedido.setMoneda(Constante.COD_LMONEDA);
            } else {
                currentPedido.setMoneda(Constante.COD_DMONEDA);
            }
            Framework condicion1 =(Framework) PedidoActivity.this.spnNivelPrecio.getItemAtPosition(PedidoActivity.this.spnNivelPrecio.getSelectedItemPosition());
            currentPedido.setNivelprecio(condicion1.getNombre());
            //this.currentPedido.setAlmacen(((Almacen) PedidoActivity.this.spnAlmacen.getItemAtPosition(PedidoActivity.this.spnAlmacen.getSelectedItemPosition())).getCodigo());
            String codCondPago = "";
            Framework condicion =(Framework) PedidoActivity.this.spnCondicionPago.getItemAtPosition(PedidoActivity.this.spnCondicionPago.getSelectedItemPosition());
            String tipocon = condicion.getNombre();
            String substr=tipocon.substring(0,2);
            if (substr.equals("CO")) {
                codCondPago = ((Framework) condicion).getValor();
            }
            if (substr.equals("LE") || substr.equals("Le")) {
                codCondPago = ((Framework) condicion).getValor();
            }
            if (substr.equals("CR")) {
                codCondPago = ((Framework) condicion).getValor();
            }
            currentPedido.setCondicionpago(codCondPago);
            currentPedido.setLetras(PedidoActivity.this.editLetra.getText().toString());
            currentPedido.setInicioletra(PedidoActivity.this.editInicioLetra.getText().toString());
            currentPedido.setSeparacion(PedidoActivity.this.editSeparacion.getText().toString());
            currentPedido.setNumero(new StringBuilder().append(System.currentTimeMillis()).toString());
            currentPedido.setCliente(PedidoActivity.this.currentCliente.getCodigo());
            currentPedido.setNombcliente(PedidoActivity.this.currentCliente.getNombre());
            currentPedido.setTipoventa(tipoVenta);
            currentPedido.setFormapago(formapago);
            currentPedido.setDireccion(PedidoActivity.this.currentCliente.getDireccion());
            currentPedido.setDirecccobro(PedidoActivity.this.currentCliente.getDireccion());
            currentPedido.setCoddefault(PedidoActivity.this.currentCliente.getDirembarque());
            currentPedido.setRequiereoc(PedidoActivity.this.currentCliente.getRequiereOC());
            currentPedido.setDialibre(PedidoActivity.this.currentCliente.getDiasLibre());
            if(beandescuento != null) {
                currentPedido.setDescuento(beandescuento.getDeDescuento());
            }else{
                currentPedido.setDescuento("");
            }

            DetalleActivity fragmentDetalleActivity = new DetalleActivity();
            FragmentManager fragmentManager = getParentFragmentManager();

            Bundle bundle = new Bundle();
            bundle.putSerializable("currentPedido", currentPedido);
            bundle.putSerializable("currentZona", currentZona);
            bundle.putSerializable("currentCliente", currentCliente);
            fragmentDetalleActivity.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentDetalleActivity).commit();
            //PedidoActivity.this.sqLibraryApp.getDataManager().deleteTmpDetalle();

        }
    }
    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud

        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            location = loc;
            try {
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    sDirUbicacion = DirCalle.getAddressLine(0);
                    /*direccion.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));*/
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void locationStart() {
        mlocManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

        //coordenadas.setText("Localización agregada");
        //direccion.setText("");
    }
    public void stopListening() {
        try {
            mlocManager.removeUpdates(Local);
            //f = null;
        } catch (NullPointerException e2)
        {
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public class Localizacion implements LocationListener {
        PedidoActivity mainActivity;

        public PedidoActivity getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(PedidoActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            //coordenadas.setText(Text);
            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            //coordenadas.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            //coordenadas.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
}
