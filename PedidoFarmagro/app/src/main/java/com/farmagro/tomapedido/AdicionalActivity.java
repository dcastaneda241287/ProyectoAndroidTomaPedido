package com.farmagro.tomapedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.DireccionEmbarque;
import com.farmagro.tomapedido.modelo.FacturaVendedor;
import com.farmagro.tomapedido.modelo.Proveedor;
import com.farmagro.tomapedido.modelo.VendedorBodega;
import com.farmagro.tomapedido.modelo.Zona;
import com.farmagro.tomapedido.service.SGetFacturaVendedor;
import com.farmagro.tomapedido.service.SPOSDireccionEmbarqueList;
import com.farmagro.tomapedido.util.Constante;
import com.farmagro.tomapedido.util.DecimalDigitsInputFilter;
import com.farmagro.tomapedido.util.Framework;
import com.farmagro.tomapedido.util.FrameworkAdapterOne;
import com.farmagro.tomapedido.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdicionalActivity extends Fragment {
    private static final int DATE_DIALOG_ID = 2;
    private ImageButton btnCal;
    private CabPedido currentPedido;
    private Zona currentZona;
    private Cliente currentCliente;
    private Proveedor currentTransportista;

    private EditText editDireccion;
    private EditText editFecOrden;
    private EditText editFlete;
    private EditText editOrden;
    private Button btnSiguiente;
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            AdicionalActivity.this.mYear = year;
            AdicionalActivity.this.mMonth = monthOfYear;
            AdicionalActivity.this.mDay = dayOfMonth;
            AdicionalActivity.this.updateDisplay();
        }
    };

    /* access modifiers changed from: private */
    public int mDay;
    /* access modifiers changed from: private */
    public int mMonth;
    /* access modifiers changed from: private */
    public int mYear;
    private Spinner spn_tpedido;
    protected SQLibraryApp sqLibraryApp;
    public ProgressDialog progress;
    private List<DireccionEmbarque> lstdirembarque;
    private String sCodCliente;
    private ArrayList<Framework> frameworks;
    private Framework framework;
    private FrameworkAdapterOne adapter;
    private Spinner spndiremb;
    /* access modifiers changed from: protected */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.adicional, container, false);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
        return rootView;
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_adicional));
        Calendar c = Calendar.getInstance();
        this.mYear = c.get(1);
        this.mMonth = c.get(2);
        this.mDay = c.get(5);
        this.currentPedido = (CabPedido) getArguments().get("currentPedido");
        this.currentZona = (Zona) getArguments().get("currentZona");
        this.currentCliente = (Cliente) getArguments().get("currentCliente");
        this.editFlete = view.findViewById(R.id.editFlete);
        this.editOrden = view.findViewById(R.id.editOrden);
        this.editFecOrden = view.findViewById(R.id.editFecOrden);
        this.editDireccion = view.findViewById(R.id.editDireccion);
        this.spn_tpedido = view.findViewById(R.id.spn_tpedido);
        this.spndiremb = view.findViewById(R.id.spnDirEmb);
        this.btnCal = view.findViewById(R.id.btnCal);
        this.btnSiguiente = view.findViewById(R.id.btnAceptar);
        sCodCliente = currentCliente.getCodigo();
        /*this.btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editFlete.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Ingrese el monto del flete", Toast.LENGTH_SHORT).show();
                } else if (editOrden.getText().toString().equals("") && editFecOrden.getText().toString().equals("") && currentPedido.getRequiereoc().equals("S")) {
                    Toast.makeText(getActivity(), "Ingrese los datos de la orden de compra", Toast.LENGTH_SHORT).show();
                } else if (editOrden.getText().toString().equals("") && !editFecOrden.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Ingrese el numero de la orden de compra", Toast.LENGTH_SHORT).show();
                } else if (!editOrden.getText().toString().equals("") && editFecOrden.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Ingrese la fecha de la orden de compra", Toast.LENGTH_SHORT).show();
                } else if (editDireccion.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Ingrese la direccion de entrega", Toast.LENGTH_SHORT).show();
                } else {
                    currentPedido.setMonflete(editFlete.getText().toString());
                    currentPedido.setOrdencompra(editOrden.getText().toString());
                    currentPedido.setFechaorden(editFecOrden.getText().toString());
                    currentPedido.setObservacion(editDireccion.getText().toString());
                    if (spn_tpedido.getSelectedItemPosition() == 0) {
                        currentPedido.setTpedido("T");
                    }
                    if (spn_tpedido.getSelectedItemPosition() == 1) {
                        currentPedido.setTpedido("C");
                    }
                    if (spn_tpedido.getSelectedItemPosition() == 2) {
                        currentPedido.setTpedido(Constante.COD_FACTURA);
                    }
                    if (spn_tpedido.getSelectedItemPosition() == 3) {
                        currentPedido.setTpedido("O");
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("currentPedido", currentPedido);
                    //Intent intent = new Intent(this, ResumenActivity.class);


                    ResumenActivity fragmentResumenActivity = new ResumenActivity();
                    FragmentManager fragmentManager = getParentFragmentManager();

                    fragmentResumenActivity.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentResumenActivity).commit();
                }
            }
        });*/
        ArrayAdapter<CharSequence> createFromResource = ArrayAdapter.createFromResource(getActivity(), R.array.tpedido, android.R.layout.simple_spinner_item);
        createFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spn_tpedido.setAdapter(createFromResource);
        this.editFlete.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});
        this.btnCal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                final Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(1);
                int mm = calendario.get(2);
                int dd = calendario.get(5);


                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editFecOrden.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year));
                    }
                }, yy, mm, dd);

                datePicker.show();
            }
        });

        new AdicionalActivity.ServicioDirEntrega().execute(new Void[0]);
    }


    public class ServicioDirEntrega extends AsyncTask<Void, String, Integer> {
        //variables del hilo
        private Context httpContext;//contexto
        ProgressDialog progressDialog;//dialogo cargando
        public String resultadoapi = "";
        public String linkrequestAPI = "";//link  para consumir el servicio rest
        public String susu = "";
        public String spass = "";

        //constructor del hilo (Asynctask)
        public ServicioDirEntrega() {
        }

        protected void onPreExecute() {
            //LoginActivity.this.progressLogin = ProgressDialog.show(LoginActivity.this, "Login", "Validando...", true);
            //LoginActivity.this.progressLogin.show();
            progress = new ProgressDialog(getActivity());
            progress.show();
            progress.setContentView(R.layout.custom_progress_dialog);
            //se ppdrá cerrar simplemente pulsando back
            progress.setCancelable(true);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        }


        protected Integer doInBackground(Void... arg0) {
            int i = 1;

            /*Usuario beanUsuario = new Usuario();
            beanUsuario.setCodusuario();
            beanUsuario.setClave(FacturaVendedorRep.this.edt_contrasena.getText().toString());*/

            try {
                String resp = new SPOSDireccionEmbarqueList(getActivity().getApplication(), sCodCliente).doRequest();
                lstdirembarque = new ArrayList<>();
                JSONArray jsonarray = new JSONArray(resp);
                for (int j = 0; j < jsonarray.length(); j++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(j);
                    DireccionEmbarque beanDirEmb = new DireccionEmbarque();
                    beanDirEmb.setvCodCliente(jsonobject.getString("vCodCliente"));
                    beanDirEmb.setvDireccion(jsonobject.getString("vDireccion"));
                    beanDirEmb.setiDetDireccion(jsonobject.getString("iDetDireccion"));
                    beanDirEmb.setvDescripcion(jsonobject.getString("vDescripcion"));
                    beanDirEmb.setvContacto(jsonobject.getString("vContacto"));
                    beanDirEmb.setvCargo(jsonobject.getString("vCargo"));
                    beanDirEmb.setvTelefUno(jsonobject.getString("vTelefUno"));
                    beanDirEmb.setvMail(jsonobject.getString("vMail"));
                    beanDirEmb.setDtFecCrea(jsonobject.getString("dtFecCrea"));
                    lstdirembarque.add(beanDirEmb);
                }



                if (lstdirembarque.size() == 0) {
                    i = 3;
                }

            } catch (Exception e) {
                i = -1;

            } finally {
                System.gc();
            }
            return i;
        }

        protected void onPostExecute(Integer result) {
            if (AdicionalActivity.this.progress.isShowing()) {
                AdicionalActivity.this.progress.dismiss();
            }
            if (result.intValue() == -1) {

            }
            frameworks = new ArrayList<>();
            for (DireccionEmbarque direcemb : lstdirembarque) {
                framework = new Framework(0 , direcemb.getvDescripcion(),0,direcemb.getvDescripcion());
                frameworks.add(framework);
            }
            adapter = new FrameworkAdapterOne(getActivity(),frameworks);
            spndiremb.setAdapter(adapter);

            switch (result.intValue()) {
                case -1:
                    probandomensaje("Se ah detectado que no tienes señal ó tu plan de datos no se encuentra activo.", false);
                    //getActivity().showDialog(2);

                case 1:
                    //probandomensaje("Se encontraron registros", true);
                    return;
                case 3:
                    probandomensaje("No se encontraron direcciones", false);
                    return;
                default:
                    return;
            }
        }
    }

    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 2:
                return new DatePickerDialog(getActivity(), this.mDateSetListener, this.mYear, this.mMonth, this.mDay);
            default:
                return null;
        }
    }

    public void updateDisplay() {
        this.editFecOrden.setText(String.valueOf(this.mDay) + "/" + (this.mMonth + 1) + "/" + this.mYear);
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_adicional, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opcBackAdic) {
            salirDetalle();
        }
        if (item.getItemId() == R.id.opcNextAdic) {
            if (this.editFlete.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese el monto del flete", Toast.LENGTH_SHORT).show();
                return false;
            } else if (this.editOrden.getText().toString().equals("") && this.editFecOrden.getText().toString().equals("") && this.currentPedido.getRequiereoc().equals("S")) {
                Toast.makeText(getActivity(), "Ingrese los datos de la orden de compra", Toast.LENGTH_SHORT).show();
                return false;
            } else if (this.editOrden.getText().toString().equals("") && !this.editFecOrden.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese el numero de la orden de compra", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!this.editOrden.getText().toString().equals("") && this.editFecOrden.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese la fecha de la orden de compra", Toast.LENGTH_SHORT).show();
                return false;
            } else if (this.editDireccion.getText().toString().equals("") && ((Framework) AdicionalActivity.this.spndiremb.getItemAtPosition(AdicionalActivity.this.spndiremb.getSelectedItemPosition())).getValor().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese la direccion de entrega", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                this.currentPedido.setMonflete(this.editFlete.getText().toString());
                this.currentPedido.setOrdencompra(this.editOrden.getText().toString());
                this.currentPedido.setFechaorden(this.editFecOrden.getText().toString());
                //this.currentPedido.setObservacion(this.editDireccion.getText().toString());
                if(editDireccion.getText().toString().equals("")){
                    this.currentPedido.setObservacion(((Framework) AdicionalActivity.this.spndiremb.getItemAtPosition(AdicionalActivity.this.spndiremb.getSelectedItemPosition())).getValor());
                }else{
                    this.currentPedido.setObservacion(this.editDireccion.getText().toString());
                }


                if (this.spn_tpedido.getSelectedItemPosition() == 0) {
                    this.currentPedido.setTpedido("T");
                }
                if (this.spn_tpedido.getSelectedItemPosition() == 1) {
                    this.currentPedido.setTpedido("C");
                }
                if (this.spn_tpedido.getSelectedItemPosition() == 2) {
                    this.currentPedido.setTpedido(Constante.COD_FACTURA);
                }
                if (this.spn_tpedido.getSelectedItemPosition() == 3) {
                    this.currentPedido.setTpedido("O");
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("currentPedido", this.currentPedido);
                bundle.putSerializable("currentZona", currentZona);
                bundle.putSerializable("currentCliente", currentCliente);
                //Intent intent = new Intent(this, ResumenActivity.class);


                ResumenActivity fragmentResumenActivity = new ResumenActivity();
                FragmentManager fragmentManager = getParentFragmentManager();

                fragmentResumenActivity.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentResumenActivity).commit();
            }
            return true;
        }
        return true;
    }

    public void probandomensaje(String message, boolean correct) {
        PopupDialogFragment popupDialogFragment = new PopupDialogFragment();
        popupDialogFragment.setMessage(message);
        popupDialogFragment.setCorrect(correct);
        popupDialogFragment.show(getActivity().getSupportFragmentManager(), "PopDialog");

    }

    private void salirDetalle() {
        Bundle bundle2 = new Bundle();
        try {
            currentTransportista = new Proveedor();
            currentTransportista.setCodigo(currentPedido.getCodigotransp());
            currentTransportista.setDescripcion(currentPedido.getNombretransp());
            bundle2.putSerializable("currentPedido", currentPedido);
            bundle2.putSerializable("currentZona", currentZona);
            bundle2.putSerializable("currentCliente", currentCliente);
            bundle2.putSerializable("currentTransportista", currentTransportista);

            TransportistaActivity fragmentTrans = new TransportistaActivity();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentTrans.setArguments(bundle2);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentTrans).commit();
        }catch (Exception e){
            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT);
        }
        return;

        //getActivity().getFragmentManager().popBackStack();
        //getActivity().finish();

    }
}
