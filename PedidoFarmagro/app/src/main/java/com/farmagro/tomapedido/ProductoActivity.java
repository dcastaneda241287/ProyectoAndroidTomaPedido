package com.farmagro.tomapedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.farmagro.tomapedido.modelo.Letra;
import com.farmagro.tomapedido.modelo.Producto;
import com.farmagro.tomapedido.modelo.TmpDetalle;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.modelo.VendedorBodega;
import com.farmagro.tomapedido.modelo.Zona;
import com.farmagro.tomapedido.util.Constante;
import com.farmagro.tomapedido.util.DecimalDigitsInputFilter;
import com.farmagro.tomapedido.util.Framework;
import com.farmagro.tomapedido.util.FrameworkAdapterOne;
import com.farmagro.tomapedido.util.Util;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductoActivity extends Fragment {
    private static final int SEARCH_PRODUCT_CODE = 9;
    private Button btnAceptar;
    private String codmoneda;
    /* access modifiers changed from: private */
    public Producto currentProducto;
    /* access modifiers changed from: private */
    public TmpDetalle currentTmp;
    /* access modifiers changed from: private */
    public EditText editCantidad;
    /* access modifiers changed from: private */
    public EditText editDscto;
    /* access modifiers changed from: private */
    public String nivelPrecio;
    /* access modifiers changed from: private */
    public Float precioTotal;
    private TextView txtCodigo;
    private TextView txtDescripcion;
    private TextView txtIGV;
    private TextView txtLabelUnd;
    /* access modifiers changed from: private */
    public TextView txtPrecioUnid;
    private TextView txtStock;
    private TextView txtTotal;
    private TextView txtUnidVta;
    public Spinner spnAlmacen;
    private CabPedido currentPedido;
    private Zona currentZona;
    private Cliente currentCliente;

    protected SQLibraryApp sqLibraryApp;
    private ArrayList<Framework> frameworks;
    private Framework framework;
    private FrameworkAdapterOne adapter;

    /* access modifiers changed from: protected */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_producto, container, false);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
        return rootView;
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_producto));
        setHasOptionsMenu(true);
        Usuario beanUsuario = this.sqLibraryApp.getDataManager().getUsuario();
        this.currentTmp = (TmpDetalle) getArguments().get("currentTmp");
        this.nivelPrecio = getArguments().getString("nivelPrecio");
        this.currentPedido = (CabPedido) getArguments().get("currentPedido");
        this.currentZona = (Zona) getArguments().get("currentZona");
        this.currentCliente = (Cliente) getArguments().get("currentCliente");
        if (this.currentTmp == null) {
            this.currentProducto = (Producto) getArguments().get("currentProducto");
        } else {
            this.currentProducto = this.sqLibraryApp.getDataManager().getProductoByCodigo(this.currentTmp.getCodproducto());
        }
        this.codmoneda = getArguments().getString("codmoneda");
        this.txtCodigo = view.findViewById(R.id.txtCodigo);
        this.txtDescripcion = view.findViewById(R.id.txtDescripcion);
        this.txtPrecioUnid = view.findViewById(R.id.txtPrecioUnid);
        this.txtStock = view.findViewById(R.id.txtStock);
        this.txtUnidVta = view.findViewById(R.id.txtUnidVta);
        this.txtIGV = view.findViewById(R.id.txtIGV);
        this.txtLabelUnd = view.findViewById(R.id.txtLabelUnd);
        this.txtTotal = view.findViewById(R.id.txtTotal);
        this.editCantidad = view.findViewById(R.id.editCantidad);
        this.btnAceptar = view.findViewById(R.id.btnAceptar);
        this.spnAlmacen = view.findViewById(R.id.spnAlmacen);
        List<Almacen> listAlmacen = new ArrayList<>();
        Almacen beanAlmacen = new Almacen();
        /*beanAlmacen.setCodigo("0");
        beanAlmacen.setDescripcion("[Seleccione Almacen]");
        listAlmacen.add(beanAlmacen);*/
        List<VendedorBodega> listVendedorBodega = ProductoActivity.this.sqLibraryApp.getDataManager().getVendedorBodegaList(beanUsuario.getClave(), 1);
        frameworks = new ArrayList<>();
        for (VendedorBodega vendedorBodega : listVendedorBodega) {
            framework = new Framework(0 , vendedorBodega.getvDescripcion(),0,vendedorBodega.getvCodBodega());
            frameworks.add(framework);
        }
        //ArrayAdapter<Almacen> almacenAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listAlmacen);
        //almacenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter = new FrameworkAdapterOne(getActivity(),frameworks);
        this.spnAlmacen.setAdapter(adapter);
        if (Objects.isNull(currentTmp)) {
            this.spnAlmacen.setSelection(0);
        } else {
            if (currentTmp.getVcodalmacen() != "null") {
                int i = 0;
                for (VendedorBodega vendbodega : listVendedorBodega) {
                    if (vendbodega.getvCodBodega().equalsIgnoreCase(ProductoActivity.this.currentTmp.getVcodalmacen())) {
                        ProductoActivity.this.spnAlmacen.setSelection(i);
                        //return;
                    }
                    i++;
                }
            }
        }
        if (Objects.isNull(currentProducto)) {
            this.spnAlmacen.setSelection(0);
        } else {
            if (currentProducto.getvCodBodega() != "null") {
                int i = 0;
                for (VendedorBodega vendbodega : listVendedorBodega) {
                    if (vendbodega.getvCodBodega().equalsIgnoreCase(ProductoActivity.this.currentProducto.getvCodBodega())) {
                        ProductoActivity.this.spnAlmacen.setSelection(i);
                        //return;
                    }
                    i++;
                }
            }
        }

        this.spnAlmacen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
                Framework condicion = (Framework) (ProductoActivity.this.spnAlmacen.getItemAtPosition(pos));
                //if (ProductoActivity.this.spnAlmacen.getSelectedItemPosition() != 0) {
                    Producto bean = new Producto();
                    bean = ProductoActivity.this.sqLibraryApp.getDataManager().getProductoByCodigoAndAlmacen(txtCodigo.getText().toString(), condicion.getValor().toString());
                    txtStock.setText(Util.mostrarNumero(Float.parseFloat(bean.getStockproducto())));
                //} else {
               //     txtStock.setText("0");
               // }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.editCantidad.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void afterTextChanged(Editable arg0) {
                ProductoActivity.this.calcularTotal();
            }
        });
        this.editDscto = view.findViewById(R.id.editDscto);
        this.editDscto.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void afterTextChanged(Editable arg0) {
                ProductoActivity.this.calcularTotal();
            }
        });
        editDscto.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,5)});

        /*DescuentoCliente bean = ProductoActivity.this.sqLibraryApp.getDataManager().DevolverDescuentoPorCLiente(currentCliente.getCodigo());
        if(bean != null) {
            editDscto.setText(String.valueOf(bean.getDeDescuento()));
        }*/
        this.txtCodigo.setText(this.currentProducto.getCodproducto());
        this.txtDescripcion.setText(this.currentProducto.getDescripcion());
        //if (ProductoActivity.this.spnAlmacen.getSelectedItemPosition() == 0) {
        //    this.txtStock.setText("0");
        //} else {
            this.txtStock.setText(Util.mostrarNumero(Float.parseFloat(this.currentProducto.getStockproducto())));
        //}

        this.txtUnidVta.setText(this.currentProducto.getUnidadventa());
        float descEsctructurado = 0.0f;
        if (!currentPedido.getDescuento().toString().equals("")) {
            descEsctructurado = Float.parseFloat(currentPedido.getDescuento());
        }
        if (this.codmoneda.equals(Constante.COD_LMONEDA)) {

            this.txtPrecioUnid.setText(Util.getTwoDecimals((((100.0f - descEsctructurado) / 100.0f) * Float.parseFloat(this.currentProducto.getPreciosol()))));

            if (this.nivelPrecio.equals(Constante.TIPO_SEMILLA) || this.nivelPrecio.equals("SEMILLAS_SOL")) {
                this.txtIGV.setText("0.0");
            } else {
                this.txtIGV.setText(Util.getTwoDecimals(Float.parseFloat(this.currentProducto.getIgvsol())));
            }
            this.txtLabelUnd.setText("Precio unitario (L)");
        } else {
            //float a,b,c, tot;
            //c = Float.parseFloat(currentPedido.getDescuento());
            //a = ((100.0f - Float.parseFloat(currentPedido.getDescuento())) / 100.0f);
            //b = Float.parseFloat(this.currentProducto.getPreciodol());
            //tot = ((100.0f - Float.parseFloat(currentPedido.getDescuento())) / 100.0f) * Float.parseFloat(this.currentProducto.getPreciodol());
            this.txtPrecioUnid.setText(Util.getTwoDecimals((((100.0f - descEsctructurado) / 100.0f) * Float.parseFloat(this.currentProducto.getPreciodol()))));

            if (this.nivelPrecio.equals(Constante.TIPO_SEMILLA) || this.nivelPrecio.equals("SEMILLAS_SOL")) {
                this.txtIGV.setText("0.0");
            } else {
                this.txtIGV.setText(Util.getTwoDecimals(Float.parseFloat(this.currentProducto.getIgvdol())));
            }
            this.txtLabelUnd.setText("Precio unitario (D)");
        }
        if (this.currentTmp != null) {
            this.editCantidad.setText(Util.mostrarNumero(this.currentTmp.getCantidad()));
            this.editDscto.setText(this.currentTmp.getDescuento().toString());
        }
    }


    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_prod, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opcBackProd) {
            salirDetalle();
        }
        if (item.getItemId() == R.id.opcSavePProd) {
            float igv = 0.0f;
            try {
                if (ProductoActivity.this.editCantidad.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Ingrese la cantidad", Toast.LENGTH_SHORT).show();
                } else if (Float.parseFloat(ProductoActivity.this.editCantidad.getText().toString()) <= 0.0f) {
                    Toast.makeText(getActivity(), "La cantidad debe ser mayor a cero", Toast.LENGTH_SHORT).show();
                } else if (Float.parseFloat(ProductoActivity.this.txtStock.getText().toString()) <= 0.0f) {
                    Toast.makeText(getActivity(), "Producto no cuenta con stock en el almacen seleccionado!", Toast.LENGTH_SHORT).show();
                } else if (ProductoActivity.this.editDscto.getText().toString().equals("") || (Float.parseFloat(ProductoActivity.this.editDscto.getText().toString()) > 0.0f && Float.parseFloat(ProductoActivity.this.editDscto.getText().toString()) <= 100.0f)) {
                    TmpDetalle beanTmpDet = new TmpDetalle();
                    beanTmpDet.setCodproducto(ProductoActivity.this.currentProducto.getCodproducto());
                    beanTmpDet.setDesproducto(ProductoActivity.this.currentProducto.getDescripcion());
                    //beanTmpDet.setPrecio(Float.valueOf(Util.obtieneDosDecimales(Float.parseFloat(ProductoActivity.this.txtPrecioUnid.getText().toString().replaceAll(",", "")))));
                    beanTmpDet.setPrecio(Util.formatearDecimales(Float.parseFloat(ProductoActivity.this.txtPrecioUnid.getText().toString()), 2));
                    //beanTmpDet.setCantidad(Float.valueOf(Util.obtieneDosDecimales(Float.parseFloat(ProductoActivity.this.editCantidad.getText().toString()))));

                    beanTmpDet.setCantidad(Util.formatearDecimales(Float.parseFloat(ProductoActivity.this.editCantidad.getText().toString()), 2));
                    float descuento = 0.0f;
                    if (!ProductoActivity.this.editDscto.getText().toString().equals("")) {
                        descuento = Float.parseFloat(ProductoActivity.this.editDscto.getText().toString());
                    }
                    beanTmpDet.setDescuento(descuento);
                    beanTmpDet.setTotal(Util.formatearDecimales(ProductoActivity.this.precioTotal, 2));
                    beanTmpDet.setUndventa(ProductoActivity.this.currentProducto.getUnidadventa());
                    if (ProductoActivity.this.nivelPrecio.equals(Constante.TIPO_SEMILLA) || ProductoActivity.this.nivelPrecio.equals("SEMILLAS_SOL")) {
                        igv = 0.0f;
                    } else {
                        igv = (ProductoActivity.this.precioTotal.floatValue() * 18.0f) / 100.0f;
                    }

                    beanTmpDet.setIgv(Util.formatearDecimales(igv, 2));

                    DescuentoCliente beandes = ProductoActivity.this.sqLibraryApp.getDataManager().DevolverDescuentoPorCLiente(currentCliente.getCodigo());
                    if(beandes != null) {
                        beanTmpDet.setDescuentoestructurado(beandes.getDeDescuento());
                    }

                    //this.currentPedido.setAlmacen(((Almacen) PedidoActivity.this.spnAlmacen.getItemAtPosition(PedidoActivity.this.spnAlmacen.getSelectedItemPosition())).getCodigo());

                    beanTmpDet.setVcodalmacen(((Framework) ProductoActivity.this.spnAlmacen.getItemAtPosition(ProductoActivity.this.spnAlmacen.getSelectedItemPosition())).getValor());
                    if (ProductoActivity.this.currentTmp == null) {
                        ProductoActivity.this.sqLibraryApp.getDataManager().saveTmpDetalle(beanTmpDet);
                    } else {
                        beanTmpDet.setId(ProductoActivity.this.currentTmp.getId());
                        ProductoActivity.this.sqLibraryApp.getDataManager().updateTmpDetalle(beanTmpDet);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("currentPedido", currentPedido);
                    bundle.putSerializable("currentZona", currentZona);
                    bundle.putSerializable("currentCliente", currentCliente);

                    DetalleActivity fragmentDetalleActivity = new DetalleActivity();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentDetalleActivity.setArguments(bundle);
                    //getActivity().setResult(9, new Intent(ProductoActivity.this, DetalleActivity.class));
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentDetalleActivity).commit();
                } else {
                    Toast.makeText(getActivity(), "Ingrese correctamente el monto del descuento", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    public void calcularTotal() {
        float precio;
        float cantidad = 0.0f;
        float descuento = 0.0f;
        float descuentoestructurado = 0.0f;
        if (!this.editCantidad.getText().toString().equals("")) {
            cantidad = Float.parseFloat(this.editCantidad.getText().toString());
        }
        if (!this.editDscto.getText().toString().equals("")) {
            descuento = Float.parseFloat(this.editDscto.getText().toString());
        }
        if (!currentPedido.getDescuento().toString().equals("")) {
            descuentoestructurado = Float.parseFloat(currentPedido.getDescuento());
        }
        if (this.codmoneda.equals(Constante.COD_LMONEDA)) {
            //(((100.0f - Float.parseFloat(currentPedido.getDescuento())) / 100.0f) * Float.parseFloat(this.currentProducto.getPreciosol()))
            //precio = ((100.0f - descuento) / 100.0f) * Float.parseFloat(this.currentProducto.getPreciorealsol());
            precio = (((100.0f - descuentoestructurado) / 100.0f) * Float.parseFloat(this.currentProducto.getPreciosol()));
        } else {
            //(((100.0f - Float.parseFloat(currentPedido.getDescuento())) / 100.0f) * Float.parseFloat(this.currentProducto.getPreciorealdol()))
            //precio = ((100.0f - descuento) / 100.0f) * Float.parseFloat(this.currentProducto.getPreciorealdol());
            precio = (((100.0f - descuentoestructurado) / 100.0f) * Float.parseFloat(this.currentProducto.getPreciorealdol()));
        }
        this.precioTotal = Float.valueOf(((100.0f - descuento) / 100.0f) * precio * cantidad);
        //this.precioTotal = Float.valueOf(Float.parseFloat(Util.formatDecimal(String.valueOf(this.precioTotal))));
        this.txtTotal.setText(Util.getTwoDecimals(this.precioTotal));
    }

    private void salirDetalle() {
        Bundle bundle2 = new Bundle();
        bundle2.putInt("page", 1);
        bundle2.putString("codmoneda", codmoneda);
        bundle2.putString("nivelPrecio", nivelPrecio);
        bundle2.putSerializable("currentPedido", currentPedido);
        bundle2.putSerializable("currentZona", currentZona);
        bundle2.putSerializable("currentCliente", currentCliente);

        BusqProductoActivity fragmentbusqprod = new BusqProductoActivity();
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentbusqprod.setArguments(bundle2);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentbusqprod).commit();
        return;

        //getActivity().getFragmentManager().popBackStack();
        //getActivity().finish();

    }
}
