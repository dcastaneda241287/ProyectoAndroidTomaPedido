package com.farmagro.tomapedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.farmagro.tomapedido.adapters.ProductoListAdapter;
import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.Producto;
import com.farmagro.tomapedido.modelo.Zona;
import com.farmagro.tomapedido.util.Constante;
import com.farmagro.tomapedido.util.Framework;
import com.farmagro.tomapedido.util.FrameworkAdapterOne;

import java.util.ArrayList;

public class BusqProductoActivity extends Fragment {
    private CabPedido currentPedido;
    private Zona currentZona;
    private Cliente currentCliente;
    /* access modifiers changed from: private */
    public String codmoneda;
    /* access modifiers changed from: private */
    public EditText editFilter;
    private ImageButton imvSearch;
    /* access modifiers changed from: private */
    public ListView listProducto;
    /* access modifiers changed from: private */
    public ProductoListAdapter lstAdapter;
    /* access modifiers changed from: private */
    public String nivelPrecio;
    /* access modifiers changed from: private */
    public String nprecio = "";
    /* access modifiers changed from: private */
    public int page;
    /* access modifiers changed from: private */
    public Spinner spnSearch;
    protected SQLibraryApp sqLibraryApp;
    private ArrayList<Framework> frameworks;
    private Framework framework;
    private FrameworkAdapterOne adapter;
    private int x, y = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_busq_producto, container, false);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
        return rootView;
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        this.page = getArguments().getInt("page");


        if (this.page == 1) {
            setHasOptionsMenu(true);
            this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_busq_producto));
            this.codmoneda = getArguments().getString("codmoneda");

            this.currentPedido = (CabPedido) getArguments().get("currentPedido");
            this.currentZona = (Zona) getArguments().get("currentZona");
            this.currentCliente = (Cliente) getArguments().get("currentCliente");
        }
        else{
            this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_stock));
        }
        this.nivelPrecio = getArguments().getString("nivelPrecio", Constante.TIPO_LIMA);
        this.spnSearch = view.findViewById(R.id.spnSearch);
        this.listProducto = view.findViewById(R.id.listProducto);
        this.imvSearch = view.findViewById(R.id.imvSearch);
        this.editFilter = view.findViewById(R.id.editFilter);
        if (this.nivelPrecio.contains(Constante.TIPO_LIMA)) {
            this.nprecio = Constante.TIPO_LIMA;
        } else {
            this.nprecio = Constante.TIPO_SEMILLA;
        }
        //ArrayAdapter<CharSequence> createFromResource = ArrayAdapter.createFromResource(getActivity(), R.array.busqueda, android.R.layout.simple_spinner_item);
        //createFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        frameworks = new ArrayList<>();
        Resources res = getResources();
        String[] tipobusqueda = res.getStringArray(R.array.busqueda);
        //framework = new Framework(0 , "[Seleccione]",0);
        //frameworks.add(framework);
        for (x = 0; x < tipobusqueda.length; x++) {
            framework = new Framework(0 , tipobusqueda[x].toString(),0,"");
            frameworks.add(framework);
        }
        adapter = new FrameworkAdapterOne(getActivity(),frameworks);
        this.spnSearch.setAdapter(adapter);
        this.spnSearch.setSelection(1);
        this.spnSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                if (BusqProductoActivity.this.spnSearch.getSelectedItemPosition() == 0) {
                    BusqProductoActivity.this.editFilter.setText("");
                    BusqProductoActivity.this.editFilter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                    BusqProductoActivity.this.editFilter.setInputType(2);
                    return;
                }
                BusqProductoActivity.this.editFilter.setText("");
                BusqProductoActivity.this.editFilter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                BusqProductoActivity.this.editFilter.setInputType(1);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.imvSearch = view.findViewById(R.id.imvSearch);
        this.imvSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (BusqProductoActivity.this.editFilter.getText().length() < 2) {
                    Toast.makeText(getActivity(), "Ingrese minimo 2 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }
                BusqProductoActivity.this.lstAdapter = new ProductoListAdapter(getActivity(), R.layout.producto_item, BusqProductoActivity.this.sqLibraryApp.getDataManager().getProductoList(BusqProductoActivity.this.editFilter.getText().toString(), BusqProductoActivity.this.spnSearch.getSelectedItemPosition(), BusqProductoActivity.this.nprecio));
                BusqProductoActivity.this.listProducto.setAdapter(BusqProductoActivity.this.lstAdapter);
            }
        });
        this.listProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                Producto currentProducto = (Producto) BusqProductoActivity.this.listProducto.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("currentProducto", currentProducto);

                if (BusqProductoActivity.this.page != 1) {
                    //Intent intent = new Intent(BusqProductoActivity.this, ProdStockActivity.class);
                    bundle.putSerializable("currentPedido", "");
                    bundle.putString("nivelPrecio", BusqProductoActivity.this.nivelPrecio);
                    ProdStockActivity fragmentProdStockActivity = new ProdStockActivity();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentProdStockActivity.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentProdStockActivity).commit();
                    return;

                } else if (BusqProductoActivity.this.sqLibraryApp.getDataManager().getTmpDetalleById(currentProducto.getCodproducto()) == null) {
                    bundle.putSerializable("currentPedido", (CabPedido) getArguments().get("currentPedido"));
                    bundle.putString("codmoneda", BusqProductoActivity.this.codmoneda);
                    bundle.putString("nivelPrecio", BusqProductoActivity.this.nivelPrecio);
                    bundle.putSerializable("currentZona", currentZona);
                    bundle.putSerializable("currentCliente", currentCliente);

                    ProductoActivity fragmentProductoActivity = new ProductoActivity();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentProductoActivity.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentProductoActivity).commit();
                    return;
                    //BusqProductoActivity.this.finish();
                } else {
                    Toast.makeText(getActivity(), "El producto " + currentProducto.getCodproducto() + " ya fue seleccionado, seleccione otro producto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_busq_prod, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opcBackBPTransp) {
            salirDetalle();
        }
        return true;
    }

    private void salirDetalle() {
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("currentPedido", currentPedido);
        bundle2.putSerializable("currentZona", currentZona);
        bundle2.putSerializable("currentCliente", currentCliente);

        DetalleActivity fragmentdetalle = new DetalleActivity();
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentdetalle.setArguments(bundle2);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentdetalle).commit();
        return;

        //getActivity().getFragmentManager().popBackStack();
        //getActivity().finish();

    }
}
