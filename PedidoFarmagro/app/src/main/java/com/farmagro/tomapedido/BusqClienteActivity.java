package com.farmagro.tomapedido;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.farmagro.tomapedido.adapters.ClienteListAdapter;
import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.modelo.Zona;
import com.farmagro.tomapedido.modelo.ZonaVendedor;
import com.farmagro.tomapedido.util.Framework;
import com.farmagro.tomapedido.util.FrameworkAdapterOne;

import java.util.ArrayList;
import java.util.List;

public class BusqClienteActivity extends Fragment {

    /* access modifiers changed from: private */
    public EditText editFilter;
    private ImageButton imvSearch;
    /* access modifiers changed from: private */
    public ListView listClientes;
    /* access modifiers changed from: private */
    public int page;
    /* access modifiers changed from: private */
    public Spinner spnSearch;
    /* access modifiers changed from: private */
    public Spinner spnZona;
    protected SQLibraryApp sqLibraryApp;
    public static final String ARG_ARTICLES_NUMBER = "articles_number";
    private ArrayList<Framework> frameworks;
    private Framework framework;
    private FrameworkAdapterOne adapter;
    private int i = 0;

    public BusqClienteActivity() {
        // Constructor vacío

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_busq_cliente, container, false);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
        return rootView;
    }
    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragThreeTv = (TextView) view.findViewById(R.id.fragThreeTv);
        fragThreeTv.setText(message1);

    }*/
    /*
    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
    }
*/
    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Usuario beanUsuario = sqLibraryApp.getDataManager().getUsuario();
        this.page = getArguments().getInt("page");
        //this.getActivity().setActionBarTitlw((CharSequence) "BUSQUEDA DE CLIENTES");
        this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_busqueda_cliente));
        //((MenuPrincipal)getActivity()).setTitle("Create New Task");

        //SincroActivity.this.sqLibraryApp.getDataManager().getUsuario();
        this.listClientes = view.findViewById(R.id.listClientes);
        this.spnSearch = view.findViewById(R.id.spnSearch);
        this.spnZona = view.findViewById(R.id.spnZona);
        this.editFilter = view.findViewById(R.id.editFilter);
        this.imvSearch = view.findViewById(R.id.imvSearch);
        List<Zona> listZona = new ArrayList<>();
        List<ZonaVendedor> listZonaVen = this.sqLibraryApp.getDataManager().getZonaVendedorList(beanUsuario.getClave(),1);

        frameworks = new ArrayList<>();
        for (ZonaVendedor zonavendedor : listZonaVen) {
            //Zona beanZona = new Zona();
            //beanZona.setCodigo(zonavendedor.getvCodZona());
            //beanZona.setDescripcion(zonavendedor.getvDescripcion());
            //listZona.add(beanZona);
            framework = new Framework(0 , zonavendedor.getvDescripcion(),0,zonavendedor.getvCodZona());
            frameworks.add(framework);
        }

        /*if (beanUsuario.getZona1().equalsIgnoreCase(Constante.NO) || beanUsuario.getZona1().equalsIgnoreCase("")) {
            Zona beanZona = new Zona();
            beanZona.setCodigo(beanUsuario.getZona2());
            beanZona.setDescripcion(beanUsuario.getDeszona2());
            listZona.add(beanZona);
        } else {
            Zona beanZona2 = new Zona();
            beanZona2.setCodigo(beanUsuario.getZona1());
            beanZona2.setDescripcion(beanUsuario.getDeszona1());
            listZona.add(beanZona2);
        }*/
        adapter = new FrameworkAdapterOne(getActivity(),frameworks);
        //ArrayAdapter<Zona> adapterZona = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listZona);
        //adapterZona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spnZona.setAdapter(adapter);
        this.spnZona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<CharSequence> createFromResource = ArrayAdapter.createFromResource(getActivity(), R.array.busqueda, android.R.layout.simple_spinner_item);
        createFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        frameworks = new ArrayList<>();
        Resources res = getResources();
        String[] tipobusqueda = res.getStringArray(R.array.busqueda);
        //framework = new Framework(0 , "[Seleccione]",0);
        //frameworks.add(framework);
        for (i = 0; i < tipobusqueda.length; i++) {
            framework = new Framework(0 , tipobusqueda[i].toString(),0,"");
            frameworks.add(framework);
        }
        adapter = new FrameworkAdapterOne(getActivity(),frameworks);

        this.spnSearch.setAdapter(adapter);
        this.spnSearch.setSelection(1);
        this.spnSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                if (BusqClienteActivity.this.spnSearch.getSelectedItemPosition() == 0) {
                    BusqClienteActivity.this.editFilter.setText("");
                    BusqClienteActivity.this.editFilter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                    BusqClienteActivity.this.editFilter.setInputType(2);
                    return;
                }
                BusqClienteActivity.this.editFilter.setText("");
                BusqClienteActivity.this.editFilter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                BusqClienteActivity.this.editFilter.setInputType(1);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.imvSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (BusqClienteActivity.this.editFilter.getText().length() < 2) {
                    Toast.makeText(getActivity(), "Ingrese minimo 2 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }
                BusqClienteActivity.this.listClientes.setAdapter(new ClienteListAdapter(getActivity(), R.layout.cliente_item, BusqClienteActivity.this.sqLibraryApp.getDataManager().getClienteList(BusqClienteActivity.this.editFilter.getText().toString(), BusqClienteActivity.this.spnSearch.getSelectedItemPosition())));
            }
        });
        this.listClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                Zona zona =new Zona();
                PedidoActivity fragmentPedidoActivity = new PedidoActivity();
                LineaCreditoActivity fragmentLineaCredito = new LineaCreditoActivity();

                FragmentManager fragmentManager = getParentFragmentManager();
                zona.setCodigo(((Framework) BusqClienteActivity.this.spnZona.getItemAtPosition(BusqClienteActivity.this.spnZona.getSelectedItemPosition())).getValor());
                zona.setDescripcion(((Framework) BusqClienteActivity.this.spnZona.getItemAtPosition(BusqClienteActivity.this.spnZona.getSelectedItemPosition())).getNombre());

                Bundle bundle = new Bundle();
                bundle.putSerializable("currentZona", zona);
                bundle.putSerializable("currentCliente", (Cliente) BusqClienteActivity.this.listClientes.getItemAtPosition(position));

                if (BusqClienteActivity.this.page == 1) {

                    fragmentPedidoActivity.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentPedidoActivity).commit();

                    //Intent intent = new Intent(getActivity(), PedidoActivity.class);
                    //intent.putExtras(bundle);
                    //BusqClienteActivity.this.startActivity(intent);
                    return;
                }
                fragmentLineaCredito.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentLineaCredito).commit();
                return;
                //Intent intent2 = new Intent(getActivity(), LineaCreditoActivity.class);
                //intent2.putExtras(bundle);
                //BusqClienteActivity.this.startActivity(intent2);
            }
        });

    }

}